package net.media.driver;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.utils.ConverterProxy;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;

import static java.util.Objects.isNull;

/**
 * Interface for interacting with openRtb Converter.
 * {@link #config} default converter config {@link Config}
 * {@link #converterManager} contains the pipeline for converter dependencies
 * {@link ConverterManager}  initialised while calling {@link OpenRtbConverter(Config)}
 *
 * <ul>
 *   <li>{@link OpenRtbConverter(Config)} : instantiates OpenRtb converter object single object
 *   would be enough for the entire object
 *   </li>
 *   <li>{@link #convert(Config, Object, Class, Class)} : converts source object from source
 *   class to target class, fields being passed in {@link Config} overrides the values for
 *   default {@link #config} for that particular call
 *   </li>
 *   <li>{@link #convert(Object, Class, Class)} : uses
 *    {@link #convert(Config, Object, Class, Class)} without overriding config
 *   </li>
 *   <li>{@link #enhance(Config, Object, Object, Class, Class)} : enhances the target object
 *   using the source object, fields being passed in {@link Config} overrides the values for
 *   default {@link #config} for that particular call
 *   </li>
 *   <li>{@link #enhance(Object, Object, Class, Class)} uses
 *   {@link #enhance(Config, Object, Object, Class, Class)} without overriding config
 *   </li>
 * <ul/>
 *
 * @author shiva.b
 * @since 1.0
 */
public class OpenRtbConverter {

  private Config config;

  private ConverterManager converterManager;

  private ConverterProxy converterProxy;

  public OpenRtbConverter(Config config) {
    this(config, new HashMap<>());
  }

  public OpenRtbConverter(Config config, Map<Conversion, Converter> overridenMap) {
    this.config = config;
    this.converterProxy =  new ConverterProxy(()-> null);
    converterManager = new ConverterManager(overridenMap);
  }

  /**
   *
   * @param overridingConfig
   * @param source
   * @param sourceClass
   * @param targetClass
   * @param <U> source class type
   * @param <V> target class type
   * @return <class>V</class> target class
   */
  public  <U, V> V convert(Config overridingConfig, U source, Class<U> sourceClass, Class<V>
    targetClass) throws ConfigurationException, OpenRtbConverterException {
    overridingConfig = enhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      Utils.validate(source);
    }
    Converter<U, V> converter = converterManager.getConverter(sourceClass, targetClass);
    return converter.map(source, overridingConfig);
  }

  /**
   *
   * @param source
   * @param sourceClass source object
   * @param targetClass target object
   * @param <U> source class type
   * @param <V> target class type
   * @return <class>V</class> target class
   */
  public <U, V> V convert(U source, Class<U> sourceClass, Class<V> targetClass) throws
    ConfigurationException, OpenRtbConverterException {
    return convert(null, source, sourceClass, targetClass);
  }

  /**
   *
   * @param overridingConfig
   * @param source
   * @param target
   * @param sourceClass
   * @param targetClass
   * @param <U> source class type
   * @param <V> target class type
   * @throws ConfigurationException
   * @throws OpenRtbConverterException
   */
  public <U, V> void enhance(Config overridingConfig, U source, V target, Class<U> sourceClass,
                          Class<V> targetClass) throws ConfigurationException, OpenRtbConverterException {
    overridingConfig = enhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      Utils.validate(source);
    }
    Converter<U, V> converter = converterManager.getConverter(sourceClass, targetClass);
    converter.enhance(source, target, overridingConfig);
  }

  /**
   *
   * @param source
   * @param target
   * @param sourceClass
   * @param targetClass
   * @param <U> source class type
   * @param <V> target class type
   * @throws OpenRtbConverterException
   */
  public <U, V> void enhance(U source, V target, Class<U> sourceClass,
                             Class<V> targetClass) throws OpenRtbConverterException {
    Converter<U, V> converter = converterManager.getConverter(sourceClass, targetClass);
    converter.enhance(source, target, null);
  }

  /**
   *
   * @param overridingConfig
   * @return
   */
  private Config enhanceConfig(Config overridingConfig) {
    if (isNull(overridingConfig)) {
      overridingConfig = new Config(config);
    }
    else {
      overridingConfig.updateEmptyFields(this.config);
    }
    return overridingConfig;
  }

  private boolean shouldValidate(Config overridingConfig) {
    return  overridingConfig.getValidate();
  }

}
