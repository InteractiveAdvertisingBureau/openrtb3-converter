package net.media.converters.response24toResponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.converters.response25toresponse30.Bid25ToBid30Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Media;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class Bid24ToBid30Converter extends Bid25ToBid30Converter {

  public void enhance(Bid source, net.media.openrtb3.Bid target, Config config,
                      Provider converterProvider) throws
    OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("burl")) {
        source.setBurl((String) source.getExt().get("burl"));
        source.getExt().remove("burl");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("lurl")) {
        source.setLurl((String) source.getExt().get("lurl"));
        source.getExt().remove("lurl");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("tactic")) {
        source.setTactic((String) source.getExt().get("tactic"));
        source.getExt().remove("tactic");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("language")) {
        source.setLanguage((String) source.getExt().get("language"));
        source.getExt().remove("language");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("wratio")) {
        source.setWratio((Integer) source.getExt().get("wratio"));
        source.getExt().remove("wratio");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("hratio")) {
        source.setHratio((Integer) source.getExt().get("hratio"));
        source.getExt().remove("hratio");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}