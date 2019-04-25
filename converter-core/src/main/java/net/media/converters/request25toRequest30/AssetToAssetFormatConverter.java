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

package net.media.converters.request25toRequest30;

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.*;
import net.media.openrtb25.request.Segment;
import net.media.openrtb3.*;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.MapUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.*;
import java.util.zip.CheckedOutputStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.*;

/** Created by rajat.go on 03/01/19. */
public class AssetToAssetFormatConverter implements Converter<Asset, AssetFormat> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  private static final List<String> extraFieldsInVideoExt = new ArrayList<>();
  private static final List<String> extraFieldsInImageExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CLICKBROWSER);
    extraFieldsInVideoExt.add(CommonConstants.COMPANIONAD);
    extraFieldsInImageExt.add(CommonConstants.WRATIO);
    extraFieldsInImageExt.add(CommonConstants.HRATIO);
    extraFieldsInVideoExt.add(CommonConstants.PTYPE);
    extraFieldsInVideoExt.add(CommonConstants.POS);
    extraFieldsInVideoExt.add(CommonConstants.STARTDELAY);
    extraFieldsInVideoExt.add(CommonConstants.SKIP);
    extraFieldsInVideoExt.add(CommonConstants.SKIPMIN);
    extraFieldsInVideoExt.add(CommonConstants.SKIPAFTER);
    extraFieldsInVideoExt.add(CommonConstants.PLAYBACKMETHOD);
    extraFieldsInVideoExt.add(CommonConstants.API);
    extraFieldsInVideoExt.add(CommonConstants.W);
    extraFieldsInVideoExt.add(CommonConstants.H);
    extraFieldsInVideoExt.add(CommonConstants.UNIT);
    extraFieldsInVideoExt.add(CommonConstants.MAXEXTENDED);
    extraFieldsInVideoExt.add(CommonConstants.MINBITRATE);
    extraFieldsInVideoExt.add(CommonConstants.MAXBITRATE);
    extraFieldsInVideoExt.add(CommonConstants.DELIVERY);
    extraFieldsInVideoExt.add(CommonConstants.MAXSEQ);
    extraFieldsInVideoExt.add(CommonConstants.LINEARITY);
    extraFieldsInVideoExt.add(CommonConstants.BOXINGALLOWED);
    extraFieldsInVideoExt.add(CommonConstants.PLAYBACKEND);
    extraFieldsInVideoExt.add(CommonConstants.COMPANIONTYPE);
  }

  private static final JavaType javaTypeForBannerCollection =
      JacksonObjectMapperUtils.getMapper().getTypeFactory().constructCollectionType(Collection.class, Banner.class);

  @Override
  public AssetFormat map(Asset asset, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (asset == null) {
      return null;
    }
    AssetFormat assetFormat = new AssetFormat();
    enhance(asset, assetFormat, config, converterProvider);
    return assetFormat;
  }

  @Override
  public void enhance(
      Asset asset, AssetFormat assetFormat, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(asset) || isNull(assetFormat)) {
      return;
    }
    assetFormat.setReq(asset.getRequired());
    assetFormat.setId(asset.getId());
    assetFormat.setTitle(nativeTitleToTitleAssetFormat(asset.getTitle(), config));
    assetFormat.setImg(nativeImageToNativeImageAssetFormat(asset.getImg(), config));
    assetFormat.setVideo(nativeVideoToVideoPlacement(asset.getVideo(), config));
    assetFormat.setData(nativeDataToDataAssetFormat(asset.getData(), config));
    Map<String, Object> map = asset.getExt();
    if (map != null) {
      assetFormat.setExt(new HashMap<>(map));
    }
    if(nonNull(assetFormat.getVideo())) {
      fetchFromExt(assetFormat.getVideo()::setClktype, assetFormat.getExt(), CommonConstants.CLICKBROWSER, "Error in setting clktype from asset.ext.clickbrowser");
      fetchCollectionFromExt(assetFormat.getVideo()::setComp, assetFormat.getVideo().getExt(), CommonConstants.COMPANIONAD, "Error in setting creating companion", converterProvider.fetch(new Conversion<>(Banner.class, Companion.class)), config, converterProvider, javaTypeForBannerCollection);
    }
    removeFromExt(assetFormat.getExt(), extraFieldsInExt);
    removeFromExt(assetFormat.getVideo().getExt(), extraFieldsInVideoExt);
    removeFromExt(assetFormat.getImg().getExt(), extraFieldsInImageExt);
  }

  private TitleAssetFormat nativeTitleToTitleAssetFormat(NativeTitle nativeTitle, Config config) {
    if (nativeTitle == null) {
      return null;
    }

    TitleAssetFormat titleAssetFormat = new TitleAssetFormat();

    titleAssetFormat.setLen(nativeTitle.getLen());
    Map<String, Object> map = nativeTitle.getExt();
    if (map != null) {
      titleAssetFormat.setExt(new HashMap<>(map));
    }

    return titleAssetFormat;
  }

  private ImageAssetFormat nativeImageToNativeImageAssetFormat(
      NativeImage nativeImage, Config config) throws OpenRtbConverterException {
    if (nativeImage == null) {
      return null;
    }

    ImageAssetFormat imageAssetFormat = new ImageAssetFormat();

    imageAssetFormat.setMime(CollectionUtils.copyCollection(nativeImage.getMimes(), config));
    imageAssetFormat.setType(nativeImage.getType());
    imageAssetFormat.setW(nativeImage.getW());
    imageAssetFormat.setH(nativeImage.getH());
    imageAssetFormat.setWmin(nativeImage.getWmin());
    imageAssetFormat.setHmin(nativeImage.getHmin());
    fetchFromExt(imageAssetFormat::setWratio, nativeImage.getExt(), CommonConstants.WRATIO, "exception in converting image asset format");
    fetchFromExt(imageAssetFormat::setHratio, nativeImage.getExt(), CommonConstants.HRATIO, "exception in converting image asset format");
    Map<String, Object> map = nativeImage.getExt();
    if (map != null) {
      imageAssetFormat.setExt(new HashMap<>(map));
    }
    return imageAssetFormat;
  }

  private VideoPlacement nativeVideoToVideoPlacement(NativeVideo nativeVideo, Config config)
      throws OpenRtbConverterException {
    if (nativeVideo == null) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();

    videoPlacement.setMaxdur(nativeVideo.getMaxduration());
    videoPlacement.setMindur(nativeVideo.getMinduration());
    videoPlacement.setCtype(CollectionUtils.copyCollection(nativeVideo.getProtocols(), config));
    videoPlacement.setMime(CollectionUtils.copyCollection(nativeVideo.getMimes(), config));
    if(nonNull(nativeVideo.getExt())) {
      videoPlacement.setExt(new HashMap<>(nativeVideo.getExt()));
    }
    fetchFromExt(videoPlacement::setPtype, nativeVideo.getExt(), CommonConstants.PTYPE, "Error in setting ptype from asset.video.ext");
    fetchFromExt(videoPlacement::setPos, nativeVideo.getExt(), CommonConstants.POS, "Error in setting pos from asset.video.ext");
    fetchFromExt(videoPlacement::setDelay, nativeVideo.getExt(), CommonConstants.STARTDELAY, "Error in setting startdelay from asset.video.ext");
    fetchFromExt(videoPlacement::setSkip, nativeVideo.getExt(), CommonConstants.SKIP, "Error in setting skip from asset.video.ext");
    fetchFromExt(videoPlacement::setSkipmin, nativeVideo.getExt(), CommonConstants.SKIPMIN, "Error in setting skipmin from asset.video.ext");
    fetchFromExt(videoPlacement::setSkipafter, nativeVideo.getExt(), CommonConstants.SKIPAFTER, "Error in setting skipafter from asset.video.ext");
    fetchElementFromListInExt(videoPlacement::setPlaymethod, nativeVideo.getExt(), CommonConstants.PLAYBACKMETHOD, "Error in setting playbackmethod from asset.video.ext");
    fetchFromExt(videoPlacement::setApi, nativeVideo.getExt(), CommonConstants.API, "Error in setting api from asset.video.ext");
    fetchFromExt(videoPlacement::setW, nativeVideo.getExt(), CommonConstants.W, "Error in setting w from asset.video.ext");
    fetchFromExt(videoPlacement::setH, nativeVideo.getExt(), CommonConstants.H, "Error in setting h from asset.video.ext");
    fetchFromExt(videoPlacement::setUnit, nativeVideo.getExt(), CommonConstants.UNIT, "Error in setting unit from asset.video.ext");
    fetchFromExt(videoPlacement::setMaxext, nativeVideo.getExt(), CommonConstants.MAXEXTENDED, "Error in setting maxextended from asset.video.ext");
    fetchFromExt(videoPlacement::setMinbitr, nativeVideo.getExt(), CommonConstants.MINBITRATE, "Error in setting minbitrate from asset.video.ext");
    fetchFromExt(videoPlacement::setMaxbitr, nativeVideo.getExt(), CommonConstants.MAXBITRATE, "Error in setting maxbitrate from asset.video.ext");
    fetchFromExt(videoPlacement::setDelivery, nativeVideo.getExt(), CommonConstants.DELIVERY, "Error in setting delivery from asset.video.ext");
    fetchFromExt(videoPlacement::setMaxseq, nativeVideo.getExt(), CommonConstants.MAXSEQ, "Error in setting maxseq from asset.video.ext");
    fetchFromExt(videoPlacement::setLinear, nativeVideo.getExt(), CommonConstants.LINEARITY, "Error in setting linearity from asset.video.ext");
    fetchFromExt(videoPlacement::setBoxing, nativeVideo.getExt(), CommonConstants.BOXINGALLOWED, "Error in setting boxingallowed from asset.video.ext");
    fetchFromExt(videoPlacement::setPlayend, nativeVideo.getExt(), CommonConstants.PLAYBACKEND, "Error in setting playbackend from asset.video.ext");
    fetchFromExt(videoPlacement::setComptype, nativeVideo.getExt(), CommonConstants.COMPANIONTYPE, "Error in setting companiontype from asset.video.ext");
    videoPlacement.setComp(new ArrayList<>());
    return videoPlacement;
  }

  private DataAssetFormat nativeDataToDataAssetFormat(NativeData nativeData, Config config) {
    if (nativeData == null) {
      return null;
    }

    DataAssetFormat dataAssetFormat = new DataAssetFormat();

    dataAssetFormat.setType(nativeData.getType());
    dataAssetFormat.setLen(nativeData.getLen());
    Map<String, Object> map = nativeData.getExt();
    if (map != null) {
      dataAssetFormat.setExt(new HashMap<>(map));
    }

    return dataAssetFormat;
  }
}
