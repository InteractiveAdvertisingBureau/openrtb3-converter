package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Provider;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class BannerToDisplayPlacementConverter extends net.media.converters
  .request25toRequest30.BannerToDisplayPlacementConverter {

  public void enhance(Banner banner, DisplayPlacement displayPlacement, Config config,
                      Provider converterProvider) throws
    OpenRtbConverterException {
    if (banner == null || displayPlacement == null) {
      return;
    }
    if (nonNull(banner.getExt())) {
      if (banner.getExt().containsKey("vcm")) {
        banner.setVcm((Integer) banner.getExt().get("vcm"));
        banner.getExt().remove("vcm");
      }
      if (banner.getExt().containsKey("format")) {
        try {
          banner.setFormat((Collection<Format>) banner.getExt().get("format"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting banner.format from banner.ext" +
            ".format", e);
        }
        banner.getExt().remove("format");
      }
    }
    super.enhance(banner, displayPlacement, config, converterProvider);
  }
}
