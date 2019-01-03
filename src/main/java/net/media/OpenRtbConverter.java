package net.media;

import net.media.config.Config;
import net.media.converters.BidRequestToOpenRtbConverter;
import net.media.converters.BidResponseToOpenRtbConverter;
import net.media.converters.Converter;
import net.media.mapper.OpenRtb24To3MapperImpl;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * @author shiva.b
 */
public class OpenRtbConverter {

  private Config config;

  private ConverterPlumber converterPlumber;

  private OpenRtbConverter(Config config) {
    this.config = config;
    converterPlumber = new ConverterPlumber();
  }

  public  <U, V> V convert(Config overridingConfig, U source, Class<U> sourceClass, Class<V>
    targetClass) {
    overridingConfig = inhanceConfig(overridingConfig);
    Converter<U, V> converter = converterPlumber.getConverter(sourceClass, targetClass);
    return converter.map(source, overridingConfig);
  }

  public <U, V> V convert(U source, Class<U> sourceClass, Class<V> targetClass) {
    return convert(null, source, sourceClass, targetClass);
  }

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
