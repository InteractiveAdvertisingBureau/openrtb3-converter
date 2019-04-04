package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class DeviceToDeviceConverter extends net.media.converters
  .request30toRequest25.DeviceToDeviceConverter {

  public void enhance(Device source, net.media.openrtb25.request.Device target, Config config,
                      Provider<Conversion, Converter> converterProvider)
    throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    if (nonNull(target.getMccmnc())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("mccmnc", target.getMccmnc());
      target.setMccmnc(null);
    }
    if (nonNull(target.getGeofetch())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("geofetch", target.getGeofetch());
      target.setGeofetch(null);
    }
  }
}
