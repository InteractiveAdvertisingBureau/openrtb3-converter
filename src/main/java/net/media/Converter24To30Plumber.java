package net.media;

import net.media.converters.Converter;
import net.media.converters.request24toRequest30.*;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.*;

public class Converter24To30Plumber {

  public Converter<BidRequest, OpenRTB> bidRequestToOpenRtb() {
    Converter<BidRequest, Request> bidRequestToRequestConverter = bidRequestToRequest();
    return new BidRequestToOpenRtbConverter(bidRequestToRequestConverter);
  }

  private Converter<BidRequest, Request> bidRequestToRequest() {
    Converter<Imp, Item> impItemConverter = impToItemConverter();
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

  private Converter<Imp, Item> impToItemConverter() {
    Converter<net.media.openrtb24.request.Banner, DisplayPlacement> bannerDisplayPlacementConverter = new
      BannerToDisplayPlacementConverter();
    Converter<net.media.openrtb24.request.Native, DisplayPlacement> nativeDisplayPlacementConverter = new
      NativeToDisplayPlacementConverter(new NativeRequestBodyToNativeFormatConverter());
    Converter<net.media.openrtb24.request.Banner, Companion> bannerCompanionConverter = bannerCompanionConverter
      (bannerDisplayPlacementConverter);
    Converter<net.media.openrtb24.request.Video, VideoPlacement> videoVideoPlacementConverter = new
      VideoToVideoPlacementConverter(bannerCompanionConverter);
    Converter<net.media.openrtb24.request.Audio, AudioPlacement> audioAudioPlacementConverter = new
      AudioToAudioPlacementConverter(bannerCompanionConverter);
    return new ImpToItemConverter(bannerDisplayPlacementConverter,
      nativeDisplayPlacementConverter, videoVideoPlacementConverter, audioAudioPlacementConverter);
  }

  private Converter<net.media.openrtb24.request.Banner, Companion> bannerCompanionConverter(Converter<net.media.openrtb24.request.Banner,
    DisplayPlacement> bannerDisplayPlacementConverter) {
    return new BannerToCompanionConverter(bannerDisplayPlacementConverter);
  }
}
