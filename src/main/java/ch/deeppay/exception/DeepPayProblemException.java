package ch.deeppay.exception;

import java.net.URI;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpStatusCodeException;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeepPayProblemException extends AbstractThrowableProblem {

  DeepPayProblemException(@NonNull final HttpStatusCodeException e) {
    super(null, null, new HttpStatusAdapter(e.getStatusCode()), e.getResponseBodyAsString());
  }

  DeepPayProblemException(@NonNull final URI uri, @NonNull final String title, @NonNull final HttpStatus httpStatus, @NonNull final String detail) {
    super(uri, title, new HttpStatusAdapter(httpStatus), detail);
  }

  DeepPayProblemException(@NonNull final String title, @NonNull final HttpStatus httpStatus, @NonNull final String detail) {
    super(null, title, new HttpStatusAdapter(httpStatus), detail); //TODO darf uri null sein
  }

  DeepPayProblemException(@NonNull final URI uri,
                          @NonNull final String title,
                          @NonNull final HttpStatus httpStatus,
                          @NonNull final String detail,
                          @NonNull final URI instance) {
    super(uri, title, new HttpStatusAdapter(httpStatus), detail, instance);
  }

  public static void throwProblemException(@NonNull final DeepPayProblemType problem, @NonNull final String detail) {
    throw new DeepPayProblemException(problem.getUri(), problem.getTitle(), problem.getHttpStatus(), detail);
  }

  public static void throwProblemException(@NonNull final DeepPayProblemType problem, @NonNull final String detail, @NonNull final String instance) {
    throw new DeepPayProblemException(problem.getUri(), problem.getTitle(), problem.getHttpStatus(), detail, URI.create(instance));
  }

  public static DeepPayProblemException createProblemException(@NonNull final DeepPayProblemType problem,
                                                               @NonNull final String detail) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       detail);
  }

  public static DeepPayProblemException createProblemException(@NonNull final DeepPayProblemType problem,
                                                               @NonNull final String detail,
                                                               @NonNull final Object... args) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       String.format(detail, args));
  }


  public static DeepPayProblemException createProblemException(@NonNull final DeepPayProblemType problem,
                                                               @NonNull final HttpStatusCodeException exception) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       exception.getResponseBodyAsString());
  }

  public static DeepPayProblemException createServerErrorProblemException(@NonNull final String detail) {
    return new DeepPayProblemException("Unexpected server error", HttpStatus.INTERNAL_SERVER_ERROR, detail);
  }

  public static DeepPayProblemException createProblemException(@NonNull final HttpStatusCodeException exception) {
    return new DeepPayProblemException(exception);
  }
}

