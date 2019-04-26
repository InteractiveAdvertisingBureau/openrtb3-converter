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

package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.Request;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.putToExt;

/** Created by rajat.go on 03/04/19. */
public class RequestToBidRequestConverter
    extends net.media.converters.request30toRequest25.RequestToBidRequestConverter {

  public void enhance(
      Request source, BidRequest2_X target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    putToExt(target::getBseat, target.getExt(), CommonConstants.BSEAT, target::setExt);
    target.setBseat(null);
    putToExt(target::getWlang, target.getExt(), CommonConstants.WLANG, target::setExt);
    target.setWlang(null);
    putToExt(target::getSource, target.getExt(), CommonConstants.SOURCE, target::setExt);
    target.setSource(null);
  }
}
