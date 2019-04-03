package net.media.driver;

import com.sun.tools.javac.util.Convert;

import net.media.converters.Converter;
import net.media.converters.response25toresponse30.Asset25ToAsset30Converter;
import net.media.converters.response25toresponse30.Bid25ToBid30Converter;
import net.media.converters.response25toresponse30.Bid25ToMediaConverter;
import net.media.converters.response25toresponse30.BidResponseToOpenRtbConverter;
import net.media.converters.response25toresponse30.BidResponseToResponseConverter;
import net.media.converters.response25toresponse30.BidToAdConverter;
import net.media.converters.response25toresponse30.BidToAudioConverter;
import net.media.converters.response25toresponse30.BidToAuditConverter;
import net.media.converters.response25toresponse30.BidToDisplayConverter;
import net.media.converters.response25toresponse30.BidToVideoConverter;
import net.media.converters.response25toresponse30.LinkToLinkAssetConverter;
import net.media.converters.response25toresponse30.Native25ToNative30Converter;
import net.media.converters.response25toresponse30.SeatBid25ToSeatBid30Converter;
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

/**
 * Created by shiva.b on 28/03/19.
 */
@SuppressWarnings("unchecked")
public class Convert25To30ResponseManager {

  public Convert25To30ResponseManager(Provider<Conversion, Converter> converterProvider) {
    converterProvider.register(new Conversion(Link.class, LinkAsset.class), new LinkToLinkAssetConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.request.Asset.class, Asset
      .class), new Asset25ToAsset30Converter());
    converterProvider.register(new Conversion(NativeResponse.class, Native.class), new Native25ToNative30Converter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Display
      .class), new BidToDisplayConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Audit
      .class), new BidToAuditConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Audio
      .class), new BidToAudioConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Video
      .class), new BidToVideoConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Ad.class),
      new BidToAdConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Media
      .class), new Bid25ToMediaConverter());
    converterProvider.register(new Conversion(net.media.openrtb25.response.Bid.class, Bid.class),
      new Bid25ToBid30Converter());
    converterProvider.register(new Conversion(SeatBid.class, Seatbid.class), new SeatBid25ToSeatBid30Converter());
    converterProvider.register(new Conversion(BidResponse.class, Response.class), new BidResponseToResponseConverter());
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class), new BidResponseToOpenRtbConverter());
  }

}
