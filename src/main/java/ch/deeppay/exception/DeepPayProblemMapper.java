package ch.deeppay.exception;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.zalando.problem.AbstractThrowableProblem;

import static ch.deeppay.exception.DeepPayProblemException.createProblemException;
import static ch.deeppay.exception.DeepPayProblemException.createServerErrorProblemException;


/**
 * This class maps several error codes or HttpStatusCodeException's to DeepPayProblemException's.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeepPayProblemMapper extends AbstractThrowableProblem {

  private final Map<String, DeepPayProblemTypeGetter> mapper = new HashMap<>();
  private DeepPayProblemTypeGetter fallbackProblemType;

  /**
   * Map's an error code to an {@code DeepPayProblemTypeGetter} type
   *
   * @param apiErrorCode       error code of an external api
   * @param deepPayProblemType internal DeepPay error type
   * @return instance of {@code DeepPayProblemMapper}
   */
  public DeepPayProblemMapper register(@Nonnull final String apiErrorCode, @Nonnull final DeepPayProblemTypeGetter deepPayProblemType) {
    mapper.put(apiErrorCode, deepPayProblemType);
    return this;
  }

  /**
   * Map's an http status code to an {@code DeepPayProblemTypeGetter} type
   *
   * @param httpStatus         http status code
   * @param deepPayProblemType internal DeepPay error type
   * @return instance of {@code DeepPayProblemMapper}
   */
  public DeepPayProblemMapper register(@Nonnull final HttpStatus httpStatus, @Nonnull final DeepPayProblemTypeGetter deepPayProblemType) {
    register(httpStatus.toString(), deepPayProblemType);
    return this;
  }

  /**
   * This fallback type is used when the error was not in the registered error list.
   *
   * @param deepPayProblemType internal DeepPay error type
   * @return instance of {@code DeepPayProblemMapper}
   */
  public DeepPayProblemMapper setFallbackProblemType(@Nonnull final DeepPayProblemTypeGetter deepPayProblemType) {
    fallbackProblemType = deepPayProblemType;
    return this;
  }

  /**
   * Creates a {@code DeepPayProblemException} instance that was mapped by the passed {@code HttpStatusCodeException}.
   * The fallback problem will be returned when the passed exception is not in the {@code register(...)} list. If no fallback is set the passed error is
   * converted into DeepPayProblemException
   *
   * @param exception
   * @return instance of {@code DeepPayProblemException}
   */
  @Nonnull
  public DeepPayProblemException create(@Nonnull final HttpStatusCodeException exception) {
    String status = exception.getStatusCode().toString();
    return mapper.containsKey(status) ? createProblemException(mapper.get(status), exception) : getFallbackProblem(exception);
  }

  /**
   * Creates a {@code DeepPayProblemException} instance that was mapped by the passed api error code.
   * The fallback problem will be returned when the passed exception is not in the {@code register(...)} list. If no fallback is set the passed detail message is
   * converted into DeepPayProblemException (HttpStatus.INTERNAL_SERVER_ERROR)
   *
   * @param apiErrorCode  error code of an external api
   * @param detailMessage detail message of the external error
   * @return instance of {@code DeepPayProblemException}
   */
  @Nonnull
  public DeepPayProblemException create(@Nonnull final String apiErrorCode, @Nonnull final String detailMessage) {
    return mapper.containsKey(apiErrorCode) ? createProblemException(mapper.get(apiErrorCode), detailMessage) : getFallbackProblem(detailMessage);
  }

  @Nonnull
  private DeepPayProblemException getFallbackProblem(@Nonnull final String detailMessage) {
    return Objects.isNull(fallbackProblemType) ? createServerErrorProblemException(detailMessage) : createProblemException(fallbackProblemType, detailMessage);
  }

  @Nonnull
  private DeepPayProblemException getFallbackProblem(@Nonnull final HttpStatusCodeException exception) {
    return Objects.isNull(fallbackProblemType) ? createProblemException(exception) : createProblemException(fallbackProblemType, exception);
  }

  public static DeepPayProblemMapper getMapper() {
    return new DeepPayProblemMapper();
  }
}