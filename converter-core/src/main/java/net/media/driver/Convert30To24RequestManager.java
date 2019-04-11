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

import net.media.converters.request30toRequest24.DisplayPlacementToBannerConverter;
import net.media.converters.request30toRequest24.DeviceToDeviceConverter;
import net.media.converters.request30toRequest24.ItemToImpConverter;
import net.media.converters.request30toRequest24.RequestToBidRequestConverter;
import net.media.converters.request30toRequest24.VideoPlacementToVideoConverter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Device;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Convert30To24RequestManager {

  public Convert30To24RequestManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Device.class, net.media.openrtb25.request.Device
      .class), new DeviceToDeviceConverter());
    converterProvider.register(new Conversion<>(DisplayPlacement.class, Banner.class), new
      DisplayPlacementToBannerConverter());
    converterProvider.register(new Conversion<>(Item.class, Imp.class), new ItemToImpConverter());
    converterProvider.register(new Conversion<>(Request.class, BidRequest2_X.class), new
      RequestToBidRequestConverter());
    converterProvider.register(new Conversion<>(VideoPlacement.class, Video.class), new
      VideoPlacementToVideoConverter());
  }
}
