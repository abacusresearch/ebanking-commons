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

  /**
   * Throws a {@code DeepPayProblemException} when the apiErrorCode matches a before registered error code. If a fallback problem is set
   * a {@code DeepPayProblemException} will be thrown in any case.
   *
   * @param apiErrorCode  error code of an external api
   * @param detailMessage detail message of the external error
   */
  public void throwIfExists(@Nonnull final String apiErrorCode, @Nonnull final String detailMessage){
    if(mapper.containsKey(apiErrorCode) || Objects.nonNull(fallbackProblemType)){
      throw create(apiErrorCode, detailMessage);
    }
  }

  /**
   * Throws a {@code DeepPayProblemException} when the HttpStatusCodeException matches a before registered exception. If a fallback problem is set
   * a {@code DeepPayProblemException} will be thrown in any case.
   *
   * @param exception
   */
  public void throwIfExists(@Nonnull final HttpStatusCodeException exception){
    String status = exception.getStatusCode().toString();
    if(mapper.containsKey(status) || Objects.nonNull(fallbackProblemType)){
      throw create(exception);
    }
  }

  /**
   * Returns true when error code is registered
   * @param apiErrorCode error code
   * @return true when error code is registered otherwise false
   */
  public boolean isRegistered(@Nonnull final String apiErrorCode){
    return mapper.containsKey(apiErrorCode);
  }

  /**
   * Returns true when {@code HttpStatus} is registered
   * @param httpStatus http status code
   * @return true when {@code HttpStatus} is registered otherwise false
   */
  public boolean isRegistered(@Nonnull final HttpStatus httpStatus){
    return isRegistered(httpStatus.toString());
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