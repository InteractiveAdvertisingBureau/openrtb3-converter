/*
 * Copyright  2019 - present. IAB Tech Lab
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

import net.media.converters.response25toresponse30.*;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.*;
import net.media.utils.Provider;

/** Created by shiva.b on 28/03/19. */
@SuppressWarnings("unchecked")
public class Convert25To30ResponseManager {

  public Convert25To30ResponseManager(Provider converterProvider) {
    converterProvider.register(
        new Conversion<>(Link.class, LinkAsset.class), new LinkToLinkAssetConverter());
    converterProvider.register(
        new Conversion<>(AssetResponse.class, Asset.class), new Asset25ToAsset30Converter());
    converterProvider.register(
        new Conversion<>(NativeResponse.class, Native.class), new Native25ToNative30Converter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Display.class),
        new BidToDisplayConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Audit.class),
        new BidToAuditConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Audio.class),
        new BidToAudioConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Video.class),
        new BidToVideoConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Ad.class), new BidToAdConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Media.class),
        new Bid25ToMediaConverter());
    converterProvider.register(
        new Conversion<>(net.media.openrtb25.response.Bid.class, Bid.class),
        new Bid25ToBid30Converter());
    converterProvider.register(
        new Conversion<>(SeatBid.class, Seatbid.class), new SeatBid25ToSeatBid30Converter());
    converterProvider.register(
        new Conversion<>(BidResponse2_X.class, Response.class),
        new BidResponseToResponseConverter());
    converterProvider.register(
        new Conversion<>(BidResponse2_X.class, OpenRTB3_X.class),
        new BidResponseToOpenRtbConverter());
    converterProvider.register(
        new Conversion<>(BidResponse2_X.class, OpenRTBWrapper3_X.class),
        new BidResponseToOpenRtbWrapperConverter());
  }
}
