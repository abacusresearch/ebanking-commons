/*
 * LanguageConstraint.java
 *
 * Creator:
 * 24/10/2019 18:36 hoefel
 *
 * Last Modification:
 * LanguageConstraint: $
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
@Constraint(validatedBy = LanguageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LanguageConstraint {

  String message() default "Invalid Language.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
