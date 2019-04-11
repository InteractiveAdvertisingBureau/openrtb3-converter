/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AdToBidConverter implements Converter<Ad,Bid>{

  public Bid map(Ad source, Config config, Provider converterProvider) throws
    OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    enhance(source,bid,config, converterProvider);
    return bid;
  }

  public void enhance(Ad source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    Converter<Display, Bid>displayBidConverter = converterProvider.fetch(new Conversion<>(Display.class,
      Bid.class));
    Converter<Video, Bid> videoBidConverter = converterProvider.fetch(new Conversion<>(Video.class,
      Bid.class));
    Converter<Audio, Bid> audioBidConverter = converterProvider.fetch(new Conversion<>(Audio.class,
      Bid.class));
    Converter<Audit, Bid> auditBidConverter = converterProvider.fetch(new Conversion<>(Audit.class,
      Bid.class));

    target.setCrid(source.getId());

    target.setAdomain(Utils.copyCollection(source.getAdomain(),config));
    if(nonNull(source.getBundle()) && source.getBundle().size()>0)
      target.setBundle(source.getBundle().iterator().next());
    target.setIurl( source.getIurl() );
    target.setCat(Utils.copyCollection(source.getCat(),config));
    target.setAttr(Utils.copyCollection(source.getAttr(),config));
    target.setLanguage(source.getLang());

    if(isNull(target.getExt()))
      target.setExt(new HashMap<>());
    if (nonNull(source.getExt())) {
      target.getExt().putAll(source.getExt());
    }
    if (nonNull(source.getSecure())) {
      target.getExt().put("secure", source.getSecure());
    }
    if (nonNull(source.getInit())) {
      target.getExt().put("init", source.getInit());
    }
    if (nonNull(source.getLastmod())) {
      target.getExt().put("lastmod", source.getLastmod());
    }
    if (nonNull(source.getCattax())) {
      target.getExt().put("cattax", source.getCattax());
    }
    target.setQagmediarating(source.getMrating());
    AdType adType = config.getAdType(target.getId());
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayBidConverter.enhance(source.getDisplay(),target,config, converterProvider);
        break;
      case VIDEO:
        videoBidConverter.enhance(source.getVideo(),target,config, converterProvider);
        break;
      case AUDIO:
        audioBidConverter.enhance(source.getAudio(),target,config, converterProvider);
        break;
      case AUDIT:
        auditBidConverter.enhance(source.getAudit(),target,config, converterProvider);
        break;
    }

  }
}
