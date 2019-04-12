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

package net.media.converters.response25toresponse30;

import com.fasterxml.jackson.databind.JavaType;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.Native;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.io.IOException;
import java.util.*;

import static java.util.Objects.nonNull;

/** @author shiva.b */
public class BidToDisplayConverter implements Converter<Bid, Display> {

  private static final JavaType javaTypeForEventCollection = Utils.getMapper().getTypeFactory()
    .constructCollectionType(Collection.class, Event.class);

  @Override
  public Display map(Bid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
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
    if(nonNull(source.getApi())) {
      target.setApi(new ArrayList<>(Collections.singletonList(source.getApi())));
    }
//    if (nonNull(source.getExt())) {
//      if (source.getExt().containsKey("curl")) {
//        target.setCurl((String) source.getExt().get("curl"));
//        source.getExt().remove("curl");
//      }
//    }
    Converter<NativeResponse, Native> converter =
        converterProvider.fetch(new Conversion<>(NativeResponse.class, Native.class));
    if (config.getAdType(source.getId()) == AdType.NATIVE) {
      if (source.getAdm() instanceof String) {
        try {
          NativeResponse nativeResponse =
              Utils.getMapper().readValue((String) source.getAdm(), NativeResponse.class);
          Native _native = converter.map(nativeResponse, config, converterProvider);
          if(config.getNativeResponseAsString())
            target.setAdm(_native);
          else
            target.set_native(_native);
        } catch (IOException e) {
          throw new OpenRtbConverterException(
              "error while deserializing native response object", e);
        }
      } else {
        try {
          Native _native =
		          converter.map(Utils.getMapper().convertValue(source.getAdm(), NativeResponse.class), config, converterProvider);
          if(config.getNativeResponseAsString())
            target.setAdm(_native);
          else
            target.set_native(_native);
        }
        catch (Exception e) {
          throw new OpenRtbConverterException("error while casting adm to native response", e);
        }
      }
    } else if (config.getAdType(source.getId()) == AdType.BANNER) {
      target.setAdm(source.getAdm());
    }
    if (nonNull(source.getExt())) {
      try {
        Map<String, Object> ext = source.getExt();
        target.setCtype((Integer) ext.get("ctype"));
        source.getExt().remove("ctype");
        target.setPriv((String) ext.get("priv"));
        source.getExt().remove("priv");
        target.setMime((String) ext.get("mime"));
        source.getExt().remove("mime");

        if (config.getAdType(source.getId()) == AdType.BANNER) {
          if (ext.containsKey("banner")) {
            target.setBanner(Utils.getMapper().convertValue(ext.get("banner"), Banner.class));
            source.getExt().remove("banner");
          }
        } else if (config.getAdType(source.getId()) == AdType.NATIVE) {
          if (ext.containsKey("native")) {
            Native _native = null;
            try {
              _native = Utils.getMapper().convertValue(ext.get("native"), Native.class);
              target.set_native(_native);
            } catch (Exception e) {
              throw new OpenRtbConverterException(
                  "Error in setting displayConverter.native from " + "bid.ext.native", e);
            }
            source.getExt().remove("native");
          }
        }
        try {
          target.setEvent(Utils.getMapper().convertValue(ext.get("event"),
            javaTypeForEventCollection));
        } catch (IllegalArgumentException e) {
          throw new OpenRtbConverterException("error while setting display.event from bid.ext" +
            ".event", e);
        }
        source.getExt().remove("event");
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while casting contents of bid.ext", e);
      }
    }
  }
}
