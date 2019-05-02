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

package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.enums.AdType;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.putToExt;

public class AdToBidConverter implements Converter<Ad, Bid> {

  public Bid map(Ad source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    Bid bid = new Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  public void enhance(Ad source, Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }
    Converter<Display, Bid> displayBidConverter =
        converterProvider.fetch(new Conversion<>(Display.class, Bid.class));
    Converter<Video, Bid> videoBidConverter =
        converterProvider.fetch(new Conversion<>(Video.class, Bid.class));
    Converter<Audio, Bid> audioBidConverter =
        converterProvider.fetch(new Conversion<>(Audio.class, Bid.class));

    target.setCrid(source.getId());

    target.setAdomain(CollectionUtils.copyCollection(source.getAdomain(), config));
    if (nonNull(source.getBundle()) && source.getBundle().size() > 0) {
      target.setBundle(source.getBundle().iterator().next());
    }
    target.setIurl(source.getIurl());
    target.setCat(CollectionUtils.copyCollection(source.getCat(), config));
    target.setAttr(CollectionUtils.copyCollection(source.getAttr(), config));
    target.setLanguage(source.getLang());

    if (isNull(target.getExt())) {
      target.setExt(new HashMap<>());
    }
    if (nonNull(source.getExt())) {
      target.getExt().putAll(source.getExt());
    }
    putToExt(source::getSecure, target.getExt(), CommonConstants.SECURE, target::setExt);
    putToExt(source::getInit, target.getExt(), CommonConstants.INIT, target::setExt);
    putToExt(source::getLastmod, target.getExt(), CommonConstants.LASTMOD, target::setExt);
    putToExt(source::getCattax, target.getExt(), CommonConstants.CATTAX, target::setExt);
    putToExt(source::getAudit, target.getExt(), CommonConstants.AUDIT, target::setExt);
    target.setQagmediarating(source.getMrating());
    AdType adType = config.getAdType(target.getImpid());
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayBidConverter.enhance(source.getDisplay(), target, config, converterProvider);
        break;
      case VIDEO:
        videoBidConverter.enhance(source.getVideo(), target, config, converterProvider);
        break;
      case AUDIO:
        audioBidConverter.enhance(source.getAudio(), target, config, converterProvider);
        break;
    }
  }
}
