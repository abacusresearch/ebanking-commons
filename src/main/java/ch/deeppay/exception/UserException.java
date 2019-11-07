package ch.deeppay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends GeneralException {

  public UserException(String detail) {
    super("User error", detail, HttpStatus.BAD_REQUEST);
  }
}
