/*
 * Copyright © 2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

import net.media.converters.request30toRequest25.*;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.*;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.App;
import net.media.openrtb3.*;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Metric;
import net.media.openrtb3.Producer;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.Source;
import net.media.openrtb3.User;
import net.media.utils.Provider;

@SuppressWarnings("unchecked")
public class Convert30To25RequestManager {

  public Convert30To25RequestManager(Provider converterProvider) {
    converterProvider.register(
        new Conversion<>(AssetFormat.class, Asset.class), new AssetFormatToAssetConverter());
    converterProvider.register(
        new Conversion<>(NativeFormat.class, NativeRequestBody.class),
        new NativeFormatToNativeRequestBodyConverter());
    converterProvider.register(
        new Conversion<>(DisplayPlacement.class, NativeRequest.class),
        new DisplayPlacementToNativeRequestConverter());
    converterProvider.register(
        new Conversion<>(DisplayPlacement.class, Banner.class),
        new DisplayPlacementToBannerConverter());
    converterProvider.register(
        new Conversion<>(Companion.class, Banner.class), new CompanionToBannerConverter());
    converterProvider.register(
        new Conversion<>(VideoPlacement.class, Video.class), new VideoPlacementToVideoConverter());
    converterProvider.register(
        new Conversion<>(AudioPlacement.class, Audio.class), new AudioPlacementToAudioConverter());
    converterProvider.register(
        new Conversion<>(Metric.class, net.media.openrtb25.request.Metric.class),
        new net.media.converters.request30toRequest25.MetricToMetricConverter());
    converterProvider.register(
        new Conversion<>(Deal.class, net.media.openrtb25.request.Deal.class),
        new DealToDealConverter());
    converterProvider.register(new Conversion<>(Item.class, Imp.class), new ItemToImpConverter());
    converterProvider.register(
        new Conversion<>(Geo.class, net.media.openrtb25.request.Geo.class),
        new GeoToGeoConverter());
    converterProvider.register(
        new Conversion<>(Device.class, net.media.openrtb25.request.Device.class),
        new DeviceToDeviceConverter());
    converterProvider.register(
        new Conversion<>(Producer.class, net.media.openrtb25.request.Producer.class),
        new ProducerToProducerConverter());
    converterProvider.register(
        new Conversion<>(Segment.class, net.media.openrtb25.request.Segment.class),
        new SegmentToSegmentConverter());
    converterProvider.register(
        new Conversion<>(Data.class, net.media.openrtb25.request.Data.class),
        new DataToDataConverter());
    converterProvider.register(
        new Conversion<>(Content.class, net.media.openrtb25.request.Content.class),
        new ContentToContentConverter());
    converterProvider.register(
        new Conversion<>(Publisher.class, net.media.openrtb25.request.Publisher.class),
        new PublisherToPublisherConverter());
    converterProvider.register(
        new Conversion<>(Site.class, net.media.openrtb25.request.Site.class),
        new SiteToSiteConverter());
    converterProvider.register(
        new Conversion<>(App.class, net.media.openrtb25.request.App.class),
        new AppToAppConverter());
    converterProvider.register(
        new Conversion<>(User.class, net.media.openrtb25.request.User.class),
        new UserToUserConverter());
    converterProvider.register(
        new Conversion<>(Request.class, net.media.openrtb25.request.User.class),
        new RequestToUserConverter());
    converterProvider.register(
        new Conversion<>(Regs.class, net.media.openrtb25.request.Regs.class),
        new RegsToRegsConverter());
    converterProvider.register(
        new Conversion<>(Source.class, net.media.openrtb25.request.Source.class),
        new SourceToSourceConverter());
    converterProvider.register(
        new Conversion<>(Request.class, BidRequest2_X.class), new RequestToBidRequestConverter());
    converterProvider.register(
        new Conversion<>(OpenRTB3_X.class, BidRequest2_X.class),
        new OpenRtbToBidRequestConverter());
    converterProvider.register(
        new Conversion<>(OpenRTBWrapper3_X.class, BidRequest2_X.class),
        new OpenRtbWrapperToBidRequestConverter());
  }
}
