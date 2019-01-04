package net.media;

import net.media.converters.request24toRequest30.AssetToAssetFormatConverter;
import net.media.converters.request24toRequest30.AudioToAudioPlacementConverter;
import net.media.converters.request24toRequest30.BannerToCompanionConverter;
import net.media.converters.request24toRequest30.BannerToDisplayPlacementConverter;
import net.media.converters.request24toRequest30.BidRequestToOpenRtbConverter;
import net.media.converters.request24toRequest30.BidRequestToRequestConverter;
import net.media.converters.request24toRequest30.DealToDealConverter;
import net.media.converters.request24toRequest30.ImpToItemConverter;
import net.media.converters.request24toRequest30.MetricToMetricConverter;
import net.media.converters.request24toRequest30.NativeRequestBodyToNativeFormatConverter;
import net.media.converters.request24toRequest30.NativeToDisplayPlacementConverter;
import net.media.converters.request24toRequest30.VideoToVideoPlacementConverter;
import net.media.converters.request30toRequest24.AssetFormatToAssetConverter;
import net.media.converters.request30toRequest24.AudioPlacementToAudioConverter;
import net.media.converters.request30toRequest24.CompanionToBannerConverter;
import net.media.converters.request30toRequest24.DisplayPlacementToBannerConverter;
import net.media.converters.request30toRequest24.DisplayPlacementToNativeConverter;
import net.media.converters.request30toRequest24.ItemToImpConverter;
import net.media.converters.request30toRequest24.NativeFormatToNativeRequestBodyConverter;
import net.media.converters.request30toRequest24.VideoPlacementToVideoConverter;
import net.media.converters.response24toresponse30.Bid24ToBid30Converter;
import net.media.converters.response24toresponse30.BidResponseToOpenRtbConverter;
import net.media.converters.response24toresponse30.BidResponseToResponseConverter;
import net.media.converters.Converter;
import net.media.converters.response24toresponse30.SeatBid24ToSeatBid30Converter;
import net.media.converters.response24toresponse30.SeatBidList24ToSeatBidList30Converter;
import net.media.openrtb24.request.Asset;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Deal;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Metric;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb24.request.Video;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

import java.util.List;

/**
 * @author shiva.b
 */
