package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Metric;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.isNull;

public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb25.request.Metric> {
  @Override
  public net.media.openrtb25.request.Metric map(Metric metric, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb25.request.Metric metric1 = new net.media.openrtb25.request.Metric();
    enhance(metric, metric1, config, converterProvider);
    return metric1;
  }

  @Override
  public void enhance(Metric metric, net.media.openrtb25.request.Metric metric1, Config config,
                      Provider converterProvider)
    throws OpenRtbConverterException {
    if (metric != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      metric1.setExt(Utils.copyMap(metric.getExt(), config));
    }
  }
}
