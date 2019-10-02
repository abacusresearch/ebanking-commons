/*
 * FileFormatConstraint.java
 *
 * Creator:
 * 2/09/2019 14:41 hoefel
 *
 * Last Modification:
 * $Id: $
 *
 * Copyright (c) 2019 ABACUS Research AG, All Rights Reserved
 */
package ch.deeppay.spring.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FileFormatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileFormatConstraint {

  String message() default "Invalid FileFormat.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
