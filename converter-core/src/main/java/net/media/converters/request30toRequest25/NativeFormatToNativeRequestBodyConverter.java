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

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NativeFormatToNativeRequestBodyConverter
    implements Converter<NativeFormat, NativeRequestBody> {

  @Override
  public NativeRequestBody map(NativeFormat nativeFormat, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (nativeFormat == null) {
      return null;
    }
    NativeRequestBody nativeRequestBody = new NativeRequestBody();
    enhance(nativeFormat, nativeRequestBody, config, converterProvider);
    return nativeRequestBody;
  }

  @Override
  public void enhance(
      NativeFormat nativeFormat,
      NativeRequestBody nativeRequestBody,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(nativeFormat) || isNull(nativeRequestBody)) {
      return;
    }
    if (nonNull(nativeFormat.getExt())) {
      if (nativeFormat.getExt().containsKey("contextsubtype")) {
        try {
          nativeRequestBody.setContextsubtype(
              (Integer) nativeFormat.getExt().get("contextsubtype"));
          nativeFormat.getExt().remove("contextsubtype");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (nativeFormat.getExt().containsKey("adunit")) {
        try {
          nativeRequestBody.setAdunit((Integer) nativeFormat.getExt().get("adunit"));
          nativeFormat.getExt().remove("adunit");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (nativeFormat.getExt().containsKey("layout")) {
        try {
          nativeRequestBody.setLayout((Integer) nativeFormat.getExt().get("layout"));
          nativeFormat.getExt().remove("layout");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (nativeFormat.getExt().containsKey("ver")) {
        try {
          nativeRequestBody.setVer((String) nativeFormat.getExt().get("ver"));
          nativeFormat.getExt().remove("ver");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
    }
    nativeRequestBody.setExt(MapUtils.copyMap(nativeFormat.getExt(), config));
    Converter<AssetFormat, Asset> assetFormatAssetConverter =
        converterProvider.fetch(new Conversion<>(AssetFormat.class, Asset.class));
    nativeRequestBody.setAssets(
        CollectionToCollectionConverter.convert(
            nativeFormat.getAsset(), assetFormatAssetConverter, config, converterProvider));
  }
}
