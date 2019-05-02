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

package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Source;
import net.media.openrtb3.Request;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 02/04/19. */
public class BidRequestToRequestConverter
    extends net.media.converters.request25toRequest30.BidRequestToRequestConverter {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.BSEAT);
    extraFieldsInExt.add(CommonConstants.WLANG);
    extraFieldsInExt.add(CommonConstants.SOURCE);
    extraFieldsInExt.add(CommonConstants.WMIN);
  }

  public void enhance(
      BidRequest2_X source, Request target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    fetchFromExt(
      source::setBseat,
      source.getExt(),
      CommonConstants.BSEAT,
      "Error in setting bseat from bidRequest.ext.bseat");
    fetchFromExt(
      source::setWlang,
      source.getExt(),
      CommonConstants.WLANG,
      "Error in setting wlang from bidRequest.ext.wlang");
    fetchFromExt(
      source::setSource,
      source.getExt(),
      CommonConstants.SOURCE,
      "Error in setting source from bidRequest.ext.source",
      Source.class);
    super.enhance(source, target, config, converterProvider);
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
