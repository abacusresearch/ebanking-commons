package ch.deeppay.rest;

public interface RestClientProvider<R> {

  R createRestTemplate(int timeoutInSeconds);

  R createAuthRestTemplate();

  R createBusinessRestTemplate();

}
