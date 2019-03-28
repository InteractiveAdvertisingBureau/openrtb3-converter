package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response30toresponse25.AdToBidConverter;
import net.media.converters.response30toresponse25.Asset30ToAsset24Converter;
import net.media.converters.response30toresponse25.AuditToBidConverter;
import net.media.converters.response30toresponse25.Bid30ToBid24Converter;
import net.media.converters.response30toresponse25.DisplayToBidConverter;
import net.media.converters.response30toresponse25.LinkAssetToLinkConverter;
import net.media.converters.response30toresponse25.MediatoBidConverter;
import net.media.converters.response30toresponse25.Native30ToNative10Converter;
import net.media.converters.response30toresponse25.OpenRtbResponseToBidResponseConverter;
import net.media.converters.response30toresponse25.SeatBid30ToSeatBid24Converter;
import net.media.converters.response30toresponse25.VideoToBidConverter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Asset;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Media;
import net.media.openrtb3.Native;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
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
