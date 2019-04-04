package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.App;
import net.media.openrtb3.Device;
import net.media.openrtb3.Item;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Request;
import net.media.openrtb3.Site;
import net.media.openrtb3.Source;
import net.media.openrtb3.User;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class RequestToBidRequestConverter extends net.media.converters
  .request30toRequest25.RequestToBidRequestConverter {

  public void enhance(Request source, BidRequest target, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    if (nonNull(target.getBseat())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("bseat", target.getBseat());
      target.setBseat(null);
    }
    if (nonNull(target.getWlang())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("wlang", target.getWlang());
      target.setWlang(null);
    }
    if (nonNull(target.getSource())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("source", target.getSource());
      target.setSource(null);
    }
  }
}