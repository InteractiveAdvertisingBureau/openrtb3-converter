package net.media.driver;

import net.media.converters.Converter;
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
import net.media.openrtb25.request.Native;
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

@SuppressWarnings("unchecked")
public class Convert30To25RequestManager {

  private ConverterProxy assetFormatToAssetConverter = new ConverterProxy(AssetFormatToAssetConverter::new);

  private ConverterProxy nativeFormatNativeRequestBodyConverter = new ConverterProxy(() -> {
    Converter<AssetFormat, Asset> assetFormatAssetConverter = assetFormatToAssetConverter.apply(new
      Conversion(AssetFormat.class, Asset.class));
    return new NativeFormatToNativeRequestBodyConverter(assetFormatAssetConverter);
  });

  private ConverterProxy displayPlacementToNativeConverter = new ConverterProxy(() -> {
    Converter<NativeFormat, NativeRequestBody> nativeFormatNativeConverter = nativeFormatNativeRequestBodyConverter.apply(
      new Conversion(NativeFormat.class, NativeRequestBody.class)
    );
    return new DisplayPlacementToNativeConverter(nativeFormatNativeConverter);
  });

  private ConverterProxy displayPlacementToBannerConverter = new ConverterProxy(DisplayPlacementToBannerConverter::new);


  private ConverterProxy companionToBannerConverter = new ConverterProxy(() -> {
    Converter<DisplayPlacement, Banner> displayPlacementBannerConverter = displayPlacementToBannerConverter.apply(
      new Conversion(DisplayPlacement.class, Banner.class)
    );
    return new CompanionToBannerConverter(displayPlacementBannerConverter);
  });

  private ConverterProxy videoPlacementToVideoConverter = new ConverterProxy(() -> {
    Converter<Companion, Banner> companionBannerConverter = companionToBannerConverter.apply(
      new Conversion(Companion.class, Banner.class)
    );
    return new VideoPlacementToVideoConverter(companionBannerConverter);
  });

  private ConverterProxy audioPlacementToAudioConverter = new ConverterProxy(() -> {
    Converter<Companion, Banner> companionBannerConverter = companionToBannerConverter.apply(
      new Conversion(Companion.class, Banner.class)
    );
    return new AudioPlacementToAudioConverter(companionBannerConverter);
  });

  private ConverterProxy metricToMetricConverter = new ConverterProxy(net.media.converters
    .request25toRequest30.MetricToMetricConverter::new);

  private ConverterProxy dealToDealConverter = new ConverterProxy(DealToDealConverter::new);

  private ConverterProxy itemToImpConverter = new ConverterProxy(() -> {
    Converter<DisplayPlacement, Banner> displayPlacementBannerConverter = displayPlacementToBannerConverter.apply(
      new Conversion(DisplayPlacement.class, Banner.class)
    );
    Converter<DisplayPlacement, Native> displayPlacementNativeConverter = displayPlacementToNativeConverter.apply(
      new Conversion(DisplayPlacement.class, Native.class)
    );
    Converter<VideoPlacement, Video> videoPlacementVideoConverter = videoPlacementToVideoConverter.apply(
      new Conversion(VideoPlacement.class, Video.class)
    );
    Converter<AudioPlacement, Audio> audioPlacementAudioConverter = audioPlacementToAudioConverter.apply(
      new Conversion(AudioPlacement.class, Audio.class)
    );
    Converter<Metric, net.media.openrtb25.request.Metric> metricMetricConverter = metricToMetricConverter.apply(
      new Conversion(Metric.class, net.media.openrtb25.request.Metric.class)
    );
    Converter<Deal, net.media.openrtb25.request.Deal> dealDealConverter = dealToDealConverter.apply(
      new Conversion(Deal.class, net.media.openrtb25.request.Deal.class)
    );
    return new ItemToImpConverter(displayPlacementBannerConverter, displayPlacementNativeConverter,
      videoPlacementVideoConverter, audioPlacementAudioConverter, metricMetricConverter, dealDealConverter);
  });

  private ConverterProxy geoToGeoConverter = new ConverterProxy(GeoToGeoConverter::new);

  private ConverterProxy deviceToDeviceConverter = new ConverterProxy(() -> {
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter =  geoToGeoConverter.apply(new Conversion(
      Geo.class, net.media.openrtb25.request.Geo.class)
    );
    return new DeviceToDeviceConverter(geoGeoConverter);
  });

  private ConverterProxy producerToProducerConverter = new ConverterProxy(ProducerToProducerConverter::new);

  private ConverterProxy segmentToSegmentConverter = new ConverterProxy(SegmentToSegmentConverter::new);

  private ConverterProxy dataToDataConverter = new ConverterProxy(() -> {
    Converter<Segment, net.media.openrtb25.request.Segment> segmentSegmentConverter = segmentToSegmentConverter.apply(
      new Conversion(Segment.class, net.media.openrtb25.request.Segment.class)
    );
    return new DataToDataConverter(segmentSegmentConverter);
  });

