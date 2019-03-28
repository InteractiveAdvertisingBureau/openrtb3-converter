package net.media;

import net.media.converters.Converter;
import net.media.converters.response25toresponse30.Asset24ToAsset30Converter;
import net.media.converters.response25toresponse30.Bid24ToBid30Converter;
import net.media.converters.response25toresponse30.Bid24ToMediaConverter;
import net.media.converters.response25toresponse30.BidResponseToOpenRtbConverter;
import net.media.converters.response25toresponse30.BidResponseToResponseConverter;
import net.media.converters.response25toresponse30.BidToAdConverter;
import net.media.converters.response25toresponse30.BidToAudioConverter;
import net.media.converters.response25toresponse30.BidToAuditConverter;
import net.media.converters.response25toresponse30.BidToDisplayConverter;
import net.media.converters.response25toresponse30.BidToVideoConverter;
import net.media.converters.response25toresponse30.LinkToLinkAssetConverter;
import net.media.converters.response25toresponse30.Native24ToNative30Converter;
import net.media.converters.response25toresponse30.SeatBid24ToSeatBid30Converter;
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
    Converter<OpenRTB, BidResponse> openRTBBidResponseConverter = openRtbToBidResponseConverter
      .apply(new Conversion(OpenRTB.class, BidResponse.class));
    Converter<BidResponse, OpenRTB> bidResponseOpenRTBConverter = bidResponseToOpenRtbConverter
      .apply(new Conversion(BidResponse.class, OpenRTB.class));
    converterProvider = new Provider<>(null);
    Converter25To30RequestPlumber converter25To30RequestPlumber = new Converter25To30RequestPlumber();
    Converter30To25RequestPlumber converter30To25RequestPlumber = new Converter30To25RequestPlumber();
    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      converter25To30RequestPlumber.getBidRequestToOpenRtbConverter().apply(new Conversion(BidRequest.class, OpenRTB.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      converter30To25RequestPlumber.getOpenRtbToBidRequestConverterProxy().apply(new Conversion(OpenRTB.class, BidRequest.class)));
    converterProvider.register(new Conversion(OpenRTB.class, BidResponse.class), openRTBBidResponseConverter);
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class), bidResponseOpenRTBConverter);
  }

  private ConverterProxy linkAssetToLinkConverter = new ConverterProxy(LinkAssetToLinkConverter::new);

  private ConverterProxy asset30Asset24Converter = new ConverterProxy(() -> {
    Converter<LinkAsset, Link> linkAssetLinkConverter = linkAssetToLinkConverter.apply(new
      Conversion(LinkAsset.class, Link.class));
    return new Asset30ToAsset24Converter(linkAssetLinkConverter);
  });

  private ConverterProxy nativeNativeResponseConverter = new ConverterProxy(() -> {
    Converter<LinkAsset, Link> linkAssetLinkConverter = linkAssetToLinkConverter.apply(new
      Conversion(LinkAsset.class, Link.class));
    Converter<Asset, AssetResponse> assetAssetResponseConverter = asset30Asset24Converter.apply
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

  private ConverterProxy bid30Tobid24Converter = new ConverterProxy(() -> {
    Converter<Media, net.media.openrtb25.response.Bid> mediaBidConverter = mediaToBidConverter
      .apply(new Conversion(Media.class, net.media.openrtb25.response.Bid.class));
    return new Bid30ToBid24Converter(mediaBidConverter);
  });

  private ConverterProxy seatBid30ToSeatBid24Converter = new ConverterProxy(() -> {
    Converter<Bid, net.media.openrtb25.response.Bid> bidBidConverter = bid30Tobid24Converter
      .apply(new Conversion(Bid.class, net.media.openrtb25.response.Bid.class));
    return new SeatBid30ToSeatBid24Converter(bidBidConverter);
  });

  private ConverterProxy openRtbToBidResponseConverter = new ConverterProxy(() -> {
    Converter<Seatbid, SeatBid> seatbidSeatBidConverter = seatBid30ToSeatBid24Converter.apply(new
      Conversion(Seatbid.class, SeatBid.class));
    return new OpenRtbResponseToBidResponseConverter(seatbidSeatBidConverter);
  });

  private ConverterProxy linkToLinkAssetConverter = new ConverterProxy(LinkToLinkAssetConverter::new);

  private ConverterProxy assetResponseToAssetConverter = new ConverterProxy(() -> {
    Converter<Link, LinkAsset> linkLinkAssetConverter = linkToLinkAssetConverter.apply(new
      Conversion(Link.class, LinkAsset.class));
    return new Asset24ToAsset30Converter(linkLinkAssetConverter);
  });

  private ConverterProxy nativeResponseToNativeConverter = new ConverterProxy(() -> {
    Converter<Link, LinkAsset> linkLinkAssetConverter = linkToLinkAssetConverter.apply(
      new Conversion(Link.class, LinkAsset.class));
    Converter<AssetResponse, Asset> assetAssetConverter =
      assetResponseToAssetConverter.apply(
        new Conversion(net.media.openrtb25.request.Asset.class, Asset.class));
    return new Native24ToNative30Converter(linkLinkAssetConverter, assetAssetConverter);
  });

  private ConverterProxy bidToDisplayConverter = new ConverterProxy(() -> {
    Converter<NativeResponse, Native> nativeResponseNativeConverter =
      nativeResponseToNativeConverter.apply(new Conversion(NativeResponse.class, Native.class));
    return new BidToDisplayConverter(nativeResponseNativeConverter);
  });

  private ConverterProxy bidToAuditConverter = new ConverterProxy(BidToAuditConverter::new);

  private ConverterProxy bidToAudioConverter = new ConverterProxy(BidToAudioConverter::new);

  private ConverterProxy bidToVideoConverter = new ConverterProxy(BidToVideoConverter::new);

  private ConverterProxy bidToAdConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.response.Bid, Display> bidDisplayConverter =
      bidToDisplayConverter.apply(
        new Conversion(net.media.openrtb25.response.Bid.class, Display.class));

    Converter<net.media.openrtb25.response.Bid, Audit> bidAuditConverter =
      bidToAuditConverter.apply(
      new Conversion(net.media.openrtb25.response.Bid.class, Audit.class));

    Converter<net.media.openrtb25.response.Bid, Audio> bidAudioConverter =
      bidToAudioConverter.apply(
      new Conversion(net.media.openrtb25.response.Bid.class, Audio.class));

    Converter<net.media.openrtb25.response.Bid, Video> bidVideoConverter =
      bidToVideoConverter.apply(
        new Conversion(net.media.openrtb25.response.Bid.class, Video.class));

    return new BidToAdConverter(
      bidDisplayConverter,
      bidAudioConverter,
      bidVideoConverter,
      bidAuditConverter
    );
  });

  private ConverterProxy bidToMediaConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.response.Bid, Ad> bidAdConverter = bidToAdConverter.apply(new
      Conversion(net.media.openrtb25.response.Bid.class, Ad.class));
    return new Bid24ToMediaConverter(bidAdConverter);
  });

  private ConverterProxy bid24ToBid30Converter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.response.Bid, Media> bidMediaConverter = bidToMediaConverter
      .apply(new Conversion(net.media.openrtb25.response.Bid.class, Media.class));
    return new Bid24ToBid30Converter(bidMediaConverter);
  });

  private ConverterProxy seatBid24ToSeatBid30Converter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.response.Bid, Bid> bidBidConverter = bid24ToBid30Converter
      .apply(new Conversion(net.media.openrtb25.response.Bid.class, Bid.class));
    return new SeatBid24ToSeatBid30Converter(bidBidConverter);
  });

  private ConverterProxy bidResponseToResponseConverter = new ConverterProxy(() -> {
    Converter<SeatBid, Seatbid> seatbidSeatBidConverter =
      seatBid24ToSeatBid30Converter.apply(new Conversion(SeatBid.class, Seatbid.class));
    return new BidResponseToResponseConverter(seatbidSeatBidConverter);
  });

  private ConverterProxy bidResponseToOpenRtbConverter = new ConverterProxy(() -> {
    Converter<BidResponse, Response> bidResponseResponseConverter =
      bidResponseToResponseConverter.apply(new Conversion(BidResponse.class, Response.class));
    return new BidResponseToOpenRtbConverter(bidResponseResponseConverter);
  });

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
