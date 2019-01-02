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

/**
 * @author shiva.b
 */
public class OpenRtbConverter {

  private Config config;

  private Provider<Conversion, Converter> converterProvider;

  private OpenRtb24To3MapperImpl openRtb24To3Mapper;

  private OpenRtbConverter(Config config) {
    this.config = config;
    initConversionProvider();
  }

  private <U, V> V convert(Config config, U source, Class<U> sourceClass, Class<V> targetClass) {
    Converter<U, V> converter = converterProvider.fetch(new Conversion(sourceClass, targetClass));
    return converter.map(source, config);
  }

  private void initConversionProvider() {
    converterProvider = new Provider<>(null);
    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      new BidRequestToOpenRtbConverter());
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      new BidResponseToOpenRtbConverter());
  }
}
