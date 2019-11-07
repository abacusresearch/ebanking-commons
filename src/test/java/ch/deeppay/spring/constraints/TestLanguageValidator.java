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
 * TestLanguageValidator.java
 *
 * Creator:
 * 24/10/2019 19:36 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
class TestLanguageValidator {

  private static Validator validator = null;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidLanguageDe() {
    MyLanguageTester tester = new MyLanguageTester();
    tester.language = "de";
    final Set<ConstraintViolation<MyLanguageTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testValidLanguageFr() {
    MyLanguageTester tester = new MyLanguageTester();
    tester.language = "fr";
    final Set<ConstraintViolation<MyLanguageTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testInvalidLanguageRrr() {
    MyLanguageTester tester = new MyLanguageTester();
    tester.language = "rrr";
    final Set<ConstraintViolation<MyLanguageTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyLanguageTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid Language.", constraintViolation.getMessage());
  }

  private static class MyLanguageTester {

    @LanguageConstraint public String language;
  }
}