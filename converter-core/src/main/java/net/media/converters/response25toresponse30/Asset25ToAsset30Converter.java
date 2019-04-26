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
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** @author shiva.b */
public class Asset25ToAsset30Converter implements Converter<AssetResponse, Asset> {

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
    target.setTitle(nativeTittleToTittleAsset(source.getTitle(), config));
    target.setVideo(nativeVideoToVideoAsset(source.getVideo(), config));
    Converter<Link, LinkAsset> converter =
        converterProvider.fetch(new Conversion<>(Link.class, LinkAsset.class));
    target.setLink(converter.map(source.getLink(), config, converterProvider));
    target.setExt(Utils.copyMap(source.getExt(), config));
  }

  private DataAsset nativeDataToData(NativeData nativeData, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeData)) return null;
    DataAsset dataAsset = new DataAsset();
    dataAsset.setExt(Utils.copyMap(nativeData.getExt(), config));
    dataAsset.setValue(nativeData.getValue());
    try {
      if (nonNull(nativeData.getExt())) {
        if (nativeData.getExt().containsKey("type")) {
          dataAsset.setType((Integer) nativeData.getExt().get("type"));
          dataAsset.getExt().remove("type");
        }
        if (nativeData.getExt().containsKey("len")) {
          dataAsset.setLen((Integer) nativeData.getExt().get("len"));
          dataAsset.getExt().remove("len");
        }
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException("error while typecasting ext for nativeData", e);
    }
    if (isNull(dataAsset.getExt())) dataAsset.setExt(new HashMap<>());
    dataAsset.getExt().put("label", nativeData.getLabel());
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
    imageAsset.setExt(Utils.copyMap(nativeImage.getExt(), config));
    try {
      if (nonNull(nativeImage.getExt())) {
        if (nativeImage.getExt().containsKey("type")) {
          imageAsset.setType((Integer) nativeImage.getExt().get("type"));
          imageAsset.getExt().remove("type");
        }
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext for image asset", e);
    }
    return imageAsset;
  }

  private TitleAsset nativeTittleToTittleAsset(NativeTitle nativeTitle, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeTitle)) return null;
    TitleAsset titleAsset = new TitleAsset();
    titleAsset.setExt(Utils.copyMap(nativeTitle.getExt(), config));
    titleAsset.setText(nativeTitle.getText());
    try {
      if (nonNull(nativeTitle.getExt())) {
        if (nativeTitle.getExt().containsKey("len")) {
          titleAsset.setLen((Integer) nativeTitle.getExt().get("len"));
          titleAsset.getExt().remove("len");
        }
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext for title asset", e);
    }
    return titleAsset;
  }

  private VideoAsset nativeVideoToVideoAsset(NativeVideo nativeVideo, Config config)
      throws OpenRtbConverterException {
    if (isNull(nativeVideo)) return null;
    VideoAsset videoAsset = new VideoAsset();
    videoAsset.setAdm(nativeVideo.getVasttag());
    videoAsset.setExt(Utils.copyMap(nativeVideo.getExt(), config));
    try {
      if (nonNull(nativeVideo.getExt())) {
        if (nativeVideo.getExt().containsKey("curl")) {
          videoAsset.setCurl((String) nativeVideo.getExt().get("curl"));
          videoAsset.getExt().remove("curl");
        }
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException("error while casting ext for videoAsset", e);
    }
    return videoAsset;
  }
}
