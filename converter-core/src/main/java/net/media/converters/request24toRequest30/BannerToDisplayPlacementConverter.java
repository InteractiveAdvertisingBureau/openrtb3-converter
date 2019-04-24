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
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class BannerToDisplayPlacementConverter
    extends net.media.converters.request25toRequest30.BannerToDisplayPlacementConverter {

  public void enhance(
      Banner banner, DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (banner == null || displayPlacement == null) {
      return;
    }
    if (nonNull(banner.getExt())) {
      if (banner.getExt().containsKey(CommonConstants.VCM)) {
        try {
          banner.setVcm((Integer) banner.getExt().get(CommonConstants.VCM));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting vcm from banner.ext.vcm", e);
        }
        banner.getExt().remove(CommonConstants.VCM);
      }
    }
    if (nonNull(banner.getFormat())) {
      for (Format format : banner.getFormat()) {
        if (nonNull(format.getExt())) {
          if (format.getExt().containsKey(CommonConstants.WRATIO)) {
            try {
              format.setWratio((Integer) format.getExt().get(CommonConstants.WRATIO));
            } catch (Exception e) {
              throw new OpenRtbConverterException(
                  "Error in setting wratio from banner.format.ext" + ".wratio", e);
            }
            format.getExt().remove(CommonConstants.WRATIO);
          }
          if (format.getExt().containsKey(CommonConstants.HRATIO)) {
            try {
              format.setHratio((Integer) format.getExt().get(CommonConstants.HRATIO));
            } catch (Exception e) {
              throw new OpenRtbConverterException(
                  "Error in setting hratio from banner.ext" + ".hratio", e);
            }
            format.getExt().remove(CommonConstants.HRATIO);
          }
          if (format.getExt().containsKey(CommonConstants.WMIN)) {
            try {
              format.setWmin((Integer) format.getExt().get(CommonConstants.WMIN));
            } catch (Exception e) {
              throw new OpenRtbConverterException(
                  "Error in setting wmin from banner.ext" + ".wmin", e);
            }
            format.getExt().remove(CommonConstants.WMIN);
          }
        }
      }
    }
    super.enhance(banner, displayPlacement, config, converterProvider);
  }
}
