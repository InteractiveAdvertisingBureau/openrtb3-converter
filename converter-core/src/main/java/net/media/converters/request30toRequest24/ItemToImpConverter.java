package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Deal;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Metric;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ItemToImpConverter extends net.media.converters
  .request30toRequest25.ItemToImpConverter {

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
  }
}