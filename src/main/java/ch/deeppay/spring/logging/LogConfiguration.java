package ch.deeppay.spring.logging;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.DefaultHttpLogFormatter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.HeaderFilters;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.LogbookCreator;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.QueryFilters;
import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration;
import org.zalando.logbook.common.MediaTypeQuery;

import static java.util.regex.Pattern.compile;
import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonStringProperty;

/**
 * Contains beans used for configuring log files.
 */
@Log4j2
@Configuration
@ConditionalOnClass(LogbookAutoConfiguration.class)
@ConditionalOnProperty(value = "ch.deeppay.spring.logconfiguration.enabled", matchIfMissing = true)
public class LogConfiguration {

  private final Pattern creditCard = Pattern.compile("(\\w*)\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b(\\w*)");

  public static final String SECRET = "<secret>";

  private static final String DEFAULT_PROPERTIES = "password," +
                                                   "passwort," +
                                                   "passwordNew," +
                                                   "challenge," +
                                                   "clientId," +
                                                   "client_id," +
                                                   "clientSecret," +
                                                   "client_secret," +
                                                   "access_token," +
                                                   "accessToken," +
                                                   "refreshToken," +
                                                   "refresh_token," +
                                                   "cardNumber," +
                                                   "card_number," +
                                                   "file," +
                                                   "contractId," +
                                                   "transportData";

  private static final String DEFAULT_QUERY_FILTER_NAMES = "clientid," +
                                                           "client_id," +
                                                           "clientsecret," +
                                                           "access_token," +
                                                           "accessToken," +
                                                           "password," +
                                                           "passwort," +
                                                           "passwordNew," +
                                                           "refreshToken," +
                                                           "refresh_token," +
                                                           "contractId," +
                                                           "file," +
                                                           "transportData";

  private static final String DEFAULT_COOKIES = "*";

  private static final String DEFAULT_HEADERS = "x-api-key, authorization";

  @Value("${ch.deeppay.spring.logconfiguration.json_properties:" + DEFAULT_PROPERTIES + "}")
  private Set<String> jsonProperties;

  @Value("${ch.deeppay.spring.logconfiguration.form_url_encoded_properties:}")
  private Set<String> formUrlEncodedProperties;

  @Value("${ch.deeppay.spring.logconfiguration.xml_properties:}")
  private Set<String> xmlProperties;

  @Value("${ch.deeppay.spring.logconfiguration.query_filter_names:" + DEFAULT_QUERY_FILTER_NAMES + "}")
  private List<String> queryFilterNames;

  @Value("${ch.deeppay.spring.logconfiguration.header.cookies: " + DEFAULT_COOKIES + "}")
  private Set<String> cookieNames;

  @Value("${ch.deeppay.spring.logconfiguration.header: " + DEFAULT_HEADERS + "}")
  private Set<String> headerNames;

  /**
   * Configured bean to mask sensitive log data for request and response
   */
  @Bean
  @ConditionalOnMissingBean
  public Logbook getMaskSensitiveDataConfiguration() {

    LogbookCreator.Builder builder = Logbook.builder().sink(new DefaultSink(new DefaultHttpLogFormatter(), new CustomizedHttpLogWriter()))
                                            .bodyFilter(replaceJsonStringProperty(jsonProperties, SECRET));

    if (Objects.nonNull(formUrlEncodedProperties) && !formUrlEncodedProperties.isEmpty()) {
      builder.bodyFilter(replaceFormUrlEncodedProperty(formUrlEncodedProperties, SECRET));
    }

    if (Objects.nonNull(xmlProperties) && !xmlProperties.isEmpty()) {
      builder.bodyFilter(new SaxXmlBodyFilter(SECRET,xmlProperties));
    }

    for (String name : queryFilterNames) {
      builder.queryFilter(QueryFilters.replaceQuery(name::equalsIgnoreCase, SECRET));
    }

    if (Objects.nonNull(cookieNames) && !cookieNames.isEmpty()) {
      builder.headerFilter(HeaderFilters.replaceCookies(s -> cookieNames.contains(DEFAULT_COOKIES) || cookieNames.contains(s), SECRET));
    }

    if (Objects.nonNull(headerNames) && !headerNames.isEmpty()) {
      builder.headerFilter(HeaderFilters.replaceHeaders(headerNames, SECRET));
    }

    return builder.queryFilter(cardNumber())
                  .condition(exclude(requestTo("**/health"),
                                     requestTo("/actuator/**"),
                                     requestTo("/admin/**")))
                  .build();
  }

  private QueryFilter cardNumber() {
    return query -> {
      final Matcher matcher = creditCard.matcher(query);
      if (matcher.find()) {
        return matcher.replaceFirst("$1*********$3$4");
      }
      return query;
    };
  }

  /**
   * Code is copied from {@link org.zalando.logbook.BodyFilters#replaceFormUrlEncodedProperty(Set, String)}
   *
   * @param properties  query names properties to replace
   * @param replacement String to replace the properties values
   * @return BodyFilter
   */
  public static BodyFilter replaceFormUrlEncodedProperty(final Set<String> properties, final String replacement) {
    final Predicate<String> formUrlEncoded = MediaTypeQuery.compile(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    final QueryFilter delegate = replaceQuery(properties::contains, replacement);
    return (contentType, body) -> formUrlEncoded.test(contentType) ? delegate.filter(body) : body;
  }

  /**
   * Code is copied from {@link org.zalando.logbook.QueryFilters#replaceQuery(String, String)}. Compare to the original code
   * the properties can be incasesensitive
   *
   * @param predicate   Predicate that decide if value has to be replaced
   * @param replacement String to replace the properties values
   * @return QueryFilter
   */
  public static QueryFilter replaceQuery(final Predicate<String> predicate, final String replacement) {

    final Pattern pattern = compile("(?<name>[^&]*?)=(?:[^&]*)");

    return query -> {
      final Matcher matcher = pattern.matcher(query);
      final StringBuffer result = new StringBuffer(query.length());

      while (matcher.find()) {
        if (predicate.test(matcher.group("name"))) {
          matcher.appendReplacement(result, "${name}");
          result.append('=');
          result.append(replacement);
        } else {
          matcher.appendReplacement(result, "$0");
        }
      }
      matcher.appendTail(result);

      return result.toString();
    };
  }

}
