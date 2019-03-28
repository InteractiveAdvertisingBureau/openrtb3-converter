package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response30toresponse25.AdToBidConverter;
import net.media.converters.response30toresponse25.Asset30ToAsset25Converter;
import net.media.converters.response30toresponse25.AuditToBidConverter;
import net.media.converters.response30toresponse25.Bid30ToBid25Converter;
import net.media.converters.response30toresponse25.DisplayToBidConverter;
import net.media.converters.response30toresponse25.LinkAssetToLinkConverter;
import net.media.converters.response30toresponse25.MediatoBidConverter;
import net.media.converters.response30toresponse25.Native30ToNative10Converter;
import net.media.converters.response30toresponse25.OpenRtbResponseToBidResponseConverter;
import net.media.converters.response30toresponse25.SeatBid30ToSeatBid25Converter;
import net.media.converters.response30toresponse25.VideoToBidConverter;
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
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
import net.media.utils.ConverterProxy;

/**
 * Created by shiva.b on 28/03/19.
 */
@SuppressWarnings("unchecked")
public class Convert30To25ResponseManager {

  public ConverterProxy getOpenRtbToBidResponseConverter() {
    return openRtbToBidResponseConverter;
  }

  private ConverterProxy linkAssetToLinkConverter = new ConverterProxy(LinkAssetToLinkConverter::new);

  private ConverterProxy asset30Asset25Converter = new ConverterProxy(() -> {
    Converter<LinkAsset, Link> linkAssetLinkConverter = linkAssetToLinkConverter.apply(new
      Conversion(LinkAsset.class, Link.class));
    return new Asset30ToAsset25Converter(linkAssetLinkConverter);
  });

  private ConverterProxy nativeNativeResponseConverter = new ConverterProxy(() -> {
    Converter<LinkAsset, Link> linkAssetLinkConverter = linkAssetToLinkConverter.apply(new
      Conversion(LinkAsset.class, Link.class));
    Converter<Asset, AssetResponse> assetAssetResponseConverter = asset30Asset25Converter.apply
      (new Conversion(Asset.class, AssetResponse.class));
    return new Native30ToNative10Converter(assetAssetResponseConverter, linkAssetLinkConverter);
  });

  private ConverterProxy displayToBidConverter = new ConverterProxy(() -> {
    Converter<Native, NativeResponse> nativeToNativeResponseConverter =
      nativeNativeResponseConverter.apply(new Conversion(Native.class, NativeResponse.class));
    return new DisplayToBidConverter(nativeToNativeResponseConverter);
  });

  private ConverterProxy videoToBidConverter = new ConverterProxy(VideoToBidConverter::new);

  private ConverterProxy audioToBidConverter = new ConverterProxy(AuditToBidConverter::new);

  private ConverterProxy auditToBidConverter = new ConverterProxy(AuditToBidConverter::new);

  private ConverterProxy adToBidConverter = new ConverterProxy(() -> {
    Converter<Display, net.media.openrtb25.response.Bid> displayBidConverter = displayToBidConverter
      .apply(new Conversion(Display.class, net.media.openrtb25.response.Bid.class));
    Converter<Video, net.media.openrtb25.response.Bid> videoBidConverter = videoToBidConverter
      .apply(new Conversion(Video.class, net.media.openrtb25.response.Bid.class));
    Converter<Audio, net.media.openrtb25.response.Bid> audioBidConverter = audioToBidConverter
      .apply(new Conversion(Audio.class, net.media.openrtb25.response.Bid.class));
    Converter<Audit, net.media.openrtb25.response.Bid> auditBidConverter = auditToBidConverter
      .apply(new Conversion(Audit.class, net.media.openrtb25.response.Bid.class));
    return new AdToBidConverter(
      displayBidConverter,
      videoBidConverter,
      audioBidConverter,
      auditBidConverter
    );
  });

  private ConverterProxy mediaToBidConverter = new ConverterProxy(() -> {
    Converter<Ad, net.media.openrtb25.response.Bid> adBidConverter = adToBidConverter.apply(new
      Conversion(Ad.class, net.media.openrtb25.response.Bid.class));
    return new MediatoBidConverter(adBidConverter);
  });

  private ConverterProxy bid30Tobid25Converter = new ConverterProxy(() -> {
    Converter<Media, net.media.openrtb25.response.Bid> mediaBidConverter = mediaToBidConverter
      .apply(new Conversion(Media.class, net.media.openrtb25.response.Bid.class));
    return new Bid30ToBid25Converter(mediaBidConverter);
  });

  private ConverterProxy seatBid30ToSeatBid25Converter = new ConverterProxy(() -> {
    Converter<Bid, net.media.openrtb25.response.Bid> bidBidConverter = bid30Tobid25Converter
      .apply(new Conversion(Bid.class, net.media.openrtb25.response.Bid.class));
    return new SeatBid30ToSeatBid25Converter(bidBidConverter);
  });

  private ConverterProxy openRtbToBidResponseConverter = new ConverterProxy(() -> {
    Converter<Seatbid, SeatBid> seatbidSeatBidConverter = seatBid30ToSeatBid25Converter.apply(new
      Conversion(Seatbid.class, SeatBid.class));
    return new OpenRtbResponseToBidResponseConverter(seatbidSeatBidConverter);
  });
}
