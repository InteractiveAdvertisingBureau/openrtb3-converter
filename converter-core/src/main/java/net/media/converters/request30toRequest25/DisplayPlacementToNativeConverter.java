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

import com.fasterxml.jackson.core.JsonProcessingException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequest;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;
import net.media.utils.JacksonObjectMapper;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DisplayPlacementToNativeConverter implements Converter<DisplayPlacement, Native> {

  @Override
  public Native map(DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (displayPlacement == null) {
      return null;
    }
    Native nat = new Native();
    enhance(displayPlacement, nat, config, converterProvider);
    if (isNull(nat.getNativeRequestBody())) {
      return null;
    }
    return nat;
  }

  @Override
  public void enhance(
      DisplayPlacement displayPlacement, Native nat, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(displayPlacement) || isNull(nat)) {
      return;
    }
    Converter<NativeFormat, NativeRequestBody> nativeFormatNativeRequestBodyConverter =
        converterProvider.fetch(new Conversion<>(NativeFormat.class, NativeRequestBody.class));
    NativeRequestBody nativeRequestBody =
        nativeFormatNativeRequestBodyConverter.map(
            displayPlacement.getNativefmt(), config, converterProvider);
    if (isNull(nativeRequestBody)) {
      return;
    }
    NativeRequest nativeRequest = new NativeRequest();
    nativeRequest.setNativeRequestBody(nativeRequestBody);
    if (nonNull(nativeRequest.getNativeRequestBody())) {
      nativeRequest.getNativeRequestBody().setContext(displayPlacement.getContext());
      nativeRequest.getNativeRequestBody().setPlcmttype(displayPlacement.getPtype());
    }
    nat.setApi(Utils.copyCollection(displayPlacement.getApi(), config));
    if (nonNull(displayPlacement.getExt())) {
      if (isNull(nat.getExt())) {
        nat.setExt(new HashMap<>());
      }
      nat.getExt().putAll(displayPlacement.getExt());
      try {
        if (displayPlacement.getNativefmt().getExt() != null
            && displayPlacement.getNativefmt().getExt().containsKey("ver")) {
          nat.setVer((String) displayPlacement.getNativefmt().getExt().get("ver"));
          displayPlacement.getNativefmt().getExt().remove("ver");
        }
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
      }
    }
    if (config.getNativeRequestAsString()) {
      try {
        nat.setRequest(JacksonObjectMapper.getMapper().writeValueAsString(nativeRequest));
      } catch (JsonProcessingException e) {
        throw new OpenRtbConverterException(e);
      }
    } else {
      nat.setRequest(nativeRequest);
    }
    try {
      if (displayPlacement.getPriv() != null) {
        if (nat.getExt() == null) nat.setExt(new HashMap<>());
        nat.getExt().put("priv", displayPlacement.getPriv());
      }
      if (displayPlacement.getCtype() != null) {
        if (nat.getExt() == null) nat.setExt(new HashMap<>());
        nat.getExt().put("ctype", displayPlacement.getCtype());
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
    }
    //    nat.setExt(Utils.copyMap(displayPlacement.getExt(), config));
  }
}
