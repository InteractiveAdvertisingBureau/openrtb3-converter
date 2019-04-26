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

package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Content;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Site;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;
import java.util.Map;

import static net.media.utils.ExtUtils.putToExt;

public class SiteToSiteConverter implements Converter<Site, net.media.openrtb25.request.Site> {

  @Override
  public net.media.openrtb25.request.Site map(
      Site source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb25.request.Site site1 = new net.media.openrtb25.request.Site();

    enhance(source, site1, config, converterProvider);

    return site1;
  }

  @Override
  public void enhance(
      Site source,
      net.media.openrtb25.request.Site target,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter =
        converterProvider.fetch(
            new Conversion<>(Publisher.class, net.media.openrtb25.request.Publisher.class));
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter =
        converterProvider.fetch(
            new Conversion<>(Content.class, net.media.openrtb25.request.Content.class));
    target.setSectioncat(CollectionUtils.copyCollection(source.getSectcat(), config));
    target.setPrivacypolicy(source.getPrivpolicy());
    target.setPublisher(
        publisherPublisherConverter.map(source.getPub(), config, converterProvider));
    target.setId(source.getId());
    target.setName(source.getName());
    target.setDomain(source.getDomain());
    if (source.getCat() != null) {
      target.setCat(CollectionUtils.copyCollection(source.getCat(), config));
    }
    target.setPagecat(CollectionUtils.copyCollection(source.getPagecat(), config));
    target.setPage(source.getPage());
    target.setRef(source.getRef());
    target.setSearch(source.getSearch());
    target.setMobile(source.getMobile());
    target.setContent(contentContentConverter.map(source.getContent(), config, converterProvider));
    target.setKeywords(source.getKeywords());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    putToExt(source::getCattax, target.getExt(), CommonConstants.CATTAX, target::setExt);
    putToExt(source::getAmp, target.getExt(), CommonConstants.AMP, target::setExt);
  }
}
