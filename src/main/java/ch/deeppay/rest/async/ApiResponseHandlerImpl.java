package ch.deeppay.rest.async;

import java.util.Objects;
import java.util.UUID;

import ch.deeppay.exception.DeepPayProblemException;
import org.apache.commons.lang.StringUtils;

import static ch.deeppay.exception.DeepPayProblemException.createServerErrorProblemException;

public class ApiResponseHandlerImpl<T> implements ApiResponseHandler<T> {

  private final AsyncResponseSaveFactory asyncResponseSaveFactory;
  private final AsyncContextDataProvider contextDataProvider;
  private boolean isAsynchronous;
  private String response;
  private Throwable exception;
  private String identifier = null;

  private final Object lock = new Object();

  public ApiResponseHandlerImpl(final AsyncResponseSaveFactory asyncResponseSaveFactory, final AsyncContextDataProvider contextDataProvider) {
    this.asyncResponseSaveFactory = asyncResponseSaveFactory;
    this.contextDataProvider = contextDataProvider;
  }

  @Override
  public void setResponseAsync(final boolean isAsynchronous) {
    synchronized (lock) {
      this.isAsynchronous = isAsynchronous;
    }
  }

  @Override
  public String applyResponse(final String response, final Throwable throwable) {
    synchronized (lock) {
      this.response = response;
      this.exception = throwable;
      if (StringUtils.isNotEmpty(identifier)) {
        AsyncResponseSaveHandler saveHandler = asyncResponseSaveFactory.create(contextDataProvider);
        saveHandler.saveResponse(Objects.isNull(exception) ?
                                     ResponseData.builder().identifier(identifier).response(response).build() :
            ResponseData.builder().identifier(identifier).exception(exception).build());
      }
    }
    return response; //only used that CompletableFuture:handle can be used as lambda
  }

  @Override
  public T processResponse(final ResponseProcessor<T> processor) {
    ResponseData data;
    synchronized (lock) {
      //check again if response was set in the meantime
      if (Objects.nonNull(response) || Objects.nonNull(exception)) {
        isAsynchronous = false;
      }

      if (isAsynchronous) {
        //create an identifier that can be used to download the response later in an additional query.
        identifier = UUID.randomUUID().toString();
        data = ResponseData.builder().identifier(identifier).build();
      } else {
        if(Objects.nonNull(exception)){
          throw handleException(exception);
        }else{
          data =  ResponseData.builder().response(response).build();
        }
      }
    }
    return processor.process(isAsynchronous, data);
  }


  private DeepPayProblemException handleException(final Throwable exception) {
    if (exception instanceof DeepPayProblemException) {
      return (DeepPayProblemException) exception;
    } else if(exception.getCause() instanceof DeepPayProblemException) {
      return (DeepPayProblemException) exception.getCause();
    }else{
      return createServerErrorProblemException(exception.getMessage());
    }
  }

}