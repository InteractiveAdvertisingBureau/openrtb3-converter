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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.enums.AdType;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Display;
import net.media.openrtb3.Native;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

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
    target.getExt().put(CommonConstants.CTYPE, source.getCtype());

    target.getExt().put(CommonConstants.PRIV, source.getPriv());
    target.getExt().put(CommonConstants.CURL, source.getCurl());
    if (isEmpty(target.getNurl())) {
      target.setNurl(source.getCurl());
    }
    target.getExt().put(CommonConstants.EVENT, source.getEvent());
    target.getExt().put(CommonConstants.MIME, source.getMime());
    if (nonNull(source.getExt())) {
      target.getExt().putAll(source.getExt());
    }

    if (config.getAdType(target.getId()) == AdType.NATIVE) {
      target.getExt().put(CommonConstants.NATIVE, source.get_native());
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
        } else {
          target.setAdm(_native);
        }
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
      target.getExt().put(CommonConstants.BANNER, source.getBanner());
      if (nonNull(source.getAdm())) {
        target.setAdm(source.getAdm());
      }
    }
  }
}
