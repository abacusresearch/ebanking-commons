package ch.deeppay.spring.constraints;

import javax.validation.ConstraintValidatorContext;

public interface ExtendedFileFormatValidatorInterface {
  boolean isValid(String value, ConstraintValidatorContext context);
}
