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

package net.media.converters.request25toRequest30;

import static java.util.Objects.isNull;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.Provider;

/**
 * Created by shiva.b on 10/04/19.
 */
public class BidRequestToOpenRtbWrapperConverter
    implements Converter<BidRequest2_X, OpenRTBWrapper3_X> {

  @Override
  public OpenRTBWrapper3_X map(BidRequest2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    OpenRTBWrapper3_X openRTBWrapper3_x = new OpenRTBWrapper3_X();
    Converter<BidRequest2_X, OpenRTB3_X> bidRequestToOpenRtbConverter =
        converterProvider.fetch(new Conversion<>(BidRequest2_X.class, OpenRTB3_X.class));
    openRTBWrapper3_x.setOpenrtb(
        bidRequestToOpenRtbConverter.map(source, config, converterProvider));
    return openRTBWrapper3_x;
  }

  @Override
  public void enhance(
      BidRequest2_X source, OpenRTBWrapper3_X target, Config config, Provider converterProvider) {
  }
}
