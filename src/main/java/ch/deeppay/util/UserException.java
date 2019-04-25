package ch.deeppay.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends GeneralException {

  private final String message;

  UserException(String message) {
    this.message = message;
  }
}
