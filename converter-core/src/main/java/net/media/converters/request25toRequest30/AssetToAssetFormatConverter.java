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
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/** Created by rajat.go on 03/01/19. */
public class AssetToAssetFormatConverter implements Converter<Asset, AssetFormat> {

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
      assetFormat.setExt(MapUtils.copyMap(map, config));

      try {
        if (assetFormat.getExt().containsKey(CommonConstants.CLICKBROWSER)) {
          assetFormat.getVideo().setClktype((Integer) assetFormat.getExt().get(CommonConstants.CLICKBROWSER));
          assetFormat.getExt().remove(CommonConstants.CLICKBROWSER);
        }
      } catch (Exception e) {
        throw new OpenRtbConverterException(
            "Error in setting clktype from asset.ext.clickbrowser", e);
      }
    }

    if (assetFormat.getVideo().getExt().containsKey(CommonConstants.COMPANIONAD)) {
      try {
        Collection<Banner> banners =
            JacksonObjectMapperUtils.getMapper()
                .convertValue(
                    assetFormat.getVideo().getExt().get("companionad"),
                    javaTypeForBannerCollection);
        ArrayList<Companion> companionArrayList = new ArrayList<>();
        Converter<Banner, Companion> bannerCompanionConverter =
            converterProvider.fetch(new Conversion<>(Banner.class, Companion.class));
        for (Banner banner : banners) {
          companionArrayList.add(bannerCompanionConverter.map(banner, config, converterProvider));
        }
        assetFormat.getVideo().setComp(companionArrayList);
        assetFormat.getVideo().getExt().remove("companionad");
      } catch (Exception e) {
        throw new OpenRtbConverterException("Error in setting creating companion", e);
      }
    }
  }

  private TitleAssetFormat nativeTitleToTitleAssetFormat(NativeTitle nativeTitle, Config config) {
    if (nativeTitle == null) {
      return null;
    }

    TitleAssetFormat titleAssetFormat = new TitleAssetFormat();

    titleAssetFormat.setLen(nativeTitle.getLen());
    Map<String, Object> map = nativeTitle.getExt();
    if (map != null) {
      titleAssetFormat.setExt(MapUtils.copyMap(map, config));
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
    if (nativeImage.getExt() != null) {
      if (nativeImage.getExt().containsKey("wratio")) {
        try {
          imageAssetFormat.setWratio((Integer) nativeImage.getExt().get("wratio"));
          nativeImage.getExt().remove("wratio");

        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("exception in converting image asset format", e);
        }
      }
      if (nativeImage.getExt().containsKey("hratio")) {
        try {
          imageAssetFormat.setHratio((Integer) nativeImage.getExt().get("hratio"));
          nativeImage.getExt().remove("hratio");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("exception in converting image asset format", e);
        }
      }
    }
    Map<String, Object> map = nativeImage.getExt();
    if (map != null) {
      imageAssetFormat.setExt(MapUtils.copyMap(map, config));
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
    videoPlacement.setExt(MapUtils.copyMap(nativeVideo.getExt(), config));
    try {
      if (nativeVideo.getExt() != null) {
        if (nativeVideo.getExt().containsKey(CommonConstants.PTYPE)) {
          videoPlacement.setPtype((Integer) nativeVideo.getExt().get(CommonConstants.PTYPE));
          videoPlacement.getExt().remove(CommonConstants.PTYPE);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.POS)) {
          videoPlacement.setPos((Integer) nativeVideo.getExt().get(CommonConstants.POS));
          videoPlacement.getExt().remove(CommonConstants.POS);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.STARTDELAY)) {
          videoPlacement.setDelay((Integer) nativeVideo.getExt().get(CommonConstants.STARTDELAY));
          videoPlacement.getExt().remove(CommonConstants.STARTDELAY);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.SKIP)) {
          videoPlacement.setSkip((Integer) nativeVideo.getExt().get(CommonConstants.SKIP));
          videoPlacement.getExt().remove(CommonConstants.SKIP);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.SKIPMIN)) {
          videoPlacement.setSkipmin((Integer) nativeVideo.getExt().get(CommonConstants.SKIPMIN));
          videoPlacement.getExt().remove(CommonConstants.SKIPMIN);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.SKIPAFTER)) {
          videoPlacement.setSkipafter((Integer) nativeVideo.getExt().get(CommonConstants.SKIPAFTER));
          videoPlacement.getExt().remove(CommonConstants.SKIPAFTER);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.PLAYBACKMETHOD)) {
          videoPlacement.setPlaymethod(
              ((List<Integer>) nativeVideo.getExt().get(CommonConstants.PLAYBACKMETHOD)).get(0));
          videoPlacement.getExt().remove(CommonConstants.PLAYBACKMETHOD);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.API)) {
          videoPlacement.setApi((List<Integer>) nativeVideo.getExt().get(CommonConstants.API));
          videoPlacement.getExt().remove(CommonConstants.API);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.W)) {
          videoPlacement.setW((Integer) nativeVideo.getExt().get(CommonConstants.W));
          videoPlacement.getExt().remove(CommonConstants.W);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.H)) {
          videoPlacement.setH((Integer) nativeVideo.getExt().get(CommonConstants.H));
          videoPlacement.getExt().remove(CommonConstants.H);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.UNIT)) {
          videoPlacement.setUnit((Integer) nativeVideo.getExt().get(CommonConstants.UNIT));
          videoPlacement.getExt().remove(CommonConstants.UNIT);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.MAXEXTENDED)) {
          videoPlacement.setMaxext((Integer) nativeVideo.getExt().get(CommonConstants.MAXEXTENDED));
          videoPlacement.getExt().remove(CommonConstants.MAXEXTENDED);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.MINBITRATE)) {
          videoPlacement.setMinbitr((Integer) nativeVideo.getExt().get(CommonConstants.MINBITRATE));
          videoPlacement.getExt().remove(CommonConstants.MINBITRATE);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.MAXBITRATE)) {
          videoPlacement.setMaxbitr((Integer) nativeVideo.getExt().get(CommonConstants.MAXBITRATE));
          videoPlacement.getExt().remove(CommonConstants.MAXBITRATE);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.DELIVERY)) {
          videoPlacement.setDelivery((List<Integer>) nativeVideo.getExt().get(CommonConstants.DELIVERY));
          videoPlacement.getExt().remove(CommonConstants.DELIVERY);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.MAXSEQ)) {
          videoPlacement.setMaxseq((Integer) nativeVideo.getExt().get(CommonConstants.MAXSEQ));
          videoPlacement.getExt().remove(CommonConstants.MAXSEQ);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.LINEARITY)) {
          videoPlacement.setLinear((Integer) nativeVideo.getExt().get(CommonConstants.LINEARITY));
          videoPlacement.getExt().remove(CommonConstants.LINEARITY);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.BOXINGALLOWED)) {
          videoPlacement.setBoxing((Integer) nativeVideo.getExt().get(CommonConstants.BOXINGALLOWED));
          videoPlacement.getExt().remove(CommonConstants.BOXINGALLOWED);
        }
        if (nativeVideo.getExt().containsKey(CommonConstants.PLAYBACKEND)) {
          videoPlacement.setPlayend((Integer) nativeVideo.getExt().get(CommonConstants.PLAYBACKEND));
          videoPlacement.getExt().remove(CommonConstants.PLAYBACKEND);
        }

        if (nativeVideo.getExt().containsKey(CommonConstants.COMPANIONTYPE)) {
          videoPlacement.setComptype((List<Integer>) nativeVideo.getExt().get(CommonConstants.COMPANIONTYPE));
          videoPlacement.getExt().remove(CommonConstants.COMPANIONTYPE);
        }

        /*if(nativeVideo.getExt().containsKey("companionad")) {
          Collection<Companion> companionads = JacksonObjectMapperUtils.getMapper().convertValue(nativeVideo.getExt().get("companionad"),
            javaTypeForCompanionCollection);
          videoPlacement.setComp(companionads);
          videoPlacement.getExt().remove("companionad");
        }*/

        videoPlacement.setComp(new ArrayList<>());
      }

    } catch (Exception e) {
      throw new OpenRtbConverterException(
          "Error in setting videoplacement values from asset.veideo.ext", e);
    }

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
      dataAssetFormat.setExt(MapUtils.copyMap(map, config));
    }

    return dataAssetFormat;
  }
}
