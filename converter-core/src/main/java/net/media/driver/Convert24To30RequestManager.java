package net.media.driver;

import net.media.converters.request24toRequest30.BannerToDisplayPlacementConverter;
import net.media.converters.request24toRequest30.BidRequestToRequestConverter;
import net.media.converters.request24toRequest30.DeviceToDeviceConverter;
import net.media.converters.request24toRequest30.ImpToItemConverter;
import net.media.converters.request24toRequest30.VideoToVideoPlacementConverter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Convert24To30RequestManager {

  public Convert24To30RequestManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Banner.class, DisplayPlacement.class), new
      BannerToDisplayPlacementConverter());
    converterProvider.register(new Conversion<>(BidRequest2_X.class, Request.class), new
      BidRequestToRequestConverter());
    converterProvider.register(new Conversion<>(Device.class, net.media.openrtb3.Device.class), new
      DeviceToDeviceConverter());
    converterProvider.register(new Conversion<>(Imp.class, Item.class), new ImpToItemConverter());
    converterProvider.register(new Conversion<>(Video.class, VideoPlacement.class), new
      VideoToVideoPlacementConverter());
  }
}
