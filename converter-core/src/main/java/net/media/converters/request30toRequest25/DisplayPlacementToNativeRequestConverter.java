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

public class DisplayPlacementToNativeRequestConverter implements Converter<DisplayPlacement, NativeRequest> {

  @Override
  public NativeRequest map(DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (displayPlacement == null) {
      return null;
    }
    NativeRequest nativeRequest = new NativeRequest();
    enhance(displayPlacement, nativeRequest, config, converterProvider);
    if (isNull(nativeRequest.getNativeRequestBody())) {
      return null;
    }
    return nativeRequest;
  }

  @Override
  public void enhance(DisplayPlacement displayPlacement, NativeRequest nat, Config config, Provider converterProvider) throws OpenRtbConverterException {
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
    nat.setNativeRequestBody(nativeRequestBody);
    if (nonNull(nat.getNativeRequestBody())) {
      nat.getNativeRequestBody().setContext(displayPlacement.getContext());
      nat.getNativeRequestBody().setPlcmttype(displayPlacement.getPtype());
    }

    //    nat.setExt(Utils.copyMap(displayPlacement.getExt(), config));
  }
}
