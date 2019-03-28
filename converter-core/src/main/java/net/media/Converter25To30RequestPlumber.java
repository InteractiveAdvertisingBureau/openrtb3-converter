package net.media;

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
import net.media.openrtb3.Companion;
import net.media.openrtb3.Content;
import net.media.openrtb3.Context;
import net.media.openrtb3.Data;
import net.media.openrtb3.Device;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Item;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Restrictions;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.User;
import net.media.utils.ConverterProxy;

@SuppressWarnings("unchecked")
public class Converter25To30RequestPlumber {

  private ConverterProxy bannerToDisplayPlacementConverter = new ConverterProxy(BannerToDisplayPlacementConverter::new);

  private ConverterProxy bannerToCompanionConverter = new ConverterProxy(() -> {
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter = bannerToDisplayPlacementConverter.apply(
      new Conversion(Banner.class, DisplayPlacement.class)
    );
    return new BannerToCompanionConverter(bannerDisplayPlacementConverter);
  });

  private ConverterProxy assetToAssetFormatConverter = new ConverterProxy(AssetToAssetFormatConverter::new);

  private ConverterProxy nativeRequestBodyToNativeFormatConverter = new ConverterProxy(() -> {
    Converter<Asset, AssetFormat> assetAssetFormatConverter = assetToAssetFormatConverter.apply(
      new Conversion(Asset.class, AssetFormat.class)
    );
    return new NativeRequestBodyToNativeFormatConverter(assetAssetFormatConverter);
  });

  private ConverterProxy nativeToDisplayPlacementConverter = new ConverterProxy(() -> {
    Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter = nativeRequestBodyToNativeFormatConverter.apply(
      new Conversion(NativeRequestBody.class, NativeFormat.class)
    );
    return new NativeToDisplayPlacementConverter(nativeRequestBodyNativeFormatConverter);
  });

  private ConverterProxy videoToVideoPlacementConverter = new ConverterProxy(() -> {
    Converter<Banner, Companion> bannerCompanionConverter = bannerToCompanionConverter.apply(
      new Conversion(Banner.class, Companion.class)
    );
    return new VideoToVideoPlacementConverter(bannerCompanionConverter);
  });

  private ConverterProxy audioToAudioPlacementConverter = new ConverterProxy(() -> {
    Converter<Banner, Companion> bannerCompanionConverter = bannerToCompanionConverter.apply(
      new Conversion(Banner.class, Companion.class)
    );
    return new AudioToAudioPlacementConverter(bannerCompanionConverter);
  });

  private ConverterProxy metricToMetricConverter = new ConverterProxy(MetricToMetricConverter::new);

  private ConverterProxy dealToDealConverter = new ConverterProxy(DealToDealConverter::new);

  private ConverterProxy impToItemConverter = new ConverterProxy(() -> {
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter = bannerToDisplayPlacementConverter.apply(
      new Conversion(Banner.class, DisplayPlacement.class)
    );
    Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter = nativeToDisplayPlacementConverter.apply(
      new Conversion(Native.class, DisplayPlacement.class)
    );
    Converter<Video, net.media.openrtb3.VideoPlacement> videoVideoPlacementConverter = videoToVideoPlacementConverter.apply(
      new Conversion(Video.class, net.media.openrtb3.VideoPlacement.class)
    );
    Converter<Audio, net.media.openrtb3.AudioPlacement> audioAudioPlacementConverter = audioToAudioPlacementConverter.apply(
      new Conversion(Audio.class, net.media.openrtb3.AudioPlacement.class)
    );
    Converter<Deal, net.media.openrtb3.Deal> dealDealConverter = dealToDealConverter.apply(
      new Conversion(Deal.class, net.media.openrtb3.Deal.class)
    );
    Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter = metricToMetricConverter.apply(
      new Conversion(Metric.class, net.media.openrtb3.Metric.class)
    );
    return new ImpToItemConverter(bannerDisplayPlacementConverter,
      nativeDisplayPlacementConverter,
      videoVideoPlacementConverter,
      audioAudioPlacementConverter,
      dealDealConverter,
      metricMetricConverter);
  });

  private ConverterProxy segmentToSegmentConverter = new ConverterProxy(SegmentToSegmentConverter::new);

