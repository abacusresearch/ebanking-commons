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
 * TestCountryValidator.java
 *
 * Creator:
 * 29/10/2019 11:36 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
class TestCountryValidator {

  private static Validator validator = null;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidCountryDE() {
    MyCountryTester tester = new MyCountryTester();
    tester.country = "DE";
    final Set<ConstraintViolation<MyCountryTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testValidCountryCH() {
    MyCountryTester tester = new MyCountryTester();
    tester.country = "ch";
    final Set<ConstraintViolation<MyCountryTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testInvalidCountryChh() {
    MyCountryTester tester = new MyCountryTester();
    tester.country = "chh";
    final Set<ConstraintViolation<MyCountryTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyCountryTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid Country.", constraintViolation.getMessage());
  }

  @Test
  public void testInvalidCountryEmpty() {
    MyCountryTester tester = new MyCountryTester();
    final Set<ConstraintViolation<MyCountryTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyCountryTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid Country.", constraintViolation.getMessage());
  }

  private static class MyCountryTester {

    @CountryConstraint public String country;
  }
}