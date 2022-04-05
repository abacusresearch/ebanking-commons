package ch.deeppay.rest.async;

public interface ResponseProcessor<T> {

  T process(Boolean isAsynchronous, ResponseData responseData);

}
