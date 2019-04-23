/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Metric;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/01/19. */
public class MetricToMetricConverter implements Converter<Metric, net.media.openrtb3.Metric> {

  @Override
  public net.media.openrtb3.Metric map(Metric metric, Config config, Provider converterProvider) {
    if (isNull(metric)) {
      return null;
    }
    net.media.openrtb3.Metric metric1 = new net.media.openrtb3.Metric();
    enhance(metric, metric1, config, converterProvider);
    return metric1;
  }

  @Override
  public void enhance(
      Metric metric, net.media.openrtb3.Metric metric1, Config config, Provider converterProvider) {
    if (metric != null && metric1 != null) {
      metric1.setType(metric.getType());
      metric1.setVendor(metric.getVendor());
      metric1.setValue(metric.getValue());
      if(nonNull(metric.getExt()))
        metric1.setExt(new HashMap<>(metric.getExt()));
    }
  }
}
