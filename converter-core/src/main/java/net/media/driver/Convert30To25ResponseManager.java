package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response30toresponse25.*;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Asset;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Media;
import net.media.openrtb3.Native;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
import net.media.utils.Provider;

/**
 * Created by shiva.b on 28/03/19.
 */
@SuppressWarnings("unchecked")
public class Convert30To25ResponseManager {

  public Convert30To25ResponseManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(LinkAsset.class, Link.class), new LinkAssetToLinkConverter());
    converterProvider.register(new Conversion<>(Asset.class, AssetResponse.class), new Asset30ToAsset25Converter());
    converterProvider.register(new Conversion<>(Native.class, NativeResponse.class), new Native30ToNative10Converter());
    converterProvider.register(new Conversion<>(Display.class, net.media.openrtb25.response.Bid
      .class), new DisplayToBidConverter());
    converterProvider.register(new Conversion<>(Video.class, net.media.openrtb25.response.Bid
      .class), new VideoToBidConverter());
    converterProvider.register(new Conversion<>(Audio.class, net.media.openrtb25.response.Bid
      .class), new AudioToBidConverter());
    converterProvider.register(new Conversion<>(Audit.class, net.media.openrtb25.response.Bid
      .class), new AuditToBidConverter());
    converterProvider.register(new Conversion<>(Ad.class, net.media.openrtb25.response.Bid.class),
      new AdToBidConverter());
    converterProvider.register(new Conversion<>(Media.class, net.media.openrtb25.response.Bid
      .class), new MediatoBidConverter());
    converterProvider.register(new Conversion<>(Bid.class, net.media.openrtb25.response.Bid.class),
      new Bid30ToBid25Converter());
    converterProvider.register(new Conversion<>(Seatbid.class, SeatBid.class), new SeatBid30ToSeatBid25Converter());
    converterProvider.register(new Conversion<>(OpenRTB.class, BidResponse.class), new OpenRtbResponseToBidResponseConverter());
  }

}
