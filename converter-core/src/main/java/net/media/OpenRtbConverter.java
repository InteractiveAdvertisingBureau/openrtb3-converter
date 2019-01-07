package net.media;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.utils.Utils;

import javax.naming.ConfigurationException;

import static java.util.Objects.isNull;

/**
 * @author shiva.b
 */
public class OpenRtbConverter {

  private Config config;

  private ConverterPlumber converterPlumber;

  public OpenRtbConverter(Config config) {
    this.config = config;
    converterPlumber = new ConverterPlumber();
  }

  /**
   *
   * @param overridingConfig
   * @param source
   * @param sourceClass
   * @param targetClass
   * @param <U>
   * @param <V>
   * @return
   */
  public  <U, V> V convert(Config overridingConfig, U source, Class<U> sourceClass, Class<V> targetClass) throws ConfigurationException {
    overridingConfig = inhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      Utils.validate(source);
    }
    Converter<U, V> converter = converterPlumber.getConverter(sourceClass, targetClass);
    return converter.map(source, overridingConfig);
  }

  /**
   *
   * @param source
   * @param sourceClass
   * @param targetClass
   * @param <U>
   * @param <V>
   * @return
   */
  public <U, V> V convert(U source, Class<U> sourceClass, Class<V> targetClass) throws ConfigurationException {
    return convert(null, source, sourceClass, targetClass);
  }

  public <U, V> void enhance(Config overridingConfig, U source, V target, Class<U> sourceClass,
                          Class<V> targetClass) throws ConfigurationException {
    overridingConfig = inhanceConfig(overridingConfig);
    if (shouldValidate(overridingConfig)) {
      Utils.validate(source);
    }
    Converter<U, V> converter = converterPlumber.getConverter(sourceClass, targetClass);
    converter.inhance(source, target, overridingConfig);
  }

  public <U, V> void enhance(U source, V target, Class<U> sourceClass,
                             Class<V> targetClass) {
    Converter<U, V> converter = converterPlumber.getConverter(sourceClass, targetClass);
    converter.inhance(source, target, null);
  }

  /**
   *
   * @param overridingConfig
   * @return
   */
  private Config inhanceConfig(Config overridingConfig) {
    if (isNull(overridingConfig)) {
      overridingConfig = new Config(config);
    }
    else {
      overridingConfig.updateEmptyFields(this.config);
    }
    return overridingConfig;
  }

  private boolean shouldValidate(Config overridingConfig) {
    return  overridingConfig.isValidate();
  }

}
