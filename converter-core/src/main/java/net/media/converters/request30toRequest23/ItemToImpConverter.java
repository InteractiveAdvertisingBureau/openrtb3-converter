package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Item;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ItemToImpConverter extends net.media.converters.request30toRequest25.ItemToImpConverter {

  public void enhance(Item item, Imp imp, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (item == null || imp == null) {
      return;
    }
    super.enhance(item, imp, config, converterProvider);
    if (nonNull(imp.getMetric())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put("metric", imp.getMetric());
      imp.setMetric(null);
    }
    if (nonNull(imp.getClickbrowser())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put("clickbrowser", imp.getClickbrowser());
      imp.setClickbrowser(null);
    }
    if (nonNull(imp.getExp())) {
      if (isNull(imp.getExt())) {
        imp.setExt(new HashMap<>());
      }
      imp.getExt().put("exp", imp.getExp());
      imp.setExp(null);
    }
  }
}