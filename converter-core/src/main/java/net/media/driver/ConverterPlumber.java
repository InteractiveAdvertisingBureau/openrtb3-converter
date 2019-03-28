package net.media.driver;

import net.media.converters.Converter;
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
public class ConverterPlumber {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterPlumber(Map<Conversion, Converter> overrideMap) {
    overrideMap.forEach((key, value) -> new ConverterProxy(() -> value).apply(key));
    converterProvider = new Provider<>(null);

    Converter25To30RequestPlumber converter25To30RequestPlumber = new Converter25To30RequestPlumber();
    Converter30To25RequestPlumber converter30To25RequestPlumber = new Converter30To25RequestPlumber();
    Converter25To30ResponsePlumber converter25To30ResponsePlumber = new Converter25To30ResponsePlumber();
    Converter30To25ResponsePlumber converter30To25ResponsePlumber = new Converter30To25ResponsePlumber();

    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      converter25To30RequestPlumber.getBidRequestToOpenRtbConverter().apply(new Conversion(BidRequest.class, OpenRTB.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      converter30To25RequestPlumber.getOpenRtbToBidRequestConverterProxy().apply(new Conversion(OpenRTB.class, BidRequest.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidResponse.class),
      converter30To25ResponsePlumber.getOpenRtbToBidResponseConverter().apply(new Conversion
        (OpenRTB.class, BidResponse.class)));
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      converter25To30ResponsePlumber.getBidResponseToOpenRtbConverterProxy().apply(new Conversion(BidResponse.class, OpenRTB.class)));
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
