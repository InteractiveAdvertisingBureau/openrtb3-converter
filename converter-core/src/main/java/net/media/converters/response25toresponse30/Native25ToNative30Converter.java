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
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Asset;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Native;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.putToExt;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/** @author shiva.b */
public class Native25ToNative30Converter implements Converter<NativeResponse, Native> {

  @Override
  public Native map(NativeResponse source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Native _native = new Native();
    enhance(source, _native, config, converterProvider);
    return _native;
  }

  @Override
  public void enhance(
      NativeResponse source, Native target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null || source.getNativeResponseBody() == null) {
      return;
    }
    if(nonNull(source.getNativeResponseBody().getExt())) {
      target.setExt(new HashMap<>(source.getNativeResponseBody().getExt()));
    }
    if (isNull(target.getExt())) {
      target.setExt(new HashMap<>());
    }
    target.setExt(putToExt(source.getNativeResponseBody()::getJstracker, target.getExt(), CommonConstants.JS_TRACKER));
    target.setExt(putToExt(source.getNativeResponseBody()::getImptrackers, target.getExt(), CommonConstants.IMP_TRACKERS));

    Converter<Link, LinkAsset> linkLinkAssetConverter =
        converterProvider.fetch(new Conversion<>(Link.class, LinkAsset.class));
    Converter<AssetResponse, Asset> assetResponseAssetConverter =
        converterProvider.fetch(new Conversion<>(AssetResponse.class, Asset.class));
    LinkAsset linkAsset =
        linkLinkAssetConverter.map(
            source.getNativeResponseBody().getLink(), config, converterProvider);
    target.setLink(linkAsset);
    if (!isEmpty(source.getNativeResponseBody().getAsset())) {
      List<Asset> assetList = new ArrayList<>();
      for (AssetResponse assetResponse : source.getNativeResponseBody().getAsset()) {
        Asset asset = assetResponseAssetConverter.map(assetResponse, config, converterProvider);
        if (nonNull(asset)) {
          assetList.add(asset);
        }
      }
      target.setAsset(assetList);
    }
  }
}
