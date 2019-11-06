/*
 * CurrencyConstraint.java
 *
 * Creator:
 * 24/10/2019 18:31 hoefel
 *
 * Last Modification:
 * CurrencyConstraint: $
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
@Constraint(validatedBy = CurrencyConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {

  String message() default "Invalid Currency.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
