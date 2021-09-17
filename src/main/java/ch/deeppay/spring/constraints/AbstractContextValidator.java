package ch.deeppay.spring.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to validate a object. e.g. context object like statementcontext
 * @param <A> Type of annotation
 * @param <C> Type of object that will be validated
 */
public abstract class AbstractContextValidator<A extends Annotation, C> implements ConstraintValidator<A, C> {

  protected final List<SingleValidator<C>> validators = new ArrayList<>();

  @Override
  public void initialize(final A constraintAnnotation) {
    registerValidators(validators);
  }

  protected abstract void registerValidators(List<SingleValidator<C>> validators);


  @Override
  public boolean isValid(final C value, final ConstraintValidatorContext context) {
    for (SingleValidator<C> validator : validators) {
      if (!validator.isValid(context, value)) {
        return false;
      }
    }
    return true;
  }

  protected boolean addError(ConstraintValidatorContext context, String propertyNode, String message) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message)
           .addPropertyNode(propertyNode)
           .addConstraintViolation();
    return false;
  }

  public interface SingleValidator<C> {

    boolean isValid(ConstraintValidatorContext validatorContext, C value);
  }

}