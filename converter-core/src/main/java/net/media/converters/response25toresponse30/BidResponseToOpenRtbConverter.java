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

package net.media.converters.response25toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.Response;
import net.media.utils.Provider;

/** @author shiva.b */
public class BidResponseToOpenRtbConverter implements Converter<BidResponse2_X, OpenRTB3_X> {

  /**
   * @param source
   * @param config
   * @return
   */
  @Override
  public OpenRTB3_X map(BidResponse2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    OpenRTB3_X openRTB = new OpenRTB3_X();
    enhance(source, openRTB, config, converterProvider);
    return openRTB;
  }

  /**
   * @param source
   * @param target
   */
  @Override
  public void enhance(
      BidResponse2_X source, OpenRTB3_X target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    target.setDomainSpec("3.0");
    Converter<BidResponse2_X, Response> converter =
        converterProvider.fetch(new Conversion<>(BidResponse2_X.class, Response.class));
    target.setResponse(converter.map(source, config, converterProvider));
  }
}
