package ch.deeppay.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralException extends RuntimeException {

  private MessageSource messageSource;
  private HttpStatus httpStatus;

  public GeneralException() {
  }

  public GeneralException(final @NonNull String message) {
    super(message);
  }

  public GeneralException(final @NonNull String message, final @NonNull HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
