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

package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/04/19. */
public class BannerToDisplayPlacementConverter
    extends net.media.converters.request25toRequest30.BannerToDisplayPlacementConverter {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  private static final List<String> extraFieldsInDisplayFormatExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.VCM);
    extraFieldsInDisplayFormatExt.add(CommonConstants.WRATIO);
    extraFieldsInDisplayFormatExt.add(CommonConstants.HRATIO);
    extraFieldsInExt.add(CommonConstants.WMIN);
  }

  public void enhance(
      Banner banner, DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (banner == null || displayPlacement == null) {
      return;
    }
    fetchFromExt(
      banner::setVcm,
      banner.getExt(),
      CommonConstants.VCM,
      "Error in setting vcm from banner.ext.vcm");
    if (nonNull(banner.getFormat())) {
      for (Format format : banner.getFormat()) {
        fetchFromExt(
          format::setWratio,
          format.getExt(),
          CommonConstants.WRATIO,
          "Error in setting wratio from banner.format.ext.wratio");
        fetchFromExt(
          format::setHratio,
          format.getExt(),
          CommonConstants.HRATIO,
          "Error in setting hratio from banner.format.ext.hratio");
        fetchFromExt(
          format::setWmin,
          format.getExt(),
          CommonConstants.WMIN,
          "Error in setting wmin from banner.format.ext.wmin");
      }
    }
    super.enhance(banner, displayPlacement, config, converterProvider);
    removeFromExt(displayPlacement.getExt(), extraFieldsInExt);
    for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
      removeFromExt(displayFormat.getExt(), extraFieldsInDisplayFormatExt);
    }
  }
}