  private ConverterProxy contentToContentConverter = new ConverterProxy(() ->{
    Converter<Data, net.media.openrtb25.request.Data> dataDataConverter = dataToDataConverter.apply(
      new Conversion(Data.class, net.media.openrtb25.request.Data.class)
    );
    Converter<Producer, net.media.openrtb25.request.Producer> producerProducerConverter = producerToProducerConverter.apply(
      new Conversion(Producer.class, net.media.openrtb25.request.Producer.class)
    );
    return new ContentToContentConverter(producerProducerConverter, dataDataConverter);
  });

  private ConverterProxy publisherToPublisherConverter = new ConverterProxy(PublisherToPublisherConverter::new);

  private ConverterProxy siteToSiteConverter = new ConverterProxy(() -> {
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter = contentToContentConverter.apply(
      new Conversion(Content.class, net.media.openrtb25.request.Content.class)
    );
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter = publisherToPublisherConverter.apply(
      new Conversion(Site.class, net.media.openrtb25.request.Site.class)
    );
    return new SiteToSiteConverter(publisherPublisherConverter, contentContentConverter);
  });

  private ConverterProxy appToAppConverter = new ConverterProxy(() -> {
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter = contentToContentConverter.apply(
      new Conversion(Content.class, net.media.openrtb25.request.Content.class)
    );
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter = publisherToPublisherConverter.apply(
      new Conversion(Site.class, net.media.openrtb25.request.Site.class)
    );
    return new AppToAppConverter(publisherPublisherConverter, contentContentConverter);
  });

  private ConverterProxy userToUserConverter = new ConverterProxy(() ->{
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter = geoToGeoConverter.apply(
      new Conversion(Geo.class, net.media.openrtb25.request.Geo.class)
    );
    Converter<Data, net.media.openrtb25.request.Data> dataDataConverter = dataToDataConverter.apply(
      new Conversion(Data.class, net.media.openrtb25.request.Data.class)
    );
    return new UserToUserConverter(geoGeoConverter, dataDataConverter);
  });

  private ConverterProxy requestToUserConverter = new ConverterProxy(RequestToUserConverter::new);

  private ConverterProxy regsToRegsConverter = new ConverterProxy(RegsToRegsConverter::new);

  private ConverterProxy sourceToSourceConverter = new ConverterProxy(SourceToSourceConverter::new);

  private ConverterProxy requestToBidRequestConverter = new ConverterProxy(() ->{
    Converter<User, net.media.openrtb25.request.User> userUserConverter = userToUserConverter.apply(
      new Conversion(User.class, net.media.openrtb25.request.User.class)
    );
    Converter<Request, net.media.openrtb25.request.User> requestUserConverter = requestToUserConverter.apply(
      new Conversion(Request.class, net.media.openrtb25.request.User.class)
    );
    Converter<App, net.media.openrtb25.request.App> appAppConverter = appToAppConverter.apply(
      new Conversion(App.class, net.media.openrtb25.request.App.class)
    );
    Converter<Regs, net.media.openrtb25.request.Regs> regsRegsConverter = regsToRegsConverter.apply(
      new Conversion(Regs.class, net.media.openrtb25.request.Regs.class)
    );
    Converter<Site, net.media.openrtb25.request.Site> siteSiteConverter = siteToSiteConverter.apply(
      new Conversion(Site.class, net.media.openrtb25.request.Site.class)
    );
    Converter<Device, net.media.openrtb25.request.Device> deviceDeviceConverter = deviceToDeviceConverter.apply(
      new Conversion(Device.class, net.media.openrtb25.request.Device.class)
    );
    Converter<Source, net.media.openrtb25.request.Source> sourceSourceConverter = sourceToSourceConverter.apply(
      new Conversion(Source.class, net.media.openrtb25.request.Source.class)
    );
    Converter<Item, net.media.openrtb25.request.Imp> itemImpConverter = itemToImpConverter.apply(
      new Conversion(Item.class, net.media.openrtb25.request.Imp.class)
    );
    return new RequestToBidRequestConverter(userUserConverter, requestUserConverter, appAppConverter, regsRegsConverter,
      siteSiteConverter, deviceDeviceConverter, sourceSourceConverter, itemImpConverter);
  });

  private ConverterProxy openRtbToBidRequestConverter = new ConverterProxy(() -> {
    Converter<Request, net.media.openrtb25.request.BidRequest> requestBidRequestConverter = requestToBidRequestConverter.apply(
      new Conversion(Request.class, net.media.openrtb25.request.BidRequest.class)
    );
    return new OpenRtbToBidRequestConverter(requestBidRequestConverter);
  });

  public ConverterProxy getOpenRtbToBidRequestConverterProxy() {
    return openRtbToBidRequestConverter;
  }
}
