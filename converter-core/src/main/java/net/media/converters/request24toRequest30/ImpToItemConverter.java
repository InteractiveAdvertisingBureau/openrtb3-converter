package net.media.converters.request24toRequest30;

import com.fasterxml.jackson.databind.JavaType;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Deal;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Metric;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ImpToItemConverter extends net.media.converters
  .request25toRequest30.ImpToItemConverter {

  private static final JavaType javaTypeForMetricCollection = Utils.getMapper().getTypeFactory()
    .constructCollectionType(Collection.class, Metric.class);

  public void enhance(Imp imp, Item item, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (imp == null || item == null) {
      return;
    }
    if (nonNull(imp.getExt())) {
      if (imp.getExt().containsKey("metric")) {
        try {
          imp.setMetric(Utils.getMapper().convertValue(imp.getExt().get("metric"),
            javaTypeForMetricCollection));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting metric from imp.ext.metric",
            e);
        }
        imp.getExt().remove("metric");
      }
    }
    super.enhance(imp, item, config, converterProvider);
  }
}
