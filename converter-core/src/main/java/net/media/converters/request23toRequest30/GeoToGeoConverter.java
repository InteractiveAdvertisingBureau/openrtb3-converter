package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class GeoToGeoConverter extends net.media.converters.request25toRequest30.GeoToGeoConverter {

  public void enhance(Geo source, net.media.openrtb3.Geo target, Config config,
                      Provider<Conversion, Converter> converterProvider) {
    if (source == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("accuracy")) {
        source.setAccuracy((Integer) source.getExt().get("accuracy"));
        source.getExt().remove("accuracy");
      }
      if (source.getExt().containsKey("lastfix")) {
        source.setLastfix((Integer) source.getExt().get("lastfix"));
        source.getExt().remove("lastfix");
      }
      if (source.getExt().containsKey("ipservice")) {
        source.setIpservice((Integer) source.getExt().get("ipservice"));
        source.getExt().remove("ipservice");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
