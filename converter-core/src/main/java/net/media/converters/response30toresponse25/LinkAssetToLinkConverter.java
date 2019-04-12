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

package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb3.LinkAsset;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.isNull;

public class LinkAssetToLinkConverter implements Converter<LinkAsset, Link> {

  public Link map(LinkAsset source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) return null;
    Link link = new Link();
    enhance(source, link, config, converterProvider);
    return link;
  }

  public void enhance(LinkAsset source, Link target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(target) || isNull(config)) return;

    target.setUrl(source.getUrl());
    target.setFallback(source.getUrlfb());
    target.setClicktrackers(source.getTrkr());
    target.setExt(Utils.copyMap(source.getExt(), config));
  }
}
