package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb3.Geo;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class GeoToGeoConverter extends net.media.converters.request30toRequest25.GeoToGeoConverter {

  public void enhance(Geo source, net.media.openrtb25.request.Geo target, Config config,
                      Provider converterProvider) {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    if (nonNull(target.getAccuracy())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("accuracy", target.getAccuracy());
      target.setAccuracy(null);
    }
    if (nonNull(target.getLastfix())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("lastfix", target.getLastfix());
      target.setLastfix(null);
    }
    if (nonNull(target.getIpservice())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("ipservice", target.getIpservice());
      target.setIpservice(null);
    }
  }
}
