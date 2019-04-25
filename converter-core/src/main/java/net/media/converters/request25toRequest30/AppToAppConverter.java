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
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.App;
import net.media.openrtb25.request.Content;
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

public class AppToAppConverter implements Converter<App, net.media.openrtb3.App> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CATTAX);
    extraFieldsInExt.add(CommonConstants.STOREID);
  }

  @Override
  public net.media.openrtb3.App map(App source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.App app1 = new net.media.openrtb3.App();

    enhance(source, app1, config, converterProvider);

    return app1;
  }

  @Override
  public void enhance(
      App source, net.media.openrtb3.App target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setPrivpolicy(source.getPrivacypolicy());
    target.setSectcat(CollectionUtils.copyCollection(source.getSectioncat(), config));
    Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter =
        converterProvider.fetch(
            new Conversion<>(Publisher.class, net.media.openrtb3.Publisher.class));
    Converter<Content, net.media.openrtb3.Content> contentContentConverter =
        converterProvider.fetch(new Conversion<>(Content.class, net.media.openrtb3.Content.class));
    target.setPub(
        publisherPublisherConverter.map(source.getPublisher(), config, converterProvider));
    target.setId(source.getId());
    target.setName(source.getName());
    target.setContent(contentContentConverter.map(source.getContent(), config, converterProvider));
    target.setDomain(source.getDomain());
    target.setCat(CollectionUtils.copyCollection(source.getCat(), config));
    target.setPagecat(CollectionUtils.copyCollection(source.getPagecat(), config));
    target.setKeywords(source.getKeywords());
    target.setBundle(source.getBundle());
    target.setStoreurl(source.getStoreurl());
    target.setVer(source.getVer());
    target.setPaid(source.getPaid());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
      target.setExt(new HashMap<>(map));
    }
    target.setCattax(DEFAULT_CATTAX_TWODOTX);
    fetchFromExt(target::setCattax, source.getExt(), CommonConstants.CATTAX, "error while typecasting ext for app");
    fetchFromExt(target::setStoreid, source.getExt(), CommonConstants.STOREID, "error while typecasting ext for app");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
