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

package net.media.converters.response30toResponse24;

import net.media.config.Config;
import net.media.converters.response30toresponse25.Bid30ToBid25Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import static net.media.utils.ExtUtils.putToExt;

/** Created by rajat.go on 03/04/19. */
public class Bid30ToBid24Converter extends Bid30ToBid25Converter {

  public void enhance(
      net.media.openrtb3.Bid source, Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    putToExt(target::getBurl, target.getExt(), CommonConstants.BURL, target::setExt);
    target.setBurl(null);
    putToExt(target::getLurl, target.getExt(), CommonConstants.LURL, target::setExt);
    target.setLurl(null);
    putToExt(target::getTactic, target.getExt(), CommonConstants.TACTIC, target::setExt);
    target.setTactic(null);
    putToExt(target::getLanguage, target.getExt(), CommonConstants.LANGUAGE, target::setExt);
    target.setLanguage(null);
    putToExt(target::getWratio, target.getExt(), CommonConstants.WRATIO, target::setExt);
    target.setWratio(null);
    putToExt(target::getHratio, target.getExt(), CommonConstants.HRATIO, target::setExt);
    target.setHratio(null);
  }
}
