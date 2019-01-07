package net.media;

import net.media.config.Config;
import net.media.converters.Converter;

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
  public  <U, V> V convert(Config overridingConfig, U source, Class<U> sourceClass, Class<V>
    targetClass) {
    overridingConfig = inhanceConfig(overridingConfig);
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
  public <U, V> V convert(U source, Class<U> sourceClass, Class<V> targetClass) {
    return convert(null, source, sourceClass, targetClass);
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

}
