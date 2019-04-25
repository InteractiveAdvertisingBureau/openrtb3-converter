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
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.*;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.*;

import static java.util.Objects.isNull;

/** Created by rajat.go on 04/01/19. */
public class AssetFormatToAssetConverter implements Converter<AssetFormat, Asset> {

  @Override
  public Asset map(AssetFormat assetFormat, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (assetFormat == null) {
      return null;
    }
    Asset asset = new Asset();
    enhance(assetFormat, asset, config, converterProvider);
    return asset;
  }

  @Override
  public void enhance(
      AssetFormat assetFormat, Asset asset, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(assetFormat) || isNull(asset)) {
      return;
    }
    asset.setRequired(assetFormat.getReq());
    asset.setId(assetFormat.getId());
    asset.setTitle(titleAssetFormatToNativeTitle(assetFormat.getTitle(), config));
    asset.setImg(imageAssetFormatToNativeImage(assetFormat.getImg(), config));
    asset.setVideo(videoAssetFormatToVideoImage(assetFormat.getVideo(), config));
    asset.setData(dataAssetFormatToNativeData(assetFormat.getData(), config));
    asset.setExt(MapUtils.copyMap(assetFormat.getExt(), config));

    if (asset.getExt() == null) {
      asset.setExt(new HashMap<>());
    }
    asset.getExt().put(CommonConstants.CLICKBROWSER, assetFormat.getVideo().getClktype());

    if (assetFormat.getVideo().getComp() != null) {
      try {
        Collection<Banner> banners = new ArrayList<>();
        Converter<Companion, Banner> companionToBannerConverter =
            converterProvider.fetch(new Conversion<>(Companion.class, Banner.class));
        for (Companion companion : assetFormat.getVideo().getComp()) {
          banners.add(companionToBannerConverter.map(companion, config, converterProvider));
        }
        if (asset.getVideo().getExt() == null) {
          asset.getVideo().setExt(new HashMap<>());
        }
        asset.getVideo().getExt().put(CommonConstants.COMPANIONAD, banners);
      } catch (Exception e) {
        throw new OpenRtbConverterException("Error in setting creating companion", e);
      }
    }
  }

  private NativeTitle titleAssetFormatToNativeTitle(
      TitleAssetFormat titleAssetFormat, Config config) {
    if (titleAssetFormat == null) {
      return null;
    }

    NativeTitle nativeTitle = new NativeTitle();

    nativeTitle.setLen(titleAssetFormat.getLen());
    nativeTitle.setExt(MapUtils.copyMap(titleAssetFormat.getExt(), config));

    return nativeTitle;
  }

  private NativeImage imageAssetFormatToNativeImage(
      ImageAssetFormat imageAssetFormat, Config config) throws OpenRtbConverterException {
    if (imageAssetFormat == null) {
      return null;
    }

    NativeImage nativeImage = new NativeImage();

    nativeImage.setMimes(CollectionUtils.copyCollection(imageAssetFormat.getMime(), config));
    nativeImage.setType(imageAssetFormat.getType());
    nativeImage.setW(imageAssetFormat.getW());
    nativeImage.setWmin(imageAssetFormat.getWmin());
    nativeImage.setH(imageAssetFormat.getH());
    nativeImage.setHmin(imageAssetFormat.getHmin());
    Map<String, Object> map = imageAssetFormat.getExt();
    if (map != null) {
      nativeImage.setExt(MapUtils.copyMap(map, config));
    }
    try {
      if (imageAssetFormat.getHratio() != null) {
        if (nativeImage.getExt() == null) nativeImage.setExt(new HashMap<>());
        nativeImage.getExt().put(CommonConstants.HRATIO, imageAssetFormat.getHratio());
      }
      if (imageAssetFormat.getWratio() != null) {
        if (nativeImage.getExt() == null) nativeImage.setExt(new HashMap<>());
        nativeImage.getExt().put(CommonConstants.WRATIO, imageAssetFormat.getWratio());
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException(e);
    }

    return nativeImage;
  }

  private NativeVideo videoAssetFormatToVideoImage(VideoPlacement videoPlacement, Config config) {
    if (videoPlacement == null) {
      return null;
    }

    NativeVideo nativeVideo = new NativeVideo();

    nativeVideo.setProtocols(CollectionUtils.copyCollection(videoPlacement.getCtype(), config));
    nativeVideo.setMinduration(videoPlacement.getMindur());
    nativeVideo.setMaxduration(videoPlacement.getMaxdur());
    nativeVideo.setMimes(CollectionUtils.copyCollection(videoPlacement.getMime(), config));
    nativeVideo.setExt(MapUtils.copyMap(videoPlacement.getExt(), config));
    nativeVideo.getExt().put(CommonConstants.BOXINGALLOWED, videoPlacement.getBoxing());
    nativeVideo.getExt().put(CommonConstants.PTYPE, videoPlacement.getPtype());
    nativeVideo.getExt().put(CommonConstants.POS, videoPlacement.getPos());
    nativeVideo.getExt().put(CommonConstants.STARTDELAY, videoPlacement.getDelay());
    nativeVideo.getExt().put(CommonConstants.SKIP, videoPlacement.getSkip());
    nativeVideo.getExt().put(CommonConstants.SKIPMIN, videoPlacement.getSkipmin());
    nativeVideo.getExt().put(CommonConstants.SKIPAFTER, videoPlacement.getSkipafter());
    nativeVideo.getExt().put(CommonConstants.PLAYBACKMETHOD, Collections.singletonList
      (videoPlacement.getPlaymethod()));
    nativeVideo.getExt().put(CommonConstants.PLAYBACKEND, videoPlacement.getPlayend());
    nativeVideo.getExt().put(CommonConstants.API, videoPlacement.getApi());
    nativeVideo.getExt().put(CommonConstants.W, videoPlacement.getW());
    nativeVideo.getExt().put(CommonConstants.H, videoPlacement.getH());
    nativeVideo.getExt().put(CommonConstants.UNIT, videoPlacement.getUnit());
    nativeVideo.getExt().put(CommonConstants.MAXEXTENDED, videoPlacement.getMaxext());
    nativeVideo.getExt().put(CommonConstants.MINBITRATE, videoPlacement.getMinbitr());
    nativeVideo.getExt().put(CommonConstants.MAXBITRATE, videoPlacement.getMaxbitr());
    nativeVideo.getExt().put(CommonConstants.DELIVERY, videoPlacement.getDelivery());
    nativeVideo.getExt().put(CommonConstants.MAXSEQ, videoPlacement.getMaxseq());
    nativeVideo.getExt().put(CommonConstants.LINEARITY, videoPlacement.getLinear());
    nativeVideo.getExt().put(CommonConstants.COMPANIONTYPE, videoPlacement.getComptype());

    return nativeVideo;
  }

  private NativeData dataAssetFormatToNativeData(DataAssetFormat dataAssetFormat, Config config) {
    if (dataAssetFormat == null) {
      return null;
    }

    NativeData nativeData = new NativeData();

    nativeData.setType(dataAssetFormat.getType());
    nativeData.setLen(dataAssetFormat.getLen());
    nativeData.setExt(MapUtils.copyMap(dataAssetFormat.getExt(), config));

    return nativeData;
  }
}
