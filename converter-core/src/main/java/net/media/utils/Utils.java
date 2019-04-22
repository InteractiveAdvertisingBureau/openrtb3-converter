/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;

import javax.naming.ConfigurationException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.nonNull;
import static javax.validation.Validation.buildDefaultValidatorFactory;

/** @author shiva.b */
public class Utils {

  private static ObjectMapper mapper = new ObjectMapper();

  private static Validator defaultValidator = buildDefaultValidatorFactory().getValidator();

  public static ObjectMapper getMapper() {
    return mapper;
  }

  public static <U> U convertToObject(Class<U> klass, String json)
      throws OpenRtbConverterException {
    try {
      return getMapper().readValue(json, klass);
    } catch (IOException e) {
      throw new OpenRtbConverterException("error while converting json to object", e);
    }
  }

  public static String convertToJson(Object object) throws OpenRtbConverterException {
    try {
      return getMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new OpenRtbConverterException("error while converting object to json", e);
    }
  }

  public static <U, V> Map<U, V> copyMap(Map<U, V> input, Config config) {
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      return new HashMap<>(input);
    }
    return null;
  }

  public static <T> Collection<T> copyCollection(Collection<T> input, Config config) {
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      if (input instanceof Set) {
        return new HashSet<>(input);
      } else {
        return new ArrayList<>(input);
      }
    }
    return null;
  }

  public static <T> void validate(T t) throws ConfigurationException {
    Set<ConstraintViolation<T>> invalids = defaultValidator.validate(t);
    StringBuilder sb = new StringBuilder("Following violations has been violated: \n");
    for (ConstraintViolation<T> constrainViolation : invalids) {
      sb.append(constrainViolation.getPropertyPath())
          .append("=>")
          .append(constrainViolation.getMessage())
          .append('\n');
    }
    if (!invalids.isEmpty()) throw new ConfigurationException(sb.toString());
  }
}
