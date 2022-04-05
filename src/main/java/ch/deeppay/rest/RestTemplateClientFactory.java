package ch.deeppay.rest;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

/**
 * Configuration of rest templates for authentication and business requests.
 * RestTemplate can not be registered as bean because otherwise sleuth will add automatically header values for tracing
 */
@Component
public class RestTemplateClientFactory implements RestClientProvider<RestTemplate> {

  @Value("${ch.deeppay.rest.timeout.authentication:60}")
  private int authenticationTimeout;

  @Value("${ch.deeppay.rest.timeout.business:1800}")
  private int businessTimeout;

  private final LogbookHttpRequestInterceptor logbookHttpRequestInterceptor;
  private final LogbookHttpResponseInterceptor logbookHttpResponseInterceptor;

  public RestTemplateClientFactory(final LogbookHttpRequestInterceptor logbookHttpRequestInterceptor,
                                   final LogbookHttpResponseInterceptor logbookHttpResponseInterceptor) {
    this.logbookHttpRequestInterceptor = logbookHttpRequestInterceptor;
    this.logbookHttpResponseInterceptor = logbookHttpResponseInterceptor;
  }


  @Override
  public RestTemplate createRestTemplate(int timeoutInSeconds) {
    final RestTemplate restTemplate = new RestTemplate(); //RestTemplateBuilder can not be used because of sleuth
    restTemplate.setRequestFactory(getClientHttpRequestFactory(authenticationTimeout));
    return restTemplate;
  }

  @Override
  public RestTemplate createAuthRestTemplate() {
    return createRestTemplate(authenticationTimeout);
  }

  @Override
  public RestTemplate createBusinessRestTemplate() {
    return createRestTemplate(businessTimeout);
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory(int seconds) {
    int timeout = seconds * 1000;
    final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(timeout);
    factory.setReadTimeout(timeout);
    factory.setHttpClient(HttpClientBuilder.create()
                                           .addInterceptorFirst(logbookHttpRequestInterceptor)
                                           .addInterceptorFirst(logbookHttpResponseInterceptor)
                                           .disableCookieManagement()
                                           .disableRedirectHandling()
                                           .build());
    return new BufferingClientHttpRequestFactory(factory);
  }

}
