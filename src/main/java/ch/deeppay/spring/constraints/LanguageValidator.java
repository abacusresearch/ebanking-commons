/*
 * LanguageValidator.java
 *
 * Creator:
 * 24/10/2019 18:38 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
package ch.deeppay.spring.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageValidator implements ConstraintValidator<LanguageConstraint, String> {

  private List<String> languageCodes = new ArrayList<>();

  @Override
  public void initialize(LanguageConstraint constraint) {
    if (languageCodes.isEmpty()) {
      languageCodes = Arrays.asList(Locale.getISOLanguages());
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value != null) {
      value = value.toLowerCase();
    }
    return languageCodes.contains(value);
  }

}
