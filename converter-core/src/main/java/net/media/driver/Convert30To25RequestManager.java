package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.request25toRequest30.MetricToMetricConverter;
import net.media.converters.request30toRequest25.AppToAppConverter;
import net.media.converters.request30toRequest25.AssetFormatToAssetConverter;
import net.media.converters.request30toRequest25.AudioPlacementToAudioConverter;
import net.media.converters.request30toRequest25.CompanionToBannerConverter;
import net.media.converters.request30toRequest25.ContentToContentConverter;
import net.media.converters.request30toRequest25.DataToDataConverter;
import net.media.converters.request30toRequest25.DealToDealConverter;
import net.media.converters.request30toRequest25.DeviceToDeviceConverter;
import net.media.converters.request30toRequest25.DisplayPlacementToBannerConverter;
import net.media.converters.request30toRequest25.DisplayPlacementToNativeConverter;
import net.media.converters.request30toRequest25.GeoToGeoConverter;
import net.media.converters.request30toRequest25.ItemToImpConverter;
import net.media.converters.request30toRequest25.NativeFormatToNativeRequestBodyConverter;
import net.media.converters.request30toRequest25.OpenRtbToBidRequestConverter;
import net.media.converters.request30toRequest25.ProducerToProducerConverter;
import net.media.converters.request30toRequest25.PublisherToPublisherConverter;
import net.media.converters.request30toRequest25.RegsToRegsConverter;
import net.media.converters.request30toRequest25.RequestToBidRequestConverter;
import net.media.converters.request30toRequest25.RequestToUserConverter;
import net.media.converters.request30toRequest25.SegmentToSegmentConverter;
import net.media.converters.request30toRequest25.SiteToSiteConverter;
import net.media.converters.request30toRequest25.SourceToSourceConverter;
import net.media.converters.request30toRequest25.UserToUserConverter;
import net.media.converters.request30toRequest25.VideoPlacementToVideoConverter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequest;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.App;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Device;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Item;
import net.media.openrtb3.Metric;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Producer;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Request;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.Source;
import net.media.openrtb3.User;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.ConverterProxy;
import net.media.utils.Provider;

@SuppressWarnings("unchecked")
public class Convert30To25RequestManager {

  public Convert30To25RequestManager(Provider<Conversion, Converter> converterProvider) {
    converterProvider.register(new Conversion(AssetFormat.class, Asset.class), new AssetFormatToAssetConverter());
    converterProvider.register(new Conversion(NativeFormat.class, NativeRequestBody.class), new
      NativeFormatToNativeRequestBodyConverter());
    converterProvider.register(new Conversion(DisplayPlacement.class, Native.class), new DisplayPlacementToNativeConverter());
    converterProvider.register(new Conversion(DisplayPlacement.class, Banner.class), new
      DisplayPlacementToBannerConverter());
    converterProvider.register(new Conversion(Companion.class, Banner.class), new CompanionToBannerConverter());
    converterProvider.register(new Conversion(VideoPlacement.class, Video.class), new VideoPlacementToVideoConverter());
    converterProvider.register(new Conversion(AudioPlacement.class, Audio.class), new AudioPlacementToAudioConverter());
    converterProvider.register(new Conversion(Metric.class, net.media.openrtb25.request.Metric
      .class), new MetricToMetricConverter());
    converterProvider.register(new Conversion(Deal.class, net.media.openrtb25.request.Deal.class)
      , new DealToDealConverter());
    converterProvider.register(new Conversion(Item.class, Imp.class), new ItemToImpConverter());
    converterProvider.register(new Conversion(Geo.class, net.media.openrtb25.request.Geo.class),
      new GeoToGeoConverter());
    converterProvider.register(new Conversion(Device.class, net.media.openrtb25.request.Device
      .class), new DeviceToDeviceConverter());
    converterProvider.register(new Conversion(Producer.class, net.media.openrtb25.request
      .Producer.class), new ProducerToProducerConverter());
    converterProvider.register(new Conversion(Segment.class, net.media.openrtb25.request.Segment
      .class), new SegmentToSegmentConverter());
    converterProvider.register(new Conversion(Data.class, net.media.openrtb25.request.Data.class)
      , new DataToDataConverter());
    converterProvider.register(new Conversion(Content.class, net.media.openrtb25.request.Content
      .class), new ContentToContentConverter());
    converterProvider.register(new Conversion(Publisher.class, net.media.openrtb25.request
      .Publisher.class), new PublisherToPublisherConverter());
    converterProvider.register(new Conversion(Site.class, net.media.openrtb25.request.Site.class)
      , new SiteToSiteConverter());
    converterProvider.register(new Conversion(App.class, net.media.openrtb25.request.App.class),
      new AppToAppConverter());
    converterProvider.register(new Conversion(User.class, User.class), new UserToUserConverter());
    converterProvider.register(new Conversion(Request.class, net.media.openrtb25.request.User
      .class), new RequestToUserConverter());
    converterProvider.register(new Conversion(Regs.class, net.media.openrtb25.request.Regs.class)
      , new RegsToRegsConverter());
    converterProvider.register(new Conversion(Source.class, net.media.openrtb25.request.Source
      .class), new SourceToSourceConverter());
    converterProvider.register(new Conversion(Request.class, BidRequest.class), new RequestToBidRequestConverter());
    converterProvider.register(new Conversion(OpenRTB.class, Request.class), new OpenRtbToBidRequestConverter());
  }
}