  private ConverterProxy dataToDataConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.request.Segment, Segment> segmentSegmentConverter = segmentToSegmentConverter.apply(
      new Conversion(net.media.openrtb25.request.Segment.class, Segment.class)
    );
    return new DataToDataConverter(segmentSegmentConverter);
  });

  private ConverterProxy producerToProducerConverter = new ConverterProxy(ProducerToProducerConverter::new);

  private ConverterProxy contentToContentConverter = new ConverterProxy(() -> {
    Converter<Producer, net.media.openrtb3.Producer> producerProducerConverter = producerToProducerConverter.apply(
      new Conversion(Producer.class, net.media.openrtb3.Producer.class)
    );
    Converter<net.media.openrtb25.request.Data, Data> dataDataConverter = dataToDataConverter.apply(
      new Conversion(net.media.openrtb25.request.Data.class, Data.class)
    );
    return new ContentToContentConverter(producerProducerConverter, dataDataConverter);
  });

  private ConverterProxy publisherToPublisherConverter = new ConverterProxy(PublisherToPublisherConverter::new);

  private ConverterProxy siteToSiteConverter = new ConverterProxy(() -> {
    Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = publisherToPublisherConverter.apply(
      new Conversion(Publisher.class, net.media.openrtb3.Publisher.class)
    );
    Converter<net.media.openrtb25.request.Content, Content> contentContentConverter = contentToContentConverter.apply(
      new Conversion(net.media.openrtb25.request.Content.class, Content.class)
    );
    return new SiteToSiteConverter(publisherPublisherConverter, contentContentConverter);
  });

  private ConverterProxy appToAppConverter = new ConverterProxy(() -> {
    Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = publisherToPublisherConverter.apply(
      new Conversion(Publisher.class, net.media.openrtb3.Publisher.class)
    );
    Converter<net.media.openrtb25.request.Content, Content> contentContentConverter = contentToContentConverter.apply(
      new Conversion(net.media.openrtb25.request.Content.class, Content.class)
    );
    return new AppToAppConverter(publisherPublisherConverter, contentContentConverter);
  });

  private ConverterProxy geoToGeoConverter = new ConverterProxy(GeoToGeoConverter::new);

  private ConverterProxy userToUserConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.request.Geo, Geo> geoGeoConverter = geoToGeoConverter.apply(
      new Conversion(net.media.openrtb25.request.Geo.class, Geo.class)
    );
    Converter<net.media.openrtb25.request.Data, Data> dataDataConverter = dataToDataConverter.apply(
      new Conversion(net.media.openrtb25.request.Data.class, Data.class)
    );
    return new UserToUserConverter(geoGeoConverter, dataDataConverter);
  });

  private ConverterProxy deviceToDeviceConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.request.Geo, Geo> geoGeoConverter = geoToGeoConverter.apply(
      new Conversion(net.media.openrtb25.request.Geo.class, Geo.class)
    );
    return new DeviceToDeviceConverter(geoGeoConverter);
  });

  private ConverterProxy regsToRegsConverter = new ConverterProxy(RegsToRegsConverter::new);

  private ConverterProxy bidRequestToRestrictionsConverter = new ConverterProxy(BidRequestToRestrictionsConverter::new);

  private ConverterProxy bidRequestToContextConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.request.Regs, Regs> regsRegsConverter = regsToRegsConverter.apply(
      new Conversion(net.media.openrtb25.request.Regs.class, Regs.class)
    );
    Converter<net.media.openrtb25.request.Site, Site> siteSiteConverter = siteToSiteConverter.apply(
      new Conversion(net.media.openrtb25.request.Site.class, Site.class)
    );
    Converter<net.media.openrtb25.request.User, User> userUserConverter = userToUserConverter.apply(
      new Conversion(net.media.openrtb25.request.User.class, User.class)
    );
    Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter = bidRequestToRestrictionsConverter.apply(
      new Conversion(net.media.openrtb25.request.BidRequest.class, Restrictions.class)
    );
    Converter<net.media.openrtb25.request.App, App> appAppConverter = appToAppConverter.apply(
      new Conversion(net.media.openrtb25.request.App.class, App.class)
    );
    Converter<net.media.openrtb25.request.Device, Device> deviceDeviceConverter = deviceToDeviceConverter.apply(
      new Conversion(net.media.openrtb25.request.Device.class, Device.class)
    );
    return new BidRequestToContextConverter(regsRegsConverter, siteSiteConverter, userUserConverter,
      bidRequestRestrictionsConverter, appAppConverter, deviceDeviceConverter);
  });

  private ConverterProxy sourceToSourceConverter = new ConverterProxy(SourceToSourceConverter::new);

  private ConverterProxy bidRequestToRequestConverter = new ConverterProxy(() -> {
    Converter<Imp, Item> impItemConverter = impToItemConverter.apply(
      new Conversion(Imp.class, Item.class)
    );
    Converter<BidRequest, Context> bidRequestContextConverter = bidRequestToContextConverter.apply(
      new Conversion(BidRequest.class, Context.class)
    );
    Converter<net.media.openrtb25.request.Source, net.media.openrtb3.Source> sourceSourceConverter = sourceToSourceConverter.apply(
      new Conversion(Source.class, net.media.openrtb3.Source.class)
    );
    return new BidRequestToRequestConverter(impItemConverter, bidRequestContextConverter, sourceSourceConverter);
  });

  private ConverterProxy bidRequestToOpenRtbConverter = new ConverterProxy(() -> {
    Converter<net.media.openrtb25.request.BidRequest, net.media.openrtb3.Request> bidRequestRequestConverter = bidRequestToRequestConverter.apply(
      new Conversion(BidRequest.class, net.media.openrtb3.Request.class)
    );
    return new BidRequestToOpenRtbConverter(bidRequestRequestConverter);
  });

  public ConverterProxy getBidRequestToOpenRtbConverter() {
    return bidRequestToOpenRtbConverter;
  }
}