public class ConverterPlumber {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterPlumber() {
    converterProvider = new Provider<>(null);
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      bidResponseToOpenRtb());
    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      bidRequestToOpenRtb());
    converterProvider.register(new Conversion(OpenRTB.class, BidResponse.class),
      openRtbToBidResponseConverter());
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      OpenRtbToBidRequestConverter());
  }

  private Converter<OpenRTB, BidRequest> OpenRtbToBidRequestConverter() {
    Converter<Item, Imp> itemImpConverter = itemToImp();
    return null;
  }

  private Converter<Item, Imp> itemToImp() {
    Converter<DisplayPlacement, Banner> displayPlacementBannerConverter = new
      DisplayPlacementToBannerConverter();
    Converter<DisplayPlacement, Native> displayPlacementNativeConverter =
      displayPlacementToNative();
    Converter<Companion, Banner> companionBannerConverter = new CompanionToBannerConverter
      (displayPlacementBannerConverter);
    Converter<VideoPlacement, Video> videoPlacementVideoConverter = new
      VideoPlacementToVideoConverter(companionBannerConverter);
    Converter<AudioPlacement, Audio> audioPlacementAudioConverter = new
      AudioPlacementToAudioConverter(companionBannerConverter);
    Converter<net.media.openrtb3.Metric, Metric> metricMetricConverter = new net.media.converters
      .request30toRequest24.MetricToMetricConverter();
    Converter<net.media.openrtb3.Deal, Deal> dealDealConverter = new net.media.converters
      .request30toRequest24.DealToDealConverter();
    return new ItemToImpConverter(displayPlacementBannerConverter,
                                  displayPlacementNativeConverter,
                                  videoPlacementVideoConverter,
                                  audioPlacementAudioConverter,
                                  metricMetricConverter,
                                  dealDealConverter);
  }

  private Converter<DisplayPlacement, Native> displayPlacementToNative() {
    Converter<AssetFormat, Asset> assetFormatAssetConverter = new AssetFormatToAssetConverter();
    Converter<NativeFormat, NativeRequestBody> nativeFormatNativeRequestBodyConverter = new
      NativeFormatToNativeRequestBodyConverter(assetFormatAssetConverter);
    return new DisplayPlacementToNativeConverter(nativeFormatNativeRequestBodyConverter);
  }

  private Converter<OpenRTB, BidResponse> openRtbToBidResponseConverter() {
    return null;
  }

  private Converter<BidRequest, OpenRTB> bidRequestToOpenRtb() {
    Converter<BidRequest, Request> bidRequestToRequestConverter = bidRequestToRequest();
    return new BidRequestToOpenRtbConverter(bidRequestToRequestConverter);
  }

  private Converter<BidRequest, Request> bidRequestToRequest() {
    Converter<Imp, Item> impItemConverter = impToItem();
    return new BidRequestToRequestConverter(impItemConverter);
  }

  private Converter<Imp, Item> impToItem() {
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter = new
      BannerToDisplayPlacementConverter();
    Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter =
      nativeToDisplayPlacement();
    Converter<Banner, Companion> bannerCompanionConverter = bannerToCompanion
      (bannerDisplayPlacementConverter);
    Converter<Video, VideoPlacement> videoVideoPlacementConverter = new
      VideoToVideoPlacementConverter(bannerCompanionConverter);
    Converter<Audio, AudioPlacement> audioAudioPlacementConverter = new
      AudioToAudioPlacementConverter(bannerCompanionConverter);
    Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter = new
      MetricToMetricConverter();
    Converter<Deal, net.media.openrtb3.Deal> dealDealConverter = new DealToDealConverter();
    return new ImpToItemConverter(bannerDisplayPlacementConverter,
                                  nativeDisplayPlacementConverter,
                                  videoVideoPlacementConverter,
                                  audioAudioPlacementConverter,
                                  dealDealConverter,
                                  metricMetricConverter);
  }

  private Converter<Native, DisplayPlacement> nativeToDisplayPlacement() {
    Converter<Asset, AssetFormat> assetAssetFormatConverter = new AssetToAssetFormatConverter();
    Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter = new
      NativeRequestBodyToNativeFormatConverter(assetAssetFormatConverter);
    return new NativeToDisplayPlacementConverter(nativeRequestBodyNativeFormatConverter);
  }

  private Converter<Banner, Companion> bannerToCompanion(Converter<Banner,
    DisplayPlacement> bannerDisplayPlacementConverter) {
    return new BannerToCompanionConverter(bannerDisplayPlacementConverter);
  }

  private Converter<BidResponse, OpenRTB> bidResponseToOpenRtb() {
    Converter<BidResponse, Response> bidResponseToResponseConverter = bidResponseToResponse();
    Converter<BidResponse, OpenRTB> bidResponseToOpenRtbConverter = new
      BidResponseToOpenRtbConverter(bidResponseToResponseConverter);

    return bidResponseToOpenRtbConverter;
  }

  private Converter<BidResponse,Response> bidResponseToResponse() {
    Converter<net.media.openrtb24.response.Bid, Bid> bid24ToBid30Converter = new
      Bid24ToBid30Converter();
    Converter<SeatBid, Seatbid> seatBid24ToSeatBid30Converter = new SeatBid24ToSeatBid30Converter
      (bid24ToBid30Converter);
    Converter<List<SeatBid>, List<Seatbid>> seatBidListToSeatBidListConverter =
      new SeatBidList24ToSeatBidList30Converter(seatBid24ToSeatBid30Converter);
    Converter<BidResponse, Response> bidResponseToResponseConverter = new
      BidResponseToResponseConverter(seatBidListToSeatBidListConverter);
    return bidResponseToResponseConverter;
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
