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

package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/** Created by shiva.b on 10/04/19. */
public class OpenRtbWrapperToBidResponseConverter
    implements Converter<OpenRTBWrapper3_X, BidResponse2_X> {
  @Override
  public BidResponse2_X map(OpenRTBWrapper3_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    if (isNull(source.getOpenrtb())) {
      return null;
    }
    Converter<OpenRTB3_X, BidResponse2_X> openRTB3_xBidResponse2_xConverter =
        converterProvider.fetch(new Conversion<>(OpenRTB3_X.class, BidResponse2_X.class));
    return openRTB3_xBidResponse2_xConverter.map(source.getOpenrtb(), config, converterProvider);
  }

  @Override
  public void enhance(
      OpenRTBWrapper3_X source, BidResponse2_X target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {}
}
