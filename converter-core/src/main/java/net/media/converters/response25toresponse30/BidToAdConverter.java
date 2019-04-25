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
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

/** @author shiva.b */
public class BidToAdConverter implements Converter<Bid, Ad> {

  @Override
  public Ad map(Bid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Ad ad = new Ad();
    enhance(source, ad, config, converterProvider);
    return ad;
  }

  @Override
  public void enhance(Bid source, Ad target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(target)) {
      return;
    }
    target.setId(source.getCrid());
    target.setAdomain(CollectionUtils.copyCollection(source.getAdomain(), config));
    if (nonNull(source.getBundle())) {
      List<String> bundle = new ArrayList<>();
      bundle.add(source.getBundle());
      target.setBundle(bundle);
    }
    target.setIurl(source.getIurl());
    target.setCat(CollectionUtils.copyCollection(source.getCat(), config));
    target.setLang(source.getLanguage());
    target.setAttr(CollectionUtils.copyCollection(source.getAttr(), config));
    target.setMrating(source.getQagmediarating());
    try {
      if (nonNull(source.getExt())) {
        Map<String, Object> ext = source.getExt();
        if (ext.containsKey(CommonConstants.SECURE)) {
          target.setSecure((Integer) ext.get(CommonConstants.SECURE));
        }
        if (ext.containsKey(CommonConstants.INIT)) {
          target.setInit((Integer) ext.get(CommonConstants.INIT));
        }
        if (ext.containsKey(CommonConstants.LASTMOD)) {
          target.setLastmod((Integer) ext.get(CommonConstants.LASTMOD));
        }
        if (ext.containsKey(CommonConstants.CATTAX)) {
          target.setCattax((Integer) ext.get(CommonConstants.CATTAX));
        } else {
          target.setCattax(DEFAULT_CATTAX_TWODOTX);
        }
        if (source.getExt().containsKey(CommonConstants.AUDIT)) {
          target.setAudit(JacksonObjectMapperUtils.getMapper().convertValue(source.getExt().get(CommonConstants.AUDIT), Audit.class));
        }
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext in bid", e);
    }

    switch (config.getAdType(source.getId())) {
      case BANNER:
      case NATIVE:
        Converter<Bid, Display> bidDisplayConverter =
            converterProvider.fetch(new Conversion<>(Bid.class, Display.class));
        target.setDisplay(bidDisplayConverter.map(source, config, converterProvider));
        break;
      case VIDEO:
        Converter<Bid, Video> bidVideoConverter =
            converterProvider.fetch(new Conversion<>(Bid.class, Video.class));
        target.setVideo(bidVideoConverter.map(source, config, converterProvider));
        break;
      case AUDIO:
        Converter<Bid, Audio> bidAudioConverter =
            converterProvider.fetch(new Conversion<>(Bid.class, Audio.class));
        target.setAudio(bidAudioConverter.map(source, config, converterProvider));
        break;
    }
  }
}
