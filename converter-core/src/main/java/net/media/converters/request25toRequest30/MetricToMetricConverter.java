package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.Metric;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 03/01/19.
 */
public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb3.Metric> {

  @Override
  public net.media.openrtb3.Metric map(Metric metric, Config config, Provider<Conversion, Converter> converterProvider) {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb3.Metric metric1 = new net.media.openrtb3.Metric();
    enhance(metric, metric1, config, converterProvider);
    return metric1;
  }

  @Override
  public void enhance(Metric metric, net.media.openrtb3.Metric metric1, Config config, Provider<Conversion, Converter> converterProvider) {
    if (metric != null && metric1 != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      metric1.setExt(Utils.copyMap(metric.getExt(), config));
    }
  }
}
