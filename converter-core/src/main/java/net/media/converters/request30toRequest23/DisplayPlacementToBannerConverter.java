package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class DisplayPlacementToBannerConverter extends net.media.converters
  .request30toRequest25.DisplayPlacementToBannerConverter {

  public void enhance(DisplayPlacement displayPlacement, Banner banner, Config config,
                      Provider converterProvider) {
    if (displayPlacement == null || banner == null) {
      return;
    }
    super.enhance(displayPlacement, banner, config, converterProvider);
    if (nonNull(banner.getVcm())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("vcm", banner.getVcm());
      banner.setVcm(null);
    }
    if (nonNull(banner.getFormat())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("format", banner.getFormat());
      banner.setFormat(null);
    }
  }
}
