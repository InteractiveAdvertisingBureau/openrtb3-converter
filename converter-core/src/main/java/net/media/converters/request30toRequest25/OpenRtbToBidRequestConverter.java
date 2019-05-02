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

package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

public class OpenRtbToBidRequestConverter implements Converter<OpenRTB3_X, BidRequest2_X> {

  @Override
  public BidRequest2_X map(OpenRTB3_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    if (isNull(source.getRequest())) {
      return null;
    }
    Converter<Request, BidRequest2_X> requestBidRequestConverter =
        converterProvider.fetch(new Conversion<>(Request.class, BidRequest2_X.class));
    return requestBidRequestConverter.map(source.getRequest(), config, converterProvider);
  }

  @Override
  public void enhance(
      OpenRTB3_X source, BidRequest2_X target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    throw new OpenRtbConverterException("Enhance method not supported for OpenRtbToBidRequestConverter");
  }
}
