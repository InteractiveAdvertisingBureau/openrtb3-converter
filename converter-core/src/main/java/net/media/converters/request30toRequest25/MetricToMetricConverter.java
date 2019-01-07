package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Metric;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 04/01/19.
 */
public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb24.request
  .Metric> {
  @Override
  public net.media.openrtb24.request.Metric map(Metric metric, Config config) {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb24.request.Metric metric1 = new net.media.openrtb24.request.Metric();
    inhance(metric, metric1, config);
    return metric1;
  }

  @Override
  public void inhance(Metric metric, net.media.openrtb24.request.Metric metric1, Config config) {
    if (metric != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      Map<String, Object> extMap = metric.getExt();
      if (nonNull(extMap)) {
        metric1.setExt(new HashMap<>(extMap));
      }
    }
  }
}
