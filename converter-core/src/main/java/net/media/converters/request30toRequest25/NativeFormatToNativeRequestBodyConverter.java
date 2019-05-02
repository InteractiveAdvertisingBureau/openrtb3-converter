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
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class NativeFormatToNativeRequestBodyConverter
    implements Converter<NativeFormat, NativeRequestBody> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CONTEXTSUBTYPE);
    extraFieldsInExt.add(CommonConstants.ADUNIT);
    extraFieldsInExt.add(CommonConstants.LAYOUT);
    extraFieldsInExt.add(CommonConstants.VER);
  }

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
    fetchFromExt(
      nativeRequestBody::setContextsubtype,
      nativeFormat.getExt(),
      CommonConstants.CONTEXTSUBTYPE,
      "error while mapping ext for DisplayPlacement");
    fetchFromExt(
      nativeRequestBody::setAdunit,
      nativeFormat.getExt(),
      CommonConstants.ADUNIT,
      "error while mapping ext for DisplayPlacement");
    fetchFromExt(
      nativeRequestBody::setLayout,
      nativeFormat.getExt(),
      CommonConstants.LAYOUT,
      "error while mapping ext for DisplayPlacement");
    fetchFromExt(
      nativeRequestBody::setVer,
      nativeFormat.getExt(),
      CommonConstants.VER,
      "error while mapping ext for DisplayPlacement");
    nativeRequestBody.setExt(new HashMap<>(nativeFormat.getExt()));
    Converter<AssetFormat, Asset> assetFormatAssetConverter =
        converterProvider.fetch(new Conversion<>(AssetFormat.class, Asset.class));
    nativeRequestBody.setAssets(
        CollectionUtils.convert(
            nativeFormat.getAsset(), assetFormatAssetConverter, config, converterProvider));
    removeFromExt(nativeRequestBody.getExt(), extraFieldsInExt);
  }
}
