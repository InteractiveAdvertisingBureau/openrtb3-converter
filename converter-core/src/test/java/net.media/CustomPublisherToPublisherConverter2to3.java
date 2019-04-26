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

package net.media;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Publisher;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

public class CustomPublisherToPublisherConverter2to3
  implements Converter<Publisher, net.media.openrtb3.Publisher> {
  @Override
  public net.media.openrtb3.Publisher map(
    Publisher source, Config config, Provider converterProvider) {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Publisher publisher1 = new net.media.openrtb3.Publisher();

    enhance(source, publisher1, config, converterProvider);

    return publisher1;
  }

  @Override
  public void enhance(
    Publisher source,
    net.media.openrtb3.Publisher target,
    Config config,
    Provider converterProvider) {
    if (source == null || target == null) return;
    target.setId("customConverterID");
    target.setName(source.getName());
    target.setDomain("myCustomDomain");
    target.setCat(null);
    target.setCattax(DEFAULT_CATTAX_TWODOTX);
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
      if (map.containsKey(CommonConstants.CATTAX)) {
        target.setCattax((Integer) map.get(CommonConstants.CATTAX));
      }
      target.getExt().remove(CommonConstants.CATTAX);
    }
  }
}
