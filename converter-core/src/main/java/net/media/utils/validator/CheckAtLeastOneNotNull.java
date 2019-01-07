package net.media.utils.validator;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = CheckAtLeastOneNotNull.CheckAtLeastOneNotNullValidator.class)
@Documented
public @interface CheckAtLeastOneNotNull {
  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] fieldNames();

  class CheckAtLeastOneNotNullValidator implements ConstraintValidator<CheckAtLeastOneNotNull, Object> {
    private String[] fieldNames;
    public void initialize(CheckAtLeastOneNotNull constraint) {
      this.fieldNames = constraint.fieldNames();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
      if (object == null) {
        return true;
      }
      try {
        for (String fieldName:fieldNames){
          Object property = PropertyUtils.getProperty(object, fieldName);
          if (property!=null) {
            return true;
          }
        }
        ValidatorErrorHandler.setErrorMessage(context, "atleast one of the following should be present: " + Arrays.toString(fieldNames));
        return false;
      } catch (Exception e) {
        ValidatorErrorHandler.setErrorMessage(context, "atleast one of the following should be present: " + Arrays.toString(fieldNames));
        return false;
      }
    }
  }
}
