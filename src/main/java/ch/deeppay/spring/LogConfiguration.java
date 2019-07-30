package ch.deeppay.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilters;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.QueryFilters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class LogConfiguration {

  @Bean
  public Logbook logbook() {
    final Set<String> properties = new HashSet<>(Arrays.asList(
        "password", "passwort", "passwordNew",
        "challenge",
        "clientId", "client_id",
        "clientSecret", "client_secret",
        "access_token", "accessToken",
        "refreshToken", "refresh_token",
        "transportData"));

    return Logbook.builder()
        .bodyFilter(BodyFilters.replaceJsonStringProperty(properties, "<secret>"))
        .bodyFilter(BodyFilters.replaceFormUrlEncodedProperty(properties, "<secret>"))
        .queryFilter(QueryFilters.replaceQuery("clientid", "<secret>"))
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
        .build();
  }

}
