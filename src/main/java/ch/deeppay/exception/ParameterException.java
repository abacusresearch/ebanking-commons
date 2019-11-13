package ch.deeppay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParameterException extends GeneralException {

  private final String message;
  private final String field;

  public ParameterException(@NonNull final String field, @NonNull final String message) {
    super("Parameter Violation",
          "Parameter " + field + ": " + message,
          HttpStatus.BAD_REQUEST);
    this.field = field;
    this.message = message;
  }
}
