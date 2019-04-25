package ch.deeppay.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
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

  @Autowired
  public GeneralException(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  MessageSource getMessageSource() {
    return this.messageSource;
  }
}
