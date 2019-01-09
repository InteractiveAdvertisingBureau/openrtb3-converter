package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Metric;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */
public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb3.Metric> {

  @Override
  public net.media.openrtb3.Metric map(Metric metric, Config config) {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb3.Metric metric1 = new net.media.openrtb3.Metric();
    inhance(metric, metric1, config);
    return metric1;
  }

  @Override
  public void inhance(Metric metric, net.media.openrtb3.Metric metric1, Config config) {
    if (metric != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      metric1.setExt(Utils.copyMap(metric.getExt(), config));
    }
  }
}
