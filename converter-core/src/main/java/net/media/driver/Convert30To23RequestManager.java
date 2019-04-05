package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.request30toRequest23.DeviceToDeviceConverter;
import net.media.converters.request30toRequest23.DisplayPlacementToBannerConverter;
import net.media.converters.request30toRequest23.GeoToGeoConverter;
import net.media.converters.request30toRequest23.ItemToImpConverter;
import net.media.converters.request30toRequest23.RequestToBidRequestConverter;
import net.media.converters.request30toRequest23.VideoPlacementToVideoConverter;
import net.media.converters.request30toRequest23.ContentToContentConverter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Content;
import net.media.openrtb3.Device;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Convert30To23RequestManager {

  public Convert30To23RequestManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Device.class, net.media.openrtb25.request.Device
      .class), new DeviceToDeviceConverter());
    converterProvider.register(new Conversion<>(DisplayPlacement.class, Banner.class), new
      DisplayPlacementToBannerConverter());
    converterProvider.register(new Conversion<>(Item.class, Imp.class), new ItemToImpConverter());
    converterProvider.register(new Conversion<>(Request.class, BidRequest.class), new
      RequestToBidRequestConverter());
    converterProvider.register(new Conversion<>(VideoPlacement.class, Video.class), new
      VideoPlacementToVideoConverter());
    converterProvider.register(new Conversion<>(Content.class, net.media.openrtb25.request.Content
      .class), new ContentToContentConverter());
    converterProvider.register(new Conversion<>(Geo.class, net.media.openrtb25.request.Geo.class),
      new GeoToGeoConverter());
  }
}
