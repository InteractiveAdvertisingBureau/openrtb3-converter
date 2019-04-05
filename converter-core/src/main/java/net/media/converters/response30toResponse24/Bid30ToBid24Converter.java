package net.media.converters.response30toResponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.converters.response30toresponse25.Bid30ToBid25Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class Bid30ToBid24Converter extends Bid30ToBid25Converter {

  public void enhance(net.media.openrtb3.Bid source, Bid target, Config config,
                      Provider converterProvider) throws
    OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    if (nonNull(target.getBurl())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("burl", target.getBurl());
      target.setBurl(null);
    }
    if (nonNull(target.getLurl())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("lurl", target.getLurl());
      target.setNurl(null);
    }
    if (nonNull(target.getTactic())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("tactic", target.getTactic());
      target.setTactic(null);
    }
    if (nonNull(target.getLanguage())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("language", target.getLanguage());
      target.setLanguage(null);
    }
    if (nonNull(target.getWratio())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("wratio", target.getWratio());
      target.setWratio(null);
    }
    if (nonNull(target.getHratio())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("hratio", target.getHratio());
      target.setHratio(null);
    }
  }

}
