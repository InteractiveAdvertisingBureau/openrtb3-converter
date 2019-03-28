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
public class ConverterManager {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterManager(Map<Conversion, Converter> overrideMap) {
    overrideMap.forEach((key, value) -> new ConverterProxy(() -> value).apply(key));
    converterProvider = new Provider<>(null);

    Convert25To30RequestManager converter25To30RequestPlumber = new Convert25To30RequestManager();
    Convert30To25RequestManager convert30To25RequestManager = new Convert30To25RequestManager();
    Convert25To30ResponseManager convert25To30ResponseManager = new Convert25To30ResponseManager();
    Convert30To25ResponseManager convert30To25ResponseManager = new Convert30To25ResponseManager();

    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      converter25To30RequestPlumber.getBidRequestToOpenRtbConverter().apply(new Conversion(BidRequest.class, OpenRTB.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      convert30To25RequestManager.getOpenRtbToBidRequestConverterProxy().apply(new Conversion(OpenRTB.class, BidRequest.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidResponse.class),
      convert30To25ResponseManager.getOpenRtbToBidResponseConverter().apply(new Conversion
        (OpenRTB.class, BidResponse.class)));
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      convert25To30ResponseManager.getBidResponseToOpenRtbConverterProxy().apply(new Conversion(BidResponse.class, OpenRTB.class)));
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
