package net.media.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.ConfigurationException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static java.util.Objects.nonNull;
import static javax.validation.Validation.buildDefaultValidatorFactory;

/**
 * @author shiva.b
 */
public class Utils {

  private static ObjectMapper mapper = new ObjectMapper();

  private static Validator defaultValidator = buildDefaultValidatorFactory().getValidator();

  public static ObjectMapper getMapper() {
    return mapper;
  }

  public static <T> List<T> copyList(List<T> input, Config config){
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      return new ArrayList<>(input);
    }
    return null;
  }

  public static <T> Set<T> copySet(Set<T> input, Config config){
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      return new HashSet<>(input);
    }
    return null;
  }

  public static <U,V> Map<U,V> copyMap(Map<U,V> input, Config config){
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      return new HashMap<>(input);
    }
    return null;
  }

  public static <T> void validate(T t) throws ConfigurationException {
    Set<ConstraintViolation<T>> invalids = defaultValidator.validate(t);
    StringBuilder sb = new StringBuilder("Following violations has been violated: \n");
    for (ConstraintViolation<T> constrainViolation : invalids) {
      sb.append(constrainViolation.getPropertyPath()).append("=>").append(constrainViolation.getMessage()).append('\n');
    }
    if (!invalids.isEmpty())
      throw new ConfigurationException(sb.toString());
  }
}
