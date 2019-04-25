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
import net.media.openrtb25.response.nativeresponse.*;
import net.media.openrtb3.*;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.putToExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** @author shiva.b */
public class Asset25ToAsset30Converter implements Converter<AssetResponse, Asset> {

  static List<String> extraFieldsInDataExt = new ArrayList<>();
  static List<String> extraFieldsInImageAssetExt = new ArrayList<>();
  static List<String> extraFieldsInTitleAssetExt = new ArrayList<>();
  static List<String> extraFieldsInVideoAssetExt = new ArrayList<>();

  static {
    extraFieldsInDataExt.add(CommonConstants.TYPE);
    extraFieldsInDataExt.add(CommonConstants.LEN);
    extraFieldsInImageAssetExt.add(CommonConstants.TYPE);
    extraFieldsInTitleAssetExt.add(CommonConstants.LEN);
    extraFieldsInVideoAssetExt.add(CommonConstants.CURL);
  }

  @Override
  public Asset map(AssetResponse source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Asset asset = new Asset();
    enhance(source, asset, config, converterProvider);
    return asset;
  }

  @Override
  public void enhance(AssetResponse source, Asset target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setData(nativeDataToData(source.getData(), config));
    target.setId(source.getId());
    target.setReq(source.getRequired());
    target.setImage(nativeImageToImageAsset(source.getImg(), config));
    target.setTitle(nativeTitleToTitleAsset(source.getTitle(), config));
    target.setVideo(nativeVideoToVideoAsset(source.getVideo(), config));
    Converter<Link, LinkAsset> converter =
        converterProvider.fetch(new Conversion<>(Link.class, LinkAsset.class));
    target.setLink(converter.map(source.getLink(), config, converterProvider));
    if(nonNull(source.getExt())) {
      target.setExt(new HashMap<>(source.getExt()));
    }
  }

  private DataAsset nativeDataToData(NativeData nativeData, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeData)) {
      return null;
    }
    DataAsset dataAsset = new DataAsset();
    if(nonNull(nativeData.getExt())) {
      dataAsset.setExt(new HashMap<>(nativeData.getExt()));
    }
    List<String> value = new ArrayList<>();
    value.add(nativeData.getValue());
    dataAsset.setValue(value);
    fetchFromExt(dataAsset::setType, nativeData.getExt(), CommonConstants.TYPE, "Error while mapping type from nativedata");
    fetchFromExt(dataAsset::setLen, nativeData.getExt(), CommonConstants.LEN, "Error while mapping len from nativedata");
    if(isNull(dataAsset.getExt())) {
      dataAsset.setExt(new HashMap<>());
    }
    dataAsset.setExt(putToExt(nativeData::getLabel, dataAsset.getExt(), CommonConstants.LABEL));
    removeFromExt(dataAsset.getExt(), extraFieldsInDataExt);
    return dataAsset;
  }

  private ImageAsset nativeImageToImageAsset(NativeImage nativeImage, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeImage)) {
      return null;
    }
    ImageAsset imageAsset = new ImageAsset();
    imageAsset.setH(nativeImage.getH());
    imageAsset.setW(nativeImage.getW());
    imageAsset.setUrl(nativeImage.getUrl());
    if(nonNull(nativeImage.getExt())) {
      imageAsset.setExt(new HashMap<>(nativeImage.getExt()));
    }
    fetchFromExt(imageAsset::setType, nativeImage.getExt(), CommonConstants.TYPE, "Error while mapping type from nativeimage");
    removeFromExt(imageAsset.getExt(), extraFieldsInImageAssetExt);
    return imageAsset;
  }

  private TitleAsset nativeTitleToTitleAsset(NativeTitle nativeTitle, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeTitle)) {
      return null;
    }
    TitleAsset titleAsset = new TitleAsset();
    if(nonNull(nativeTitle.getExt())) {
      titleAsset.setExt(new HashMap<>(nativeTitle.getExt()));
    }
    titleAsset.setText(nativeTitle.getText());
    fetchFromExt(titleAsset::setLen, nativeTitle.getExt(), CommonConstants.LEN, "Error while mapping len from nativetitle");
    removeFromExt(titleAsset.getExt(), extraFieldsInTitleAssetExt);
    return titleAsset;
  }

  private VideoAsset nativeVideoToVideoAsset(NativeVideo nativeVideo, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeVideo)) {
      return null;
    }
    VideoAsset videoAsset = new VideoAsset();
    videoAsset.setAdm(nativeVideo.getVasttag());
    if(nonNull(nativeVideo.getExt())) {
      videoAsset.setExt(new HashMap<>(nativeVideo.getExt()));
    }
    fetchFromExt(videoAsset::setCurl, nativeVideo.getExt(), CommonConstants.CURL, "Error while mapping curl from nativevideo");
    removeFromExt(videoAsset.getExt(), extraFieldsInVideoAssetExt);
    return videoAsset;
  }
}
