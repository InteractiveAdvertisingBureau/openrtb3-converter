package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class BannerToDisplayPlacementConverter extends net.media.converters
  .request25toRequest30.BannerToDisplayPlacementConverter {

  public void enhance(Banner banner, DisplayPlacement displayPlacement, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (banner == null || displayPlacement == null) {
      return;
    }
    if (nonNull(banner.getExt())) {
      if (banner.getExt().containsKey("vcm")) {
        try {
          banner.setVcm((Integer) banner.getExt().get("vcm"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting vcm from banner.ext.vcm",
            e);
        }
        banner.getExt().remove("vcm");
      }
    }
    if (nonNull(banner.getFormat())) {
      for (Format format : banner.getFormat()) {
        if (nonNull(format.getExt())) {
          if (format.getExt().containsKey("wratio")) {
            try {
              format.setWratio((Integer) format.getExt().get("wratio"));
            } catch (Exception e) {
              throw new OpenRtbConverterException("Error in setting wratio from banner.format.ext" +
                ".wratio", e);
            }
            format.getExt().remove("wratio");
          }
          if (format.getExt().containsKey("hratio")) {
            try {
              format.setHratio((Integer) format.getExt().get("hratio"));
            } catch (Exception e) {
              throw new OpenRtbConverterException("Error in setting hratio from banner.ext" +
                ".hratio", e);
            }
            format.getExt().remove("hratio");
          }
          if (format.getExt().containsKey("wmin")) {
            try {
              format.setWmin((Integer) format.getExt().get("wmin"));
            } catch (Exception e) {
              throw new OpenRtbConverterException("Error in setting wmin from banner.ext" +
                ".wmin", e);
            }
            format.getExt().remove("wmin");
          }
        }
      }
    }
    super.enhance(banner, displayPlacement, config, converterProvider);
  }
}
