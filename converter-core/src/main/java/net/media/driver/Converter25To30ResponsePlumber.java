package net.media.driver;

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
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
import net.media.utils.ConverterProxy;

/**
 * Created by shiva.b on 28/03/19.
 */
@SuppressWarnings("unchecked")
public class Converter25To30ResponsePlumber {

  public ConverterProxy getBidResponseToOpenRtbConverterProxy() {
    return bidResponseToOpenRtbConverter;
  }


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

}
