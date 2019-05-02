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

package net.media.converters.response25toresponse30;

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.enums.AdType;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.Native;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;

/** @author shiva.b */
public class BidToDisplayConverter implements Converter<Bid, Display> {

  private static final JavaType javaTypeForEventCollection =
    JacksonObjectMapperUtils.getMapper()
      .getTypeFactory()
      .constructCollectionType(Collection.class, Event.class);

  @Override
  public Display map(Bid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Display display = new Display();
    enhance(source, display, config, converterProvider);
    return display;
  }

  @Override
  public void enhance(Bid source, Display target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setH(source.getH());
    target.setWratio(source.getWratio());
    target.setW(source.getW());
    target.setHratio(source.getHratio());
    target.setCurl(source.getNurl());
    if (nonNull(source.getApi())) {
      target.setApi(new ArrayList<>(Collections.singletonList(source.getApi())));
    }
    Converter<NativeResponse, Native> converter =
        converterProvider.fetch(new Conversion<>(NativeResponse.class, Native.class));
    if (config.getAdType(source.getImpid()) == AdType.NATIVE) {
      if (source.getAdm() instanceof String) {
        try {
          NativeResponse nativeResponse =
            JacksonObjectMapperUtils.getMapper()
              .readValue((String) source.getAdm(), NativeResponse.class);
          Native _native = converter.map(nativeResponse, config, converterProvider);
          if (config.getNativeResponseAsString()) target.setAdm(_native);
          else target.set_native(_native);
        } catch (IOException e) {
          throw new OpenRtbConverterException(
              "error while deserializing native response object", e);
        }
      } else {
        try {
          Native _native =
              converter.map(
                JacksonObjectMapperUtils.getMapper()
                  .convertValue(source.getAdm(), NativeResponse.class),
                  config,
                  converterProvider);
          if (config.getNativeResponseAsString()) target.setAdm(_native);
          else target.set_native(_native);
        } catch (Exception e) {
          throw new OpenRtbConverterException("error while casting adm to native response", e);
        }
      }
    } else if (config.getAdType(source.getImpid()) == AdType.BANNER) {
      target.setAdm(source.getAdm());
    }
    fetchFromExt(
      target::setCtype,
      source.getExt(),
      CommonConstants.CTYPE,
      "Error while mapping Ctype from Bid.ext");
    fetchFromExt(
      target::setPriv,
      source.getExt(),
      CommonConstants.PRIV,
      "Error while mapping priv from Bid.ext");
    fetchFromExt(
      target::setMime,
      source.getExt(),
      CommonConstants.MIME,
      "Error while mapping mime from Bid.ext");
    if (config.getAdType(source.getImpid()) == AdType.BANNER) {
      fetchFromExt(
        target::setBanner,
        source.getExt(),
        CommonConstants.BANNER,
        "error while mapping banner from bid.ext",
        Banner.class);
    } else if (config.getAdType(source.getImpid()) == AdType.NATIVE) {
      fetchFromExt(
        target::set_native,
        source.getExt(),
        CommonConstants.NATIVE,
        "error while mapping native from bid.ext",
        Native.class);
    }
    fetchFromExt(
      target::setEvent,
      source.getExt(),
      CommonConstants.EVENT,
      "error while mapping event from bid.ext",
      javaTypeForEventCollection);
  }
}
