/*
 * FileFormatValidator.java
 *
 * Creator:
 * 2/09/2019 14:39 hoefel
 *
 * Last Modification:
 * $Id: $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
package ch.deeppay.spring.constraints;

import ch.deeppay.util.FileFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileFormatValidator implements ConstraintValidator<FileFormatConstraint, String> {

  @Override
  public void initialize(FileFormatConstraint constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      if (value == null) {
        return false;
      }
      FileFormat.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }
}
