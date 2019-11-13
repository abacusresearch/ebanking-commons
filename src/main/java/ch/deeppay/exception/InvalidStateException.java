package ch.deeppay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * Can be thrown if an API user reaches an unauthorized state.
 * Possible states: https://wiki.abacus.ch/display/EPAY/API+Design
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidStateException extends GeneralException {

  public InvalidStateException(@NonNull final String tokenType, @NonNull final Collection<String> values) {
    super("Invalid state reached",
          "Current State: " + tokenType + " Possible states: " + values,
          HttpStatus.BAD_REQUEST);
  }
}
