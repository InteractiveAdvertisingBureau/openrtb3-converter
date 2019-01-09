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
import net.media.converters.response25toresponse30.SeatBidList24ToSeatBidList30Converter;
import net.media.converters.response30toresponse25.AdToBidConverter;
import net.media.converters.response30toresponse25.Asset30ToAsset24Converter;
import net.media.converters.response30toresponse25.AudioToBidConverter;
import net.media.converters.response30toresponse25.AuditToBidConverter;
import net.media.converters.response30toresponse25.Bid30ToBid24Converter;
import net.media.converters.response30toresponse25.DisplayToBidConverter;
import net.media.converters.response30toresponse25.LinkAssetToLinkConverter;
import net.media.converters.response30toresponse25.MediatoBidConverter;
import net.media.converters.response30toresponse25.Native30ToNative10Converter;
import net.media.converters.response30toresponse25.OpenRtbResponseToBidResponseConverter;
import net.media.converters.response30toresponse25.SeatBid30ToSeatBid24Converter;
import net.media.converters.response30toresponse25.VideoToBidConverter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb24.response.nativeresponse.AssetResponse;
import net.media.openrtb24.response.nativeresponse.Link;
import net.media.openrtb24.response.nativeresponse.NativeResponse;
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

import java.util.List;

/**
 * @author shiva.b
 */
@SuppressWarnings("unchecked")
public class ConverterPlumber {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterPlumber() {
    openRtbToBidResponseConverter.apply(new Conversion(OpenRTB.class, BidResponse.class));

    converterProvider = new Provider<>(null);
    Converter25To30RequestPlumber converter25To30RequestPlumber = new Converter25To30RequestPlumber();
    Converter30To25RequestPlumber converter30To25RequestPlumber = new Converter30To25RequestPlumber();
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      bidResponseToOpenRtb());
    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      converter25To30RequestPlumber.bidRequestToOpenRtb());
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      converter30To25RequestPlumber.openRtbToBidRequestConverter());
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
    Converter<Display, net.media.openrtb24.response.Bid> displayBidConverter = displayToBidConverter
      .apply(new Conversion(Display.class, net.media.openrtb24.response.Bid.class));
    Converter<Video, net.media.openrtb24.response.Bid> videoBidConverter = videoToBidConverter
      .apply(new Conversion(Video.class, net.media.openrtb24.response.Bid.class));
    Converter<Audio, net.media.openrtb24.response.Bid> audioBidConverter = audioToBidConverter
      .apply(new Conversion(Audio.class, net.media.openrtb24.response.Bid.class));
    Converter<Audit, net.media.openrtb24.response.Bid> auditBidConverter = auditToBidConverter
      .apply(new Conversion(Audit.class, net.media.openrtb24.response.Bid.class));
    return new AdToBidConverter(
      displayBidConverter,
      videoBidConverter,
      audioBidConverter,
      auditBidConverter
    );
  });

  private ConverterProxy mediaToBidConverter = new ConverterProxy(() -> {
    Converter<Ad, net.media.openrtb24.response.Bid> adBidConverter = adToBidConverter.apply(new
      Conversion(Ad.class, net.media.openrtb24.response.Bid.class));
    return new MediatoBidConverter(adBidConverter);
  });

  private ConverterProxy bid30Tobid24Converter = new ConverterProxy(() -> {
    Converter<Media, net.media.openrtb24.response.Bid> mediaBidConverter = mediaToBidConverter
      .apply(new Conversion(Media.class, net.media.openrtb24.response.Bid.class));
    return new Bid30ToBid24Converter(mediaBidConverter);
  });

  private ConverterProxy seatBid30ToSeatBid24Converter = new ConverterProxy(() -> {
    Converter<Bid, net.media.openrtb24.response.Bid> bidBidConverter = bid30Tobid24Converter
      .apply(new Conversion(Bid.class, net.media.openrtb24.response.Bid.class));
    return new SeatBid30ToSeatBid24Converter(bidBidConverter);
  });

  private ConverterProxy openRtbToBidResponseConverter = new ConverterProxy(() -> {
    Converter<Seatbid, SeatBid> seatbidSeatBidConverter = seatBid30ToSeatBid24Converter.apply(new
      Conversion(Seatbid.class, SeatBid.class));
    return new OpenRtbResponseToBidResponseConverter(seatbidSeatBidConverter);
  });


  private Converter<BidResponse, OpenRTB> bidResponseToOpenRtb() {
    Converter<BidResponse, Response> bidResponseToResponseConverter = bidResponseToResponse();
    Converter<BidResponse, OpenRTB> bidResponseToOpenRtbConverter = new BidResponseToOpenRtbConverter(bidResponseToResponseConverter);

    return bidResponseToOpenRtbConverter;
  }

  private Converter<BidResponse,Response> bidResponseToResponse() {
    Converter<net.media.openrtb24.response.Bid, Bid> bid24ToBid30Converter = bid24ToBid30();
    Converter<SeatBid, Seatbid> seatBid24ToSeatBid30Converter = new SeatBid24ToSeatBid30Converter(bid24ToBid30Converter);
    Converter<List<SeatBid>, List<Seatbid>> seatBidListToSeatBidListConverter = new SeatBidList24ToSeatBidList30Converter(seatBid24ToSeatBid30Converter);
    Converter<BidResponse, Response> bidResponseToResponseConverter = new BidResponseToResponseConverter(seatBidListToSeatBidListConverter);
    return bidResponseToResponseConverter;
  }

  private Converter<net.media.openrtb24.response.Bid, Bid> bid24ToBid30() {
    Converter<net.media.openrtb24.response.Bid, Media> bidMediaConverter = bidToMediaConverter();
    Converter<net.media.openrtb24.response.Bid, Bid> bid24ToBid30Converter = new Bid24ToBid30Converter(bidMediaConverter);
    return bid24ToBid30Converter;
  }

  private Converter<net.media.openrtb24.response.Bid,Media> bidToMediaConverter() {
    Converter<Link, LinkAsset> linkToLinkAssetConverter = new LinkToLinkAssetConverter();
    Converter<AssetResponse, net.media.openrtb3.Asset> assetResponseToAssetConverter = new Asset24ToAsset30Converter(linkToLinkAssetConverter);
    Converter<NativeResponse, Native> nativeResponseNativeConverter = new Native24ToNative30Converter(linkToLinkAssetConverter, assetResponseToAssetConverter);
    Converter<net.media.openrtb24.response.Bid, Display> bidDisplayConverter = new BidToDisplayConverter(nativeResponseNativeConverter);
    Converter<net.media.openrtb24.response.Bid, Audio> bidAudioConverter = new BidToAudioConverter();
    Converter<net.media.openrtb24.response.Bid, Video> bidVideoConverter = new BidToVideoConverter();
    Converter<net.media.openrtb24.response.Bid, Audit> bidAuditConverter = new BidToAuditConverter();
    Converter<net.media.openrtb24.response.Bid, Ad> bidAdConverter = new BidToAdConverter(bidDisplayConverter, bidAudioConverter, bidVideoConverter, bidAuditConverter);
    Converter<net.media.openrtb24.response.Bid, Media> bidMediaConverter = new Bid24ToMediaConverter(bidAdConverter);
    return bidMediaConverter;
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
