package ch.deeppay.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParameterException extends GeneralException {

  private final List<String> errors;

  public ParameterException(@NonNull final String error) {
    this.errors = Collections.singletonList(error);
  }

}
