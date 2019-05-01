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

package net.media.converters.response25toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb3.LinkAsset;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.nonNull;
import static net.media.utils.CollectionUtils.copyCollection;

/** @author shiva.b */
public class LinkToLinkAssetConverter implements Converter<Link, LinkAsset> {

  @Override
  public LinkAsset map(Link source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    LinkAsset linkAsset = new LinkAsset();
    enhance(source, linkAsset, config, converterProvider);
    return linkAsset;
  }

  @Override
  public void enhance(Link source, LinkAsset target, Config config, Provider converterProvider) {
    if (source == null || target == null) {
      return;
    }
    target.setUrl(source.getUrl());
    target.setUrlfb(source.getFallback());
    target.setTrkr(copyCollection(source.getClicktrackers(), config));
    if (nonNull(source.getExt())) {
      target.setExt(new HashMap<>(source.getExt()));
    }
  }
}
