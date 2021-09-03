package ch.deeppay.spring.logging;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilters;
import org.zalando.logbook.DefaultHttpLogFormatter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.QueryFilters;
import org.zalando.logbook.json.JsonBodyFilters;

import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;

/**
 * Contains beans used for configuring log files.
 */
@Configuration
@ConditionalOnProperty(value = "ch.deeppay.spring.logconfiguration.enabled", matchIfMissing = true)
public class LogConfiguration {

  final Pattern creditcard = Pattern.compile("(\\w*)\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b(\\w*)");

  /**
   * Configured bean to mask sensitive log data for request and response
   */
  @Bean
  @ConditionalOnMissingBean
  public Logbook getMaskSensitiveDataConfiguration() {
    final Set<String> properties = new HashSet<>(Arrays.asList(
        "password", "passwort", "passwordNew",
        "challenge",
        "clientId", "client_id",
        "clientSecret", "client_secret",
        "access_token", "accessToken",
        "refreshToken", "refresh_token",
        "cardNumber", "card_number",
        "file",
        "transportData"));

    return Logbook.builder()
                  .sink(new DefaultSink(new DefaultHttpLogFormatter(), new CustomizedHttpLogWriter()))
                  .bodyFilter(BodyFilters.replaceFormUrlEncodedProperty(properties, "<secret>"))
                  .bodyFilter(JsonBodyFilters.replaceJsonStringProperty(properties, "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("clientid", "<secret>"))/**/
                  .queryFilter(QueryFilters.replaceQuery("client_id", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("clientsecret", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("access_token", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("accessToken", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("password", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("passwort", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("passwordNew", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("refreshToken", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("refresh_token", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("transportData", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("contractId", "<secret>"))
                  .queryFilter(QueryFilters.replaceQuery("file", "<secret>"))
                  .queryFilter(cardNumber())
                  .condition(exclude(requestTo("**/health"),
                                     requestTo("/actuator/**"),
                                     requestTo("/admin/**")))
                  .build();
  }

  private QueryFilter cardNumber() {
    return query -> {
      final Matcher matcher = creditcard.matcher(query);
      if (matcher.find()) {
        return matcher.replaceFirst("$1*********$3$4");
      }
      return query;
    };
  }

}