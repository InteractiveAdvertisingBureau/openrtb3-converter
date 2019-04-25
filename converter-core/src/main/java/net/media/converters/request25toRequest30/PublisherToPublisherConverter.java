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
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Publisher;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class PublisherToPublisherConverter
    implements Converter<Publisher, net.media.openrtb3.Publisher> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CATTAX);
  }

  @Override
  public net.media.openrtb3.Publisher map(
      Publisher source, Config config, Provider converterProvider) throws OpenRtbConverterException {
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
      Provider converterProvider) throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setId(source.getId());
    target.setName(source.getName());
    target.setDomain(source.getDomain());
    target.setCat(CollectionUtils.copyCollection(source.getCat(), config));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    target.setCattax(DEFAULT_CATTAX_TWODOTX);
    fetchFromExt(target::setCattax, source.getExt(), CommonConstants.CATTAX, "error while mapping cattax from publisher");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
