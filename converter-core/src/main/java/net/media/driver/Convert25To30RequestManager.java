package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.request25toRequest30.AppToAppConverter;
import net.media.converters.request25toRequest30.AssetToAssetFormatConverter;
import net.media.converters.request25toRequest30.AudioToAudioPlacementConverter;
import net.media.converters.request25toRequest30.BannerToCompanionConverter;
import net.media.converters.request25toRequest30.BannerToDisplayPlacementConverter;
import net.media.converters.request25toRequest30.BidRequestToContextConverter;
import net.media.converters.request25toRequest30.BidRequestToOpenRtbConverter;
import net.media.converters.request25toRequest30.BidRequestToRequestConverter;
import net.media.converters.request25toRequest30.BidRequestToRestrictionsConverter;
import net.media.converters.request25toRequest30.ContentToContentConverter;
import net.media.converters.request25toRequest30.DataToDataConverter;
import net.media.converters.request25toRequest30.DealToDealConverter;
import net.media.converters.request25toRequest30.DeviceToDeviceConverter;
import net.media.converters.request25toRequest30.GeoToGeoConverter;
import net.media.converters.request25toRequest30.ImpToItemConverter;
import net.media.converters.request25toRequest30.MetricToMetricConverter;
import net.media.converters.request25toRequest30.NativeRequestBodyToNativeFormatConverter;
import net.media.converters.request25toRequest30.NativeToDisplayPlacementConverter;
import net.media.converters.request25toRequest30.ProducerToProducerConverter;
import net.media.converters.request25toRequest30.PublisherToPublisherConverter;
import net.media.converters.request25toRequest30.RegsToRegsConverter;
import net.media.converters.request25toRequest30.SegmentToSegmentConverter;
import net.media.converters.request25toRequest30.SiteToSiteConverter;
import net.media.converters.request25toRequest30.SourceToSourceConverter;
import net.media.converters.request25toRequest30.UserToUserConverter;
import net.media.converters.request25toRequest30.VideoToVideoPlacementConverter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Deal;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Metric;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb25.request.Producer;
import net.media.openrtb25.request.Publisher;
import net.media.openrtb25.request.Source;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.App;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.openrtb3.Content;
import net.media.openrtb3.Context;
import net.media.openrtb3.Data;
import net.media.openrtb3.Device;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Item;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Request;
import net.media.openrtb3.Restrictions;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.User;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

@SuppressWarnings("unchecked")
public class Convert25To30RequestManager {

  public Convert25To30RequestManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Banner.class, DisplayPlacement.class), new BannerToDisplayPlacementConverter());
    converterProvider.register(new Conversion<>(Banner.class, Companion.class), new BannerToCompanionConverter());
    converterProvider.register(new Conversion<>(Asset.class, AssetFormat.class), new AssetToAssetFormatConverter());
    converterProvider.register(new Conversion<>(NativeRequestBody.class, NativeFormat.class), new
      NativeRequestBodyToNativeFormatConverter());
    converterProvider.register(new Conversion<>(Native.class, DisplayPlacement.class), new NativeToDisplayPlacementConverter());
    converterProvider.register(new Conversion<>(Video.class, VideoPlacement.class), new VideoToVideoPlacementConverter());
    converterProvider.register(new Conversion<>(Audio.class, AudioPlacement.class), new AudioToAudioPlacementConverter());
    converterProvider.register(new Conversion<>(Metric.class, net.media.openrtb3.Metric.class), new
      MetricToMetricConverter());
    converterProvider.register(new Conversion<>(Deal.class, net.media.openrtb3.Deal.class), new DealToDealConverter());
    converterProvider.register(new Conversion<>(Imp.class, Item.class), new ImpToItemConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Segment.class, Segment
      .class), new SegmentToSegmentConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Data.class, Data.class)
      , new DataToDataConverter());
    converterProvider.register(new Conversion<>(Producer.class, net.media.openrtb3.Producer.class),
      new ProducerToProducerConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Content.class, Content
      .class), new ContentToContentConverter());
    converterProvider.register(new Conversion<>(Publisher.class, net.media.openrtb3.Publisher
      .class), new PublisherToPublisherConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Site.class, Site.class)
      , new SiteToSiteConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.App.class, App.class),
      new AppToAppConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Geo.class, Geo.class),
      new GeoToGeoConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.User.class, User.class)
      , new UserToUserConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Device.class, Device
      .class), new DeviceToDeviceConverter());
    converterProvider.register(new Conversion<>(net.media.openrtb25.request.Regs.class, Regs.class)
      , new RegsToRegsConverter());
    converterProvider.register(new Conversion<>(BidRequest.class, Restrictions.class), new
      BidRequestToRestrictionsConverter());
    converterProvider.register(new Conversion<>(BidRequest.class, Context.class), new
      BidRequestToContextConverter());
    converterProvider.register(new Conversion<>(Source.class, net.media.openrtb3.Source.class), new
      SourceToSourceConverter());
    converterProvider.register(new Conversion<>(BidRequest.class, Request.class), new
      BidRequestToRequestConverter());
    converterProvider.register(new Conversion<>(BidRequest.class, OpenRTB.class), new BidRequestToOpenRtbConverter());
  }

}
