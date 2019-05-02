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

package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Media;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

public class MediatoBidConverter implements Converter<Media, Bid> {

  public Bid map(Media source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Bid bid = new Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  public void enhance(Media source, Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    Converter<Ad, Bid> adBidConverter =
        converterProvider.fetch(new Conversion<>(Ad.class, Bid.class));
    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }
    adBidConverter.enhance(source.getAd(), target, config, converterProvider);
  }
}
