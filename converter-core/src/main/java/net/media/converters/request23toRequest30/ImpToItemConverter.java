package net.media.converters.request23toRequest30;

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

  public void enhance(Imp imp, Item item, Config config, Provider converterProvider) throws
    OpenRtbConverterException {
    if (imp == null || item == null) {
      return;
    }
    if (nonNull(imp.getExt())) {
      if (imp.getExt().containsKey("metric")) {
        try {
          imp.setMetric(Utils.getMapper().convertValue(imp.getExt().get("metric"),
            javaTypeForMetricCollection));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting metric from imp.ext.accuracy",
            e);
        }
        imp.getExt().remove("metric");
      }
      if (imp.getExt().containsKey("clickbrowser")) {
        try {
          imp.setClickbrowser((Integer) imp.getExt().get("clickbrowser"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting clickbrowser from imp.ext" +
            ".clickbrowser", e);
        }
        imp.getExt().remove("clickbrowser");
      }
      if (imp.getExt().containsKey("exp")) {
        try {
          imp.setExp((Integer) imp.getExt().get("exp"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting exp from imp.ext" +
            ".exp", e);
        }
        imp.getExt().remove("exp");
      }
    }
    super.enhance(imp, item, config, converterProvider);
  }
}
