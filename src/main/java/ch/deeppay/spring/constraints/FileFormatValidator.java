package ch.deeppay.spring.constraints;

import ch.deeppay.util.FileFormat;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileFormatValidator implements ConstraintValidator<FileFormatConstraint, String> {

  @Autowired(required = false)
  private final ExtendedFileFormatValidatorInterface extendedFileFormatValidator;

  /**
   * Default constructor for tests.
   */
  public FileFormatValidator() {
    extendedFileFormatValidator = null;
  }

  public FileFormatValidator(ExtendedFileFormatValidatorInterface extendedFileFormatValidator) {
    this.extendedFileFormatValidator = extendedFileFormatValidator;
  }

  @Override
  public void initialize(FileFormatConstraint constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(extendedFileFormatValidator != null){
      return extendedFileFormatValidator.isValid(value, context);
    }

    //Default case, no external validator available.
    try {
      if (value == null || value.isEmpty()) {
        return true;
      }
      FileFormat.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }
}
