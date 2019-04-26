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

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.nativeresponse.*;
import net.media.openrtb3.*;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.putToExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class Asset30ToAsset25Converter implements Converter<Asset, AssetResponse> {

  static List<String> extraFieldsInNativeDataExt = new ArrayList<>();

  static {
    extraFieldsInNativeDataExt.add(CommonConstants.LABEL);
  }

  public AssetResponse map(Asset source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    AssetResponse assetResponse = new AssetResponse();
    enhance(source, assetResponse, config, converterProvider);
    return assetResponse;
  }

  public void enhance(Asset source, AssetResponse target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {

    Converter<LinkAsset, Link> linkAssetLinkConverter =
        converterProvider.fetch(new Conversion<>(LinkAsset.class, Link.class));

    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }

    target.setData(dataTonativeData(source.getData()));
    target.setId(source.getId());
    target.setRequired(source.getReq());
    target.setImg(imageAssetToNativeImage(source.getImage()));
    target.setVideo(videoAssetToNativeVideo(source.getVideo()));
    target.setTitle(tittleAssetToNativeTittle(source.getTitle()));
    target.setLink(linkAssetLinkConverter.map(source.getLink(), config, converterProvider));
    if(nonNull(source.getExt())) {
      target.setExt(new HashMap<>(source.getExt()));
    }
  }

  private NativeData dataTonativeData(DataAsset data) throws OpenRtbConverterException {
    if (isNull(data)) {
      return null;
    }
    NativeData nativeData = new NativeData();
    if(nonNull(data.getExt())){
      nativeData.setExt(new HashMap<>(data.getExt()));
    }
    fetchFromExt(nativeData::setLabel, data.getExt(), CommonConstants.LABEL, "error while mapping label from data.ext");
    if (isNull(nativeData.getExt())) {
      nativeData.setExt(new HashMap<>());
    }
    putToExt(data::getType, nativeData.getExt(), CommonConstants.TYPE, nativeData::setExt);
    putToExt(data::getLen, nativeData.getExt(), CommonConstants.LEN, nativeData::setExt);
    if (nonNull(data.getValue()) && data.getValue().size() > 0) {
      nativeData.setValue(data.getValue().iterator().next());
    }
    removeFromExt(nativeData.getExt(), extraFieldsInNativeDataExt);
    return nativeData;
  }

  private NativeImage imageAssetToNativeImage(ImageAsset imageAsset) {
    if (isNull(imageAsset)) {
      return null;
    }
    NativeImage nativeImage = new NativeImage();
    if(nonNull(imageAsset.getExt())) {
      nativeImage.setExt(new HashMap<>(imageAsset.getExt()));
    }
    nativeImage.setH(imageAsset.getH());
    nativeImage.setW(imageAsset.getW());
    nativeImage.setUrl(imageAsset.getUrl());
    if (isNull(nativeImage.getExt())) {
      nativeImage.setExt(new HashMap<>());
    }
    putToExt(imageAsset::getType, nativeImage.getExt(), CommonConstants.TYPE, nativeImage::setExt);
    return nativeImage;
  }

  private NativeVideo videoAssetToNativeVideo(VideoAsset videoAsset) {
    if (isNull(videoAsset)) {
      return null;
    }
    NativeVideo nativeVideo = new NativeVideo();
    if(nonNull(videoAsset.getExt())) {
      nativeVideo.setExt(new HashMap<>(videoAsset.getExt()));
    }
    nativeVideo.setVasttag(videoAsset.getAdm());
    if (isNull(nativeVideo.getExt())) {
      nativeVideo.setExt(new HashMap<>());
    }
    putToExt(videoAsset::getCurl, nativeVideo.getExt(), CommonConstants.CURL, nativeVideo::setExt);
    return nativeVideo;
  }

  private NativeTitle tittleAssetToNativeTittle(TitleAsset titleAsset) {
    if (isNull(titleAsset)) {
      return null;
    }
    NativeTitle nativeTitle = new NativeTitle();
    if(nonNull(titleAsset.getExt())) {
      nativeTitle.setExt(new HashMap<>(titleAsset.getExt()));
    }
    nativeTitle.setText(titleAsset.getText());
    if (isNull(nativeTitle.getExt())) {
      nativeTitle.setExt(new HashMap<>());
    }
    putToExt(titleAsset::getLen, nativeTitle.getExt(), CommonConstants.LEN, nativeTitle::setExt);
    return nativeTitle;
  }
}
