/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

import net.media.converters.response30toresponse25.AdToBidConverter;
import net.media.converters.response30toresponse25.Asset30ToAsset25Converter;
import net.media.converters.response30toresponse25.AudioToBidConverter;
import net.media.converters.response30toresponse25.Bid30ToBid25Converter;
import net.media.converters.response30toresponse25.DisplayToBidConverter;
import net.media.converters.response30toresponse25.LinkAssetToLinkConverter;
import net.media.converters.response30toresponse25.MediatoBidConverter;
import net.media.converters.response30toresponse25.Native30ToNative10Converter;
import net.media.converters.response30toresponse25.OpenRtbResponseToBidResponseConverter;
import net.media.converters.response30toresponse25.OpenRtbWrapperToBidResponseConverter;
import net.media.converters.response30toresponse25.SeatBid30ToSeatBid25Converter;
import net.media.converters.response30toresponse25.VideoToBidConverter;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Asset;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Media;
import net.media.openrtb3.Native;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
import net.media.utils.Provider;

/**
 * Created by shiva.b on 28/03/19.
 */
@SuppressWarnings("unchecked")
public class Convert30To25ResponseManager {

  public Convert30To25ResponseManager(Provider converterProvider) {
    converterProvider.register(
        new Conversion<>(LinkAsset.class, Link.class), new LinkAssetToLinkConverter());
    converterProvider.register(
        new Conversion<>(Asset.class, AssetResponse.class), new Asset30ToAsset25Converter());
    converterProvider.register(
        new Conversion<>(Native.class, NativeResponse.class), new Native30ToNative10Converter());
    converterProvider.register(
        new Conversion<>(Display.class, net.media.openrtb25.response.Bid.class),
        new DisplayToBidConverter());
    converterProvider.register(
        new Conversion<>(Video.class, net.media.openrtb25.response.Bid.class),
        new VideoToBidConverter());
    converterProvider.register(
        new Conversion<>(Audio.class, net.media.openrtb25.response.Bid.class),
        new AudioToBidConverter());
    converterProvider.register(
        new Conversion<>(Ad.class, net.media.openrtb25.response.Bid.class), new AdToBidConverter());
    converterProvider.register(
        new Conversion<>(Media.class, net.media.openrtb25.response.Bid.class),
        new MediatoBidConverter());
    converterProvider.register(
        new Conversion<>(Bid.class, net.media.openrtb25.response.Bid.class),
        new Bid30ToBid25Converter());
    converterProvider.register(
        new Conversion<>(Seatbid.class, SeatBid.class), new SeatBid30ToSeatBid25Converter());
    converterProvider.register(
        new Conversion<>(OpenRTB3_X.class, BidResponse2_X.class),
        new OpenRtbResponseToBidResponseConverter());
    converterProvider.register(
        new Conversion<>(OpenRTBWrapper3_X.class, BidResponse2_X.class),
        new OpenRtbWrapperToBidResponseConverter());
  }
}
