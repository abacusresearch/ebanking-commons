package ch.deeppay.rest.async;

import java.io.InputStream;
import java.util.Objects;

import ch.deeppay.exception.DeepPayProblemException;
import lombok.extern.log4j.Log4j2;

import static ch.deeppay.exception.DeepPayProblemException.createServerErrorProblemException;

@Log4j2
public class ApiResponseHandlerImpl<T> implements ApiResponseHandler<T> {

  private final AsyncResponseSaveFactory asyncResponseHandlerFactory;
  private final AsyncContextDataProvider contextDataProvider;
  private boolean isAsynchronous;
  private InputStream response;
  private Throwable exception;
  private AsyncResponseSaveHandler asyncResponseHandler = null;

  private final Object lock = new Object();

  public ApiResponseHandlerImpl(final AsyncResponseSaveFactory asyncResponseHandlerFactory, final AsyncContextDataProvider contextDataProvider) {
    this.asyncResponseHandlerFactory = asyncResponseHandlerFactory;
    this.contextDataProvider = contextDataProvider;
  }

  @Override
  public void setResponseAsync(final boolean isAsynchronous) {
    synchronized (lock) {
      this.isAsynchronous = isAsynchronous;
    }
  }

  @Override
  public Void applyResponse(final InputStream response, final Throwable throwable) {
    synchronized (lock) {
      this.response = response;
      this.exception = throwable;
    }

    if (Objects.nonNull(asyncResponseHandler)) {
      if (Objects.nonNull(exception)) {
        asyncResponseHandler.handleResponse(throwable);
      } else {
        asyncResponseHandler.handleResponse(response);
      }
    }

    return null;
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
        asyncResponseHandler = asyncResponseHandlerFactory.create(contextDataProvider);
        data = ResponseData.builder().identifier(asyncResponseHandler.getIdentifier()).build();
        log.info("Job {} has to be processed asynchronous.", data.getIdentifier());

        //make api call outside the lock might cause a duplicate record exception problem on the job service.
        asyncResponseHandler.createJob();
      } else {
        if (Objects.nonNull(exception)) {
          throw handleException(exception);
        } else {
          data = ResponseData.builder().response(response).build();
        }
      }
    }

    return processor.process(isAsynchronous, data);
  }


  private DeepPayProblemException handleException(final Throwable exception) {
    if (exception instanceof DeepPayProblemException) {
      return (DeepPayProblemException) exception;
    } else if (exception.getCause() instanceof DeepPayProblemException) {
      return (DeepPayProblemException) exception.getCause();
    } else {
      return createServerErrorProblemException(exception.getMessage());
    }
  }

}