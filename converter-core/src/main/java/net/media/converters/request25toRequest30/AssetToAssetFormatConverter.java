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
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.*;

import static java.util.Objects.isNull;

/** Created by rajat.go on 03/01/19. */
public class AssetToAssetFormatConverter implements Converter<Asset, AssetFormat> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  private static final List<String> extraFieldsInVideoExt = new ArrayList<>();
  private static final List<String> extraFieldsInImageExt = new ArrayList<>();
  static {
    extraFieldsInExt.add("clickbrowser");
    extraFieldsInVideoExt.add("companionad");
    extraFieldsInImageExt.add("wratio");
    extraFieldsInImageExt.add("hratio");
    extraFieldsInVideoExt.add("ptype");
    extraFieldsInVideoExt.add("pos");
    extraFieldsInVideoExt.add("startdelay");
    extraFieldsInVideoExt.add("skip");
    extraFieldsInVideoExt.add("skipmin");
    extraFieldsInVideoExt.add("skipafter");
    extraFieldsInVideoExt.add("playbackmethod");
    extraFieldsInVideoExt.add("api");
    extraFieldsInVideoExt.add("w");
    extraFieldsInVideoExt.add("h");
    extraFieldsInVideoExt.add("unit");
    extraFieldsInVideoExt.add("maxextended");
    extraFieldsInImageExt.add("minbitrate");
    extraFieldsInImageExt.add("maxbitrate");
    extraFieldsInImageExt.add("delivery");
    extraFieldsInImageExt.add("maxseq");
    extraFieldsInImageExt.add("linearity");
    extraFieldsInImageExt.add("boxingallowed");
    extraFieldsInImageExt.add("playbackend");
    extraFieldsInImageExt.add("companiontype");

  }

  private static final JavaType javaTypeForBannerCollection =
      Utils.getMapper().getTypeFactory().constructCollectionType(Collection.class, Banner.class);

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
      try {
        if (assetFormat.getExt().containsKey("clickbrowser")) {
          assetFormat.getVideo().setClktype((Integer) assetFormat.getExt().get("clickbrowser"));
        }
      } catch (Exception e) {
        throw new OpenRtbConverterException(
            "Error in setting clktype from asset.ext.clickbrowser", e);
      }
    }

    if (assetFormat.getVideo().getExt().containsKey("companionad")) {
      try {
        Collection<Banner> banners =
            Utils.getMapper()
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
      } catch (Exception e) {
        throw new OpenRtbConverterException("Error in setting creating companion", e);
      }
    }
    removeFromExt(assetFormat.getExt(), extraFieldsInExt);
    removeFromExt(assetFormat.getVideo().getExt(), extraFieldsInVideoExt);
    removeFromExt(assetFormat.getImg().getExt(),extraFieldsInImageExt);
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

    imageAssetFormat.setMime(Utils.copyCollection(nativeImage.getMimes(), config));
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
    videoPlacement.setCtype(Utils.copyCollection(nativeVideo.getProtocols(), config));
    videoPlacement.setMime(Utils.copyCollection(nativeVideo.getMimes(), config));
    videoPlacement.setExt(new HashMap<>(nativeVideo.getExt()));
    try {
      if (nativeVideo.getExt() != null) {
        if (nativeVideo.getExt().containsKey("ptype")) {
          videoPlacement.setPtype((Integer) nativeVideo.getExt().get("ptype"));
        }
        if (nativeVideo.getExt().containsKey("pos")) {
          videoPlacement.setPos((Integer) nativeVideo.getExt().get("pos"));
        }
        if (nativeVideo.getExt().containsKey("startdelay")) {
          videoPlacement.setDelay((Integer) nativeVideo.getExt().get("startdelay"));
        }
        if (nativeVideo.getExt().containsKey("skip")) {
          videoPlacement.setSkip((Integer) nativeVideo.getExt().get("skip"));
        }
        if (nativeVideo.getExt().containsKey("skipmin")) {
          videoPlacement.setSkipmin((Integer) nativeVideo.getExt().get("skipmin"));
        }
        if (nativeVideo.getExt().containsKey("skipafter")) {
          videoPlacement.setSkipafter((Integer) nativeVideo.getExt().get("skipafter"));
        }
        if (nativeVideo.getExt().containsKey("playbackmethod")) {
          videoPlacement.setPlaymethod(
              ((List<Integer>) nativeVideo.getExt().get("playbackmethod")).get(0));
        }
        if (nativeVideo.getExt().containsKey("api")) {
          videoPlacement.setApi((List<Integer>) nativeVideo.getExt().get("api"));
        }
        if (nativeVideo.getExt().containsKey("w")) {
          videoPlacement.setW((Integer) nativeVideo.getExt().get("w"));
        }
        if (nativeVideo.getExt().containsKey("h")) {
          videoPlacement.setH((Integer) nativeVideo.getExt().get("h"));
        }
        if (nativeVideo.getExt().containsKey("unit")) {
          videoPlacement.setUnit((Integer) nativeVideo.getExt().get("unit"));
        }
        if (nativeVideo.getExt().containsKey("maxextended")) {
          videoPlacement.setMaxext((Integer) nativeVideo.getExt().get("maxextended"));
        }
        if (nativeVideo.getExt().containsKey("minbitrate")) {
          videoPlacement.setMinbitr((Integer) nativeVideo.getExt().get("minbitrate"));
        }
        if (nativeVideo.getExt().containsKey("maxbitrate")) {
          videoPlacement.setMaxbitr((Integer) nativeVideo.getExt().get("maxbitrate"));
        }
        if (nativeVideo.getExt().containsKey("delivery")) {
          videoPlacement.setDelivery((List<Integer>) nativeVideo.getExt().get("delivery"));
        }
        if (nativeVideo.getExt().containsKey("maxseq")) {
          videoPlacement.setMaxseq((Integer) nativeVideo.getExt().get("maxseq"));
        }
        if (nativeVideo.getExt().containsKey("linearity")) {
          videoPlacement.setLinear((Integer) nativeVideo.getExt().get("linearity"));
        }
        if (nativeVideo.getExt().containsKey("boxingallowed")) {
          videoPlacement.setBoxing((Integer) nativeVideo.getExt().get("boxingallowed"));
        }
        if (nativeVideo.getExt().containsKey("playbackend")) {
          videoPlacement.setPlayend((Integer) nativeVideo.getExt().get("playbackend"));
        }
        if (nativeVideo.getExt().containsKey("companiontype")) {
          videoPlacement.setComptype((List<Integer>) nativeVideo.getExt().get("companiontype"));
        }

        /*if(nativeVideo.getExt().containsKey("companionad")) {
          Collection<Companion> companionads = Utils.getMapper().convertValue(nativeVideo.getExt().get("companionad"),
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
      dataAssetFormat.setExt(new HashMap<>());
    }

    return dataAssetFormat;
  }
}
