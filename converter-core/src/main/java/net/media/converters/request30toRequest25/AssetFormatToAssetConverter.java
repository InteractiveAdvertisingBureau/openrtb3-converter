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
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.putListFromSingleObjectToExt;
import static net.media.utils.ExtUtils.putToExt;

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
    if (nonNull(assetFormat.getExt())) {
      asset.setExt(new HashMap<>(assetFormat.getExt()));
    }
    if(nonNull(assetFormat.getVideo())) {
      putToExt(
        assetFormat.getVideo()::getClktype,
        asset.getExt(),
        CommonConstants.CLICKBROWSER,
        asset::setExt);

      if (assetFormat.getVideo().getComp() != null) {
        Converter<Companion, Banner> companionToBannerConverter =
          converterProvider.fetch(new Conversion<>(Companion.class, Banner.class));
        Collection<Banner> banners =
          CollectionToCollectionConverter.convert(
            assetFormat.getVideo().getComp(),
            companionToBannerConverter,
            config,
            converterProvider);
        if(nonNull(asset.getVideo()))
        putToExt(
          () -> banners,
          asset.getVideo().getExt(),
          CommonConstants.COMPANIONAD,
          asset.getVideo()::setExt);
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
    if (nonNull(titleAssetFormat.getExt())) {
      nativeTitle.setExt(new HashMap<>(titleAssetFormat.getExt()));
    }
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
      nativeImage.setExt(new HashMap<>(map));
    }
    putToExt(
      imageAssetFormat::getHratio,
      nativeImage.getExt(),
      CommonConstants.HRATIO,
      nativeImage::setExt);
    putToExt(
      imageAssetFormat::getWratio,
      nativeImage.getExt(),
      CommonConstants.WRATIO,
      nativeImage::setExt);
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
    if (nonNull(videoPlacement.getExt())) {
      nativeVideo.setExt(new HashMap<>(videoPlacement.getExt()));
    }
    putToExt(
      videoPlacement::getBoxing,
      nativeVideo.getExt(),
      CommonConstants.BOXINGALLOWED,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getPtype, nativeVideo.getExt(), CommonConstants.PTYPE, nativeVideo::setExt);
    putToExt(
      videoPlacement::getPos, nativeVideo.getExt(), CommonConstants.POS, nativeVideo::setExt);
    putToExt(
      videoPlacement::getDelay,
      nativeVideo.getExt(),
      CommonConstants.STARTDELAY,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getSkip, nativeVideo.getExt(), CommonConstants.SKIP, nativeVideo::setExt);
    putToExt(
      videoPlacement::getSkipmin,
      nativeVideo.getExt(),
      CommonConstants.SKIPMIN,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getSkipafter,
      nativeVideo.getExt(),
      CommonConstants.SKIPAFTER,
      nativeVideo::setExt);
    putListFromSingleObjectToExt(
      videoPlacement::getPlaymethod,
      nativeVideo.getExt(),
      CommonConstants.PLAYBACKMETHOD,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getPlayend,
      nativeVideo.getExt(),
      CommonConstants.PLAYBACKEND,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getApi, nativeVideo.getExt(), CommonConstants.API, nativeVideo::setExt);
    putToExt(videoPlacement::getW, nativeVideo.getExt(), CommonConstants.W, nativeVideo::setExt);
    putToExt(videoPlacement::getH, nativeVideo.getExt(), CommonConstants.H, nativeVideo::setExt);
    putToExt(
      videoPlacement::getUnit, nativeVideo.getExt(), CommonConstants.UNIT, nativeVideo::setExt);
    putToExt(
      videoPlacement::getMaxext,
      nativeVideo.getExt(),
      CommonConstants.MAXEXTENDED,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getMinbitr,
      nativeVideo.getExt(),
      CommonConstants.MINBITRATE,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getMaxbitr,
      nativeVideo.getExt(),
      CommonConstants.MAXBITRATE,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getDelivery,
      nativeVideo.getExt(),
      CommonConstants.DELIVERY,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getMaxseq,
      nativeVideo.getExt(),
      CommonConstants.MAXSEQ,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getLinear,
      nativeVideo.getExt(),
      CommonConstants.LINEARITY,
      nativeVideo::setExt);
    putToExt(
      videoPlacement::getComptype,
      nativeVideo.getExt(),
      CommonConstants.COMPANIONTYPE,
      nativeVideo::setExt);
    return nativeVideo;
  }

  private NativeData dataAssetFormatToNativeData(DataAssetFormat dataAssetFormat, Config config) {
    if (dataAssetFormat == null) {
      return null;
    }

    NativeData nativeData = new NativeData();

    nativeData.setType(dataAssetFormat.getType());
    nativeData.setLen(dataAssetFormat.getLen());
    if (nonNull(dataAssetFormat.getExt())) {
      nativeData.setExt(new HashMap<>(dataAssetFormat.getExt()));
    }
    return nativeData;
  }
}
