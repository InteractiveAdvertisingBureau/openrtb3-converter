package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Metric;
import net.media.utils.Utils;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 04/01/19.
 */
public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb25.request
  .Metric> {
  @Override
  public net.media.openrtb25.request.Metric map(Metric metric, Config config) {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb25.request.Metric metric1 = new net.media.openrtb25.request.Metric();
    inhance(metric, metric1, config);
    return metric1;
  }

  @Override
  public void inhance(Metric metric, net.media.openrtb25.request.Metric metric1, Config config) {
    if (metric != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      metric1.setExt(Utils.copyMap(metric.getExt(), config));
    }
  }
}
