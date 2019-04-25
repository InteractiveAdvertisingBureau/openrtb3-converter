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

package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Item;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class ItemToImpConverter
    extends net.media.converters.request30toRequest25.ItemToImpConverter {

  public void enhance(Item item, Imp imp, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (item == null || imp == null) {
      return;
    }
    super.enhance(item, imp, config, converterProvider);
    if (nonNull(imp.getMetric())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put(CommonConstants.METRIC, imp.getMetric());
      imp.setMetric(null);
    }
    if (nonNull(imp.getClickbrowser())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put(CommonConstants.CLICKBROWSER, imp.getClickbrowser());
      imp.setClickbrowser(null);
    }
    if (nonNull(imp.getExp())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put(CommonConstants.EXP, imp.getExp());
      imp.setExp(null);
    }
  }
}
