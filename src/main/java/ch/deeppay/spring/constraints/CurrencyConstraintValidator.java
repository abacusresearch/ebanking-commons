/*
 * CurrencyConstraintValidator.java
 *
 * Creator:
 * 24/10/2019 18:34 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
package ch.deeppay.spring.constraints;

import org.joda.money.CurrencyUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConstraintValidator implements ConstraintValidator<CurrencyConstraint, String> {

  private final List<String> currencies = new ArrayList<>();

  @Override
  public void initialize(CurrencyConstraint constraintAnnotation) {
    if (currencies.isEmpty()) {
      final List<CurrencyUnit> currencyUnits = CurrencyUnit.registeredCurrencies();
      currencyUnits.forEach(currencyUnit -> currencies.add(currencyUnit.getCode()));
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return currencies.contains(value);
  }
}
