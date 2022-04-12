package ch.deeppay.rest.async;


import javax.annotation.Nullable;
import java.io.InputStream;

/**
 * Implementation of this interface handles the answer of an api call that is executed asynchronous.
 * This might be required when an external call takes too long.
 */
public interface ApiResponseHandler<T> {


  /**
   * Set value true when response must be handled asynchronous otherwise false.
   *
   * @param isAsynchronous true when api response has to be handled asynchronous
   */
  void setResponseAsync(boolean isAsynchronous);

  /**
   * Handles the response of the api. The response or the exception is stored. If applyResponse is called after processResponse the response will be prepared in such a way that it can be queried asynchronous
   * If an exception was thrown the response might be null and the exception is passed.
   *
   * @param response  response of the api call
   * @param throwable Exception that was returned instead of a response.
   * @return response of the api. It might be null when an exception was thrown
   */
  @Nullable
  Void applyResponse(@Nullable InputStream response, @Nullable Throwable throwable);

  /**
   * Methode to prepare the response for the current caller. If the answer is asynchronous the ResponseData contains an identifier that can be used to download the response later.
   *
   * @param processor boolean parameter indicates if the response has to handled asynchronous. The second parameter contains all relevant infos of the api response.
   */
  T processResponse(ResponseProcessor<T> processor);

}