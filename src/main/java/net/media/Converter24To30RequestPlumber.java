package net.media;

import net.media.converters.Converter;
import net.media.converters.request24toRequest30.*;
import net.media.openrtb24.request.*;
import net.media.openrtb24.request.Asset;
import net.media.openrtb3.*;
import net.media.openrtb3.App;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Metric;
import net.media.openrtb3.Native;
import net.media.openrtb3.Producer;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.Source;
import net.media.openrtb3.User;
import net.media.openrtb3.Video;

public class Converter24To30RequestPlumber {

  public Converter<BidRequest, OpenRTB> bidRequestToOpenRtb() {
    Converter<BidRequest, Request> bidRequestToRequestConverter = bidRequestToRequest();
    return new BidRequestToOpenRtbConverter(bidRequestToRequestConverter);
  }

  private Converter<BidRequest, Request> bidRequestToRequest() {
    Converter<Imp, Item> impItemConverter = impToItem();
    Converter<BidRequest, Context> bidRequestContextConverter = bidRequestContextConverter();
    Converter<net.media.openrtb24.request.Source, net.media.openrtb3.Source> source24Source3Converter = new SourceToSourceConverter();
    return new BidRequestToRequestConverter(impItemConverter, bidRequestContextConverter, source24Source3Converter);
  }

  private Converter<BidRequest, Context> bidRequestContextConverter() {
    Converter<net.media.openrtb24.request.Regs, Regs> regsRegsConverter = new RegsToRegsConverter();
    Converter<net.media.openrtb24.request.Site, Site> siteSiteConverter = siteSiteConverter();
    Converter<net.media.openrtb24.request.User, User> userUserConverter = userUserConverter();
    Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter = new BidRequestToRestrictionsConverter();
    Converter<net.media.openrtb24.request.App, App> appAppConverter = appAppConverter();
    Converter<net.media.openrtb24.request.Device, Device> deviceDeviceConverter = deviceDeviceConverter();
    return new BidRequestToContextConverter(regsRegsConverter, siteSiteConverter, userUserConverter,
      bidRequestRestrictionsConverter, appAppConverter, deviceDeviceConverter);
  }

  private Converter<net.media.openrtb24.request.Device, Device> deviceDeviceConverter() {
    Converter<net.media.openrtb24.request.Geo, Geo> geoGeoConverter = new GeoToGeoConverter();
    return new DeviceToDeviceConverter(geoGeoConverter);
  }

  private Converter<net.media.openrtb24.request.User, User> userUserConverter() {
    Converter<net.media.openrtb24.request.Geo, Geo> geoGeoConverter = new GeoToGeoConverter();
    Converter<net.media.openrtb24.request.Data, Data> dataDataConverter = dataDataConverter();
    return new UserToUserConverter(geoGeoConverter, dataDataConverter);
  }

  private Converter<net.media.openrtb24.request.App, App> appAppConverter() {
    Converter<net.media.openrtb24.request.Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<net.media.openrtb24.request.Content, net.media.openrtb3.Content> contentContentConverter = contentContentConverter();
    return new AppToAppConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<net.media.openrtb24.request.Site, Site> siteSiteConverter() {
    Converter<net.media.openrtb24.request.Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<net.media.openrtb24.request.Content, net.media.openrtb3.Content> contentContentConverter = contentContentConverter();
    return new SiteToSiteConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<net.media.openrtb24.request.Content, Content> contentContentConverter() {
    Converter<net.media.openrtb24.request.Producer, net.media.openrtb3.Producer> producerProducerConverter = new ProducerToProducerConverter();
    Converter<net.media.openrtb24.request.Data, Data> dataDataConverter = dataDataConverter();
    return new ContentToContentConverter(producerProducerConverter, dataDataConverter);
  }

  private Converter<net.media.openrtb24.request.Data, Data> dataDataConverter() {
    Converter<net.media.openrtb24.request.Segment, Segment> segmentSegmentConverter = new SegmentToSegmentConverter();
    return new DataToDataConverter(segmentSegmentConverter);
  }

  private Converter<Imp, Item> impToItem() {
    Converter<net.media.openrtb24.request.Banner, DisplayPlacement> bannerDisplayPlacementConverter = new
      BannerToDisplayPlacementConverter();
    Converter<net.media.openrtb24.request.Native, DisplayPlacement> nativeDisplayPlacementConverter =
      nativeToDisplayPlacement();
    Converter<net.media.openrtb24.request.Banner, Companion> bannerCompanionConverter = bannerToCompanion
      (bannerDisplayPlacementConverter);
    Converter<net.media.openrtb24.request.Video, VideoPlacement> videoVideoPlacementConverter = new
      VideoToVideoPlacementConverter(bannerCompanionConverter);
    Converter<net.media.openrtb24.request.Audio, AudioPlacement> audioAudioPlacementConverter = new
      AudioToAudioPlacementConverter(bannerCompanionConverter);
    Converter<net.media.openrtb24.request.Metric, net.media.openrtb3.Metric> metricMetricConverter = new
      MetricToMetricConverter();
    Converter<net.media.openrtb24.request.Deal, net.media.openrtb3.Deal> dealDealConverter = new DealToDealConverter();
    return new ImpToItemConverter(bannerDisplayPlacementConverter,
                                  nativeDisplayPlacementConverter,
                                  videoVideoPlacementConverter,
                                  audioAudioPlacementConverter,
                                  dealDealConverter,
                                  metricMetricConverter);
  }

  private Converter<net.media.openrtb24.request.Native, DisplayPlacement> nativeToDisplayPlacement() {
    Converter<Asset, AssetFormat> assetAssetFormatConverter = new AssetToAssetFormatConverter();
    Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter = new
      NativeRequestBodyToNativeFormatConverter(assetAssetFormatConverter);
    return new NativeToDisplayPlacementConverter(nativeRequestBodyNativeFormatConverter);
  }

  private Converter<net.media.openrtb24.request.Banner, Companion> bannerToCompanion(Converter<net.media.openrtb24.request.Banner,
    DisplayPlacement> bannerDisplayPlacementConverter) {
    return new BannerToCompanionConverter(bannerDisplayPlacementConverter);
  }
}
