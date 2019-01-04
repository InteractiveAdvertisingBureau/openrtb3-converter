package net.media;

import net.media.converters.Converter;
import net.media.converters.request30toRequest24.*;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.*;

public class Converter30To24Plumber {

  public Converter<OpenRTB, BidRequest> openRtbToBidRequestConverter() {
    Converter<Request, BidRequest> requestBidRequestConverter = requestBidRequestConverter();
    return new OpenRtbToBidRequestConverter(requestBidRequestConverter);
  }

  private Converter<Request, BidRequest> requestBidRequestConverter() {
    Converter<User, net.media.openrtb24.request.User> userUserConverter = userUserConverter();
    Converter<Request, net.media.openrtb24.request.User> requestUserConverter = new RequestToUserConverter();
    Converter<App, net.media.openrtb24.request.App> appAppConverter = appAppConverter();
    Converter<Regs, net.media.openrtb24.request.Regs> regsRegsConverter = new RegsToRegsConverter();
    Converter<Site, net.media.openrtb24.request.Site> siteSiteConverter = siteSiteConverter();
    Converter<Device, net.media.openrtb24.request.Device> deviceDeviceConverter = deviceDeviceConverter();
    Converter<Source, net.media.openrtb24.request.Source> sourceSourceConverter = new SourceToSourceConverter();
    Converter<Item, Imp> itemImpConverter = new ItemToImpConverter();
    return new RequestToBidRequestConverter(userUserConverter, requestUserConverter, appAppConverter, regsRegsConverter,
      siteSiteConverter, deviceDeviceConverter, sourceSourceConverter, itemImpConverter);
  }

  private Converter<User, net.media.openrtb24.request.User> userUserConverter() {
    Converter<Geo, net.media.openrtb24.request.Geo> geoGeoConverter = new GeoToGeoConverter();
    Converter<Data, net.media.openrtb24.request.Data> dataDataConverter = dataDataConverter();
    return new UserToUserConverter(geoGeoConverter, dataDataConverter);
  }

  private Converter<Data, net.media.openrtb24.request.Data> dataDataConverter() {
    Converter<Segment, net.media.openrtb24.request.Segment> segmentSegmentConverter = new SegmentToSegmentConverter();
    return new DataToDataConverter(segmentSegmentConverter);
  }

  private Converter<App, net.media.openrtb24.request.App> appAppConverter() {
    Converter<Publisher, net.media.openrtb24.request.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<Content, net.media.openrtb24.request.Content> contentContentConverter = contentContentConverter();
    return new AppToAppConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<Content, net.media.openrtb24.request.Content> contentContentConverter() {
    Converter<Producer, net.media.openrtb24.request.Producer> producerProducerConverter = new ProducerToProducerConverter();
    Converter<Data, net.media.openrtb24.request.Data> dataDataConverter = dataDataConverter();
    return new ContentToContentConverter(producerProducerConverter, dataDataConverter);
  }

  private Converter<Site, net.media.openrtb24.request.Site> siteSiteConverter() {
    Converter<Publisher, net.media.openrtb24.request.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<Content, net.media.openrtb24.request.Content> contentContentConverter = contentContentConverter();
    return new SiteToSiteConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<Device, net.media.openrtb24.request.Device> deviceDeviceConverter() {
    Converter<Geo, net.media.openrtb24.request.Geo> geoGeoConverter = new GeoToGeoConverter();
    return new DeviceToDeviceConverter(geoGeoConverter);
  }
}
