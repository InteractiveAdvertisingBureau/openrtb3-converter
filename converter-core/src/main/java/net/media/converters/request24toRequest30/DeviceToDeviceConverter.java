package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class DeviceToDeviceConverter extends net.media.converters
  .request25toRequest30.DeviceToDeviceConverter {

  public void enhance(Device source, net.media.openrtb3.Device target, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("mccmnc")) {
        try {
          source.setMccmnc((String) source.getExt().get("mccmnc"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting mccmnc from device.ext.mccmnc",
            e);
        }
        source.getExt().remove("mccmnc");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
