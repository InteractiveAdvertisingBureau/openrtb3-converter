/*
 * Copyright © 2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Producer;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

public class ProducerToProducerConverter
    implements Converter<Producer, net.media.openrtb3.Producer> {
  @Override
  public net.media.openrtb3.Producer map(Producer source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Producer producer1 = new net.media.openrtb3.Producer();

    enhance(source, producer1, config, converterProvider);

    return producer1;
  }

  @Override
  public void enhance(
      Producer source,
      net.media.openrtb3.Producer target,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    target.setId(source.getId());
    target.setName(source.getName());
    target.setDomain(source.getDomain());
    target.setCat(Utils.copyCollection(source.getCat(), config));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(Utils.copyMap(map, config));
    }
    if (source.getExt() == null) return;
    try {
      if (source.getExt().containsKey("cattax")) {
        target.setCattax((Integer) source.getExt().get("cattax"));
      } else {
        target.setCattax(DEFAULT_CATTAX_TWODOTX);
      }
      target.getExt().remove("cattax");
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Producer", e);
    }
  }
}
