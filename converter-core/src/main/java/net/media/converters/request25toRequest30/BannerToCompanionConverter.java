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

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

/** Created by rajat.go on 03/01/19. */
public class BannerToCompanionConverter implements Converter<Banner, Companion> {

  static List<String> extraFieldsInDisplay = new ArrayList<>();
  static {
    extraFieldsInDisplay.add("id");
  }

  @Override
  public Companion map(Banner banner, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (banner == null) {
      return null;
    }

    Companion companion = new Companion();
    enhance(banner, companion, config, converterProvider);

    return companion;
  }

  @Override
  public void enhance(Banner banner, Companion companion, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (banner == null || companion == null) {
      return;
    }
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter =
        converterProvider.fetch(new Conversion<>(Banner.class, DisplayPlacement.class));
    companion.setVcm(banner.getVcm());
    companion.setDisplay(bannerDisplayPlacementConverter.map(banner, config, converterProvider));
    companion.setId(banner.getId());
    if (companion.getDisplay() != null) {
      companion.getDisplay().setClktype(null); // remove clktype for companion
      removeFromExt(companion.getDisplay().getExt(),extraFieldsInDisplay);
    }
  }
}
