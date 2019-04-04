package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class GeoToGeoConverter extends net.media.converters.request25toRequest30.GeoToGeoConverter {

  public void enhance(Geo source, net.media.openrtb3.Geo target, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("accuracy")) {
        try {
          source.setAccuracy((Integer) source.getExt().get("accuracy"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting accuracy from geo.ext.accuracy",
            e);
        }
        source.getExt().remove("accuracy");
      }
      if (source.getExt().containsKey("lastfix")) {
        try {
          source.setLastfix((Integer) source.getExt().get("lastfix"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting lastfix from geo.ext.lastfix",
            e);
        }
        source.getExt().remove("lastfix");
      }
      if (source.getExt().containsKey("ipservice")) {
        try {
          source.setIpservice((Integer) source.getExt().get("ipservice"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting ipservice from geo.ext.ipservice",
            e);
        }
        source.getExt().remove("ipservice");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
