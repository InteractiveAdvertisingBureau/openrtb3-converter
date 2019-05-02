/*
 * Copyright  2019 - present. IAB Tech Lab
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

import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.enums.AdType;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Native;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.io.IOException;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.CollectionUtils.copyCollection;
import static net.media.utils.CollectionUtils.copyObject;
import static net.media.utils.ExtUtils.putToExt;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class DisplayToBidConverter implements Converter<Display, Bid> {

  public Bid map(Display source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    Bid bid = new Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  public void enhance(Display source, Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    Converter<Native, NativeResponse> nativeBidConverter =
        converterProvider.fetch(new Conversion<>(Native.class, NativeResponse.class));

    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }

    ObjectMapper mapper = JacksonObjectMapperUtils.getMapper();
    target.setH(source.getH());
    target.setW(source.getW());
    target.setWratio(source.getWratio());
    target.setHratio(source.getHratio());
    if (nonNull(source.getApi()) && source.getApi().size() > 0) {
      target.setApi(source.getApi().iterator().next());
    }
    if (isNull(target.getExt())) {
      target.setExt(new HashMap<>());
    }
    putToExt(source::getCtype, target.getExt(), CommonConstants.CTYPE, target::setExt);
    putToExt(source::getPriv, target.getExt(), CommonConstants.PRIV, target::setExt);
    putToExt(source::getCurl, target.getExt(), CommonConstants.CURL, target::setExt);
    putToExt(() -> copyCollection(source.getEvent(), config), target.getExt(), CommonConstants.EVENT, target::setExt);
    putToExt(source::getMime, target.getExt(), CommonConstants.MIME, target::setExt);

    if (isEmpty(target.getNurl())) {
      target.setNurl(source.getCurl());
    }
    if (nonNull(source.getExt())) {
      target.getExt().putAll(source.getExt());
    }

    if (config.getAdType(target.getImpid()) == AdType.NATIVE) {
      if (nonNull(source.get_native())) {
        NativeResponse _native =
            nativeBidConverter.map(source.get_native(), config, converterProvider);
        if (config.getNativeResponseAsString()) {
          try {
            target.setAdm(JacksonObjectMapperUtils.getMapper().writeValueAsString(_native));
          } catch (IOException e) {
            throw new OpenRtbConverterException(
                "error occured while  serializing native response", e);
          }
        } else target.setAdm(_native);
      } else if (nonNull(source.getAdm())) {
        try {
          Native native3;
          if (source.getAdm() instanceof String) {
            native3 = mapper.readValue((String) source.getAdm(), Native.class);
          } else {
            native3 = mapper.convertValue(source.getAdm(), Native.class);
          }
          NativeResponse _native = nativeBidConverter.map(native3, config, converterProvider);
          target.setAdm(_native);
        } catch (IOException e) {
          throw new OpenRtbConverterException("error occured while mapping native object", e);
        }
      }
    } else {
      putToExt(() -> copyObject(source.getBanner(), Banner.class, config), target.getExt(), CommonConstants.BANNER, target::setExt);
      if (nonNull(source.getAdm())) {
        target.setAdm(source.getAdm());
      }
    }
  }
}
