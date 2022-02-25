package ch.deeppay.exception;

import ch.deeppay.models.DeepPayProblem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

import javax.annotation.Nonnull;
import java.net.URI;

/**
 * This class defines a problem detail according to rfc7807 (https://datatracker.ietf.org/doc/html/rfc7807)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeepPayProblemException extends AbstractThrowableProblem {

  DeepPayProblemException(@Nonnull final RestClientResponseException e) {
    super(null, null, new HttpStatusAdapter(HttpStatus.valueOf(e.getRawStatusCode())), e.getResponseBodyAsString());
  }

  DeepPayProblemException(@Nonnull final WebClientResponseException e) {
    super(null, null, new HttpStatusAdapter(HttpStatus.valueOf(e.getRawStatusCode())), e.getResponseBodyAsString());
  }

  DeepPayProblemException(@Nonnull final URI uri, @Nonnull final String title, @Nonnull final HttpStatus httpStatus, @Nonnull final String detail) {
    super(uri, title, new HttpStatusAdapter(httpStatus), detail);
  }

  DeepPayProblemException(@Nonnull final String title, @Nonnull final HttpStatus httpStatus, @Nonnull final String detail) {
    super(null, title, new HttpStatusAdapter(httpStatus), detail); //TODO darf uri null sein
  }

  DeepPayProblemException(@Nonnull final URI uri,
                          @Nonnull final String title,
                          @Nonnull final HttpStatus httpStatus,
                          @Nonnull final String detail,
                          @Nonnull final URI instance) {
    super(uri, title, new HttpStatusAdapter(httpStatus), detail, instance);
  }

  /**
   * throws a DeepPayProblemException object
   *
   * @param problem of type DeepPayProblemTypeGetter
   * @param detail message of the problem
   */
  public static void throwProblemException(@Nonnull final DeepPayProblemTypeGetter problem, @Nonnull final String detail) {
    throw new DeepPayProblemException(problem.getUri(), problem.getTitle(), problem.getHttpStatus(), detail);
  }

  /**
   * throws a DeepPayProblemException object
   *
   * @param problem of type DeepPayProblemTypeGetter
   * @param detail message of the problem
   * @param instance uri (subpath) of the the resource that was called
   */
  public static void throwProblemException(@Nonnull final DeepPayProblemTypeGetter problem, @Nonnull final String detail, @Nonnull final String instance) {
    throw new DeepPayProblemException(problem.getUri(), problem.getTitle(), problem.getHttpStatus(), detail, URI.create(instance));
  }

  /**
   * Creates a new instance of a DeepPayProblemException
   *
   * @param problem of type DeepPayProblemTypeGetter
   * @param detail message of the problem
   * @return instance of DeepPayProblemException
   */
  public static DeepPayProblemException createProblemException(@Nonnull final DeepPayProblemTypeGetter problem,
                                                               @Nonnull final String detail) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       detail);
  }

  /**
   * Creates a new instance of a DeepPayProblemException
   *
   * @param problem of type DeepPayProblemTypeGetter
   * @param detail message of the problem
   * @param args the passed arguments must be part of detail message (the format must be like {@link  String#format(String, Object...)})
   * @return instance of DeepPayProblemException
   */
  public static DeepPayProblemException createProblemException(@Nonnull final DeepPayProblemTypeGetter problem,
                                                               @Nonnull final String detail,
                                                               @Nonnull final Object... args) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       String.format(detail, args));
  }

  /**
   * Creates a new instance of a DeepPayProblemException
   *
   * @param problem of type DeepPayProblemTypeGetter
   * @param exception of type HttpStatusCodeException
   * @return instance of DeepPayProblemException
   */
  public static DeepPayProblemException createProblemException(@Nonnull final DeepPayProblemTypeGetter problem,
                                                               @Nonnull final RestClientResponseException exception) {
    return new DeepPayProblemException(problem.getUri(),
                                       problem.getTitle(),
                                       problem.getHttpStatus(),
                                       exception.getResponseBodyAsString());
  }

  public static DeepPayProblemException createServerErrorProblemException(@Nonnull final String detail) {
    return new DeepPayProblemException("Unexpected server error", HttpStatus.INTERNAL_SERVER_ERROR, detail);
  }

  public static DeepPayProblemException createProblemException(@Nonnull final RestClientResponseException exception) {
    return new DeepPayProblemException(exception);
  }

  public static DeepPayProblemException createProblemException(@Nonnull final WebClientResponseException exception) {
    return new DeepPayProblemException(exception);
  }

  public static DeepPayProblemException createProblemExceptionForRethrow(@Nonnull final DeepPayProblem problem) {
    return new DeepPayProblemException(problem.getType(), problem.getTitle(), HttpStatus.valueOf(problem.getStatus()), problem.getDetail(), URI.create(problem.getInstance()));
  }

}

