/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.driver;

import net.media.converters.request23toRequest30.BannerToDisplayPlacementConverter;
import net.media.converters.request23toRequest30.BidRequestToRequestConverter;
import net.media.converters.request23toRequest30.ContentToContentConverter;
import net.media.converters.request23toRequest30.DeviceToDeviceConverter;
import net.media.converters.request23toRequest30.GeoToGeoConverter;
import net.media.converters.request23toRequest30.ImpToItemConverter;
import net.media.converters.request23toRequest30.VideoToVideoPlacementConverter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Geo;
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
public class Convert23To30RequestManager {

  public Convert23To30RequestManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Banner.class, DisplayPlacement.class), new
      BannerToDisplayPlacementConverter());
    converterProvider.register(new Conversion<>(BidRequest2_X.class, Request.class), new
      BidRequestToRequestConverter());
    converterProvider.register(new Conversion<>(Device.class, net.media.openrtb3.Device.class), new
      DeviceToDeviceConverter());
    converterProvider.register(new Conversion<>(Imp.class, Item.class), new ImpToItemConverter());
    converterProvider.register(new Conversion<>(Video.class, VideoPlacement.class), new
      VideoToVideoPlacementConverter());
    converterProvider.register(new Conversion<>(Content.class, net.media.openrtb3.Content.class), new
      ContentToContentConverter());
    converterProvider.register(new Conversion<>(Geo.class, net.media.openrtb3.Geo.class), new
      GeoToGeoConverter());
  }
}
