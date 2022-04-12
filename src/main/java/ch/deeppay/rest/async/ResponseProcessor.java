package ch.deeppay.rest.async;

/**
 * Implementation fo this interface is responsible to build the response to the client.
 * The client response depends on if the result is asynchronous or not.
 *
 * @param <T>
 */
public interface ResponseProcessor<T> {

  T process(boolean isAsynchronous, ResponseData responseData);

}
