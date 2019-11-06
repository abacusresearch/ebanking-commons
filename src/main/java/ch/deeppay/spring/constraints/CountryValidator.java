/*
 * CountryValidator.java
 *
 * Creator:
 * 24/10/2019 18:46 hoefel
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

public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

  private List<String> countryCodes = new ArrayList<>();

  @Override
  public void initialize(CountryConstraint constraint) {
    if (countryCodes.isEmpty()) {
      countryCodes = Arrays.asList(Locale.getISOCountries());
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return countryCodes.contains(value);
  }

}
