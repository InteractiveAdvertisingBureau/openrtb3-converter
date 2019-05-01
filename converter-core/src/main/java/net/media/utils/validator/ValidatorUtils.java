package net.media.utils.validator;

import net.media.driver.OpenRtbConverter;
import net.media.exceptions.OpenRtbConverterException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

/**
 * Created by rajat.go on 21/04/19.
 */
public class ValidatorUtils {

  private static Validator defaultValidator = buildDefaultValidatorFactory().getValidator();

  public static <T> void validate(T t) throws OpenRtbConverterException {
    Set<ConstraintViolation<T>> invalids = defaultValidator.validate(t);
    StringBuilder sb = new StringBuilder("Following violations has been violated: \n");
    for (ConstraintViolation<T> constrainViolation : invalids) {
      sb.append(constrainViolation.getPropertyPath())
        .append("=>")
        .append(constrainViolation.getMessage())
        .append('\n');
    }
    if (!invalids.isEmpty()) throw new OpenRtbConverterException(sb.toString());
  }
}
