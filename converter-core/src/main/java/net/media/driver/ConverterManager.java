package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.request25toRequest30.BidRequestToOpenRtbConverter;
import net.media.converters.request30toRequest25.OpenRtbToBidRequestConverter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.utils.ConverterProxy;
import net.media.utils.Provider;

import java.util.Map;

/**
 * @author shiva.b
 */
@SuppressWarnings("unchecked")
public class ConverterManager {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterManager(Map<Conversion, Converter> overrideMap) {
    converterProvider = new Provider<>(null);
    new Convert25To30RequestManager(converterProvider);
    new Convert30To25RequestManager(converterProvider);
    new Convert25To30ResponseManager(converterProvider);
    new Convert30To25ResponseManager(converterProvider);
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
