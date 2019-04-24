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

package net.media.driver;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;
import net.media.utils.validator.ValidatorUtils;

import javax.naming.ConfigurationException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Interface for interacting with openRtb Converter. {@link #config} default converter config {@link
 * Config} {@link #converterManager} contains the pipeline for converter dependencies {@link
 * ConverterManager} initialised while calling {@link OpenRtbConverter(Config)}
 *
 * <p>
 *
 * <ul>
 *   <li>{@link OpenRtbConverter(Config)} : instantiates OpenRtb converter object single object
 *       would be enough for the entire object
 *   <li>{@link #convert(Config, Object, Class, Class)} : converts source object from source class
 *       to target class, fields being passed in {@link Config} overrides the values for default
 *       {@link #config} for that particular call
 *   <li>{@link #convert(Object, Class, Class)} : uses {@link #convert(Config, Object, Class,
 *       Class)} without overriding config
 *   <li>{@link #enhance(Config, Object, Object, Class, Class)} : enhances the target object using
 *       the source object, fields being passed in {@link Config} overrides the values for default
 *       {@link #config} for that particular call
 *   <li>{@link #enhance(Object, Object, Class, Class)} uses {@link #enhance(Config, Object, Object,
 *       Class, Class)} without overriding config
 *  </ul>
 *
 * @author shiva.b
 * @since 1.0
 */
public class OpenRtbConverter {

  private Config config;

  private ConverterManager converterManager;

  public OpenRtbConverter(Config config) {
    this(config, new HashMap<>());
  }

  public OpenRtbConverter(Config config, Map<Conversion, Converter> overridenMap) {
    this.config = config;
    converterManager = new ConverterManager(overridenMap, config);
  }


  public <U, V> V convert(
      Config overridingConfig, U source, Class<U> sourceClass, Class<V> targetClass)
      throws ConfigurationException, OpenRtbConverterException {
    return convert(overridingConfig, source, sourceClass, targetClass, null);
  }

  public <U, V> String convert(
      Config overridingConfig, String source, Class<U> sourceClass, Class<V> targetClass)
      throws ConfigurationException, OpenRtbConverterException {
    U sourceObject = JacksonObjectMapperUtils.convertToObject(sourceClass, source);
    V targetObject = convert(overridingConfig, sourceObject, sourceClass, targetClass, null);
    return JacksonObjectMapperUtils.convertToJson(targetObject);
  }

  public <U, V> V convert(
      Config overridingConfig,
      U source,
      Class<U> sourceClass,
      Class<V> targetClass,
      Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    overridingConfig = enhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      ValidatorUtils.validate(source);
    }
    Provider converterProvider =
        converterManager.getConverterProvider(overridenMap, overridingConfig);
    Converter<U, V> converter = converterProvider.fetch(new Conversion<>(sourceClass, targetClass));
    return converter.map(source, overridingConfig, converterProvider);
  }

  public <U, V> String convert(
      Config overridingConfig,
      String source,
      Class<U> sourceClass,
      Class<V> targetClass,
      Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    U sourceObject = JacksonObjectMapperUtils.convertToObject(sourceClass, source);
    V targetObject =
        convert(overridingConfig, sourceObject, sourceClass, targetClass, overridenMap);
    return JacksonObjectMapperUtils.convertToJson(targetObject);
  }

  public <U, V> V convert(
      U source, Class<U> sourceClass, Class<V> targetClass, Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    return convert(null, source, sourceClass, targetClass, overridenMap);
  }

  public <U, V> String convert(
      String source,
      Class<U> sourceClass,
      Class<V> targetClass,
      Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    U sourceObject = JacksonObjectMapperUtils.convertToObject(sourceClass, source);
    V targetObject = convert(null, sourceObject, sourceClass, targetClass, overridenMap);
    return JacksonObjectMapperUtils.convertToJson(targetObject);
  }

  public <U, V> V convert(U source, Class<U> sourceClass, Class<V> targetClass)
      throws ConfigurationException, OpenRtbConverterException {
    return convert(null, source, sourceClass, targetClass, null);
  }

  public <U, V> String convert(String source, Class<U> sourceClass, Class<V> targetClass)
      throws ConfigurationException, OpenRtbConverterException {
    U sourceObject = JacksonObjectMapperUtils.convertToObject(sourceClass, source);
    V targetObject = convert(null, sourceObject, sourceClass, targetClass, null);
    return JacksonObjectMapperUtils.convertToJson(targetObject);
  }

  public <U, V> void enhance(
      Config overridingConfig, U source, V target, Class<U> sourceClass, Class<V> targetClass)
      throws ConfigurationException, OpenRtbConverterException {
    enhance(overridingConfig, source, target, sourceClass, targetClass, null);
  }

  public <U, V> void enhance(U source, V target, Class<U> sourceClass, Class<V> targetClass)
      throws OpenRtbConverterException, ConfigurationException {
    enhance(null, source, target, sourceClass, targetClass, null);
  }

  public <U, V> void enhance(
      U source,
      V target,
      Class<U> sourceClass,
      Class<V> targetClass,
      Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    enhance(null, source, target, sourceClass, targetClass, overridenMap);
  }

  public <U, V> void enhance(
      Config overridingConfig,
      U source,
      V target,
      Class<U> sourceClass,
      Class<V> targetClass,
      Map<Conversion, Converter> overridenMap)
      throws ConfigurationException, OpenRtbConverterException {
    overridingConfig = enhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      ValidatorUtils.validate(source);
    }
    Converter<U, V> converter = converterManager.getConverter(sourceClass, targetClass);
    converter.enhance(
        source,
        target,
        overridingConfig,
        converterManager.getConverterProvider(overridenMap, overridingConfig));
  }

  private Config enhanceConfig(Config overridingConfig) {
    if (isNull(overridingConfig)) {
      overridingConfig = new Config(config);
    } else {
      overridingConfig.updateEmptyFields(this.config);
    }
    return overridingConfig;
  }

  private boolean shouldValidate(Config overridingConfig) {
    return overridingConfig.getValidate();
  }
}
