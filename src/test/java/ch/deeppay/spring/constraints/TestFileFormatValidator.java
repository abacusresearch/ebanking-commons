package ch.deeppay.spring.constraints;

import ch.deeppay.util.FileFormat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * TestFileFormatValidator.java
 *
 * Creator:
 * 29/10/2019 11:41 hoefel
 *
 * Last Modification:
 * : $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
class TestFileFormatValidator {

  private static Validator validator = null;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidFileFormatMT940() {
    MyFileFormatTester tester = new MyFileFormatTester();
    tester.fileFormat = FileFormat.MT940.name();
    final Set<ConstraintViolation<MyFileFormatTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testValidFileFormatCAMT052() {
    MyFileFormatTester tester = new MyFileFormatTester();
    tester.fileFormat = FileFormat.CAMT052.name();
    final Set<ConstraintViolation<MyFileFormatTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  @Test
  public void testInvalidFileFormat() {
    MyFileFormatTester tester = new MyFileFormatTester();
    tester.fileFormat = "invalid";
    final Set<ConstraintViolation<MyFileFormatTester>> result = validator.validate(tester);
    assertEquals(1, result.size());
    final ConstraintViolation<MyFileFormatTester> constraintViolation = result.iterator().next();
    assertEquals("Invalid FileFormat.", constraintViolation.getMessage());
  }

  @Test
  public void testInvalidFileFormatEmpty() {
    MyFileFormatTester tester = new MyFileFormatTester();
    final Set<ConstraintViolation<MyFileFormatTester>> result = validator.validate(tester);
    assertEquals(0, result.size());
  }

  private static class MyFileFormatTester {

    @FileFormatConstraint public String fileFormat;
  }
}