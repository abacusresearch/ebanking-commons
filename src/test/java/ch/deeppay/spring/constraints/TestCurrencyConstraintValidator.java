package ch.deeppay.spring.constraints;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * TestCurrencyConstraintValidator.java
 *
 * Creator:
 * 24/10/2019 19:14 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
class TestCurrencyConstraintValidator {

  private static Validator validator = null;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidCurrencyAUD() {
    MyCurrencyTester tester = new MyCurrencyTester();
    tester.currency = "AUD";
    final Set<ConstraintViolation<MyCurrencyTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testValidCurrencyCHF() {
    MyCurrencyTester tester = new MyCurrencyTester();
    tester.currency = "CHF";
    final Set<ConstraintViolation<MyCurrencyTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testInvalidCurrency() {
    MyCurrencyTester tester = new MyCurrencyTester();
    tester.currency = "CCC";
    final Set<ConstraintViolation<MyCurrencyTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyCurrencyTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid Currency.", constraintViolation.getMessage());
  }

  @Test
  public void testInvalidCurrencyNull() {
    MyCurrencyTester tester = new MyCurrencyTester();
    final Set<ConstraintViolation<MyCurrencyTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyCurrencyTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid Currency.", constraintViolation.getMessage());
  }

  private static class MyCurrencyTester {

    @CurrencyConstraint public String currency;
  }
}