package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.*;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.*;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 04/01/19.
 */
public class AssetFormatToAssetConverter implements Converter<AssetFormat, Asset> {
  @Override
  public Asset map(AssetFormat assetFormat, Config config, Provider converterProvider) throws
    OpenRtbConverterException {
    if ( assetFormat == null ) {
      return null;
    }
    Asset asset = new Asset();
    enhance(assetFormat, asset, config, converterProvider);
    return asset;
  }

  @Override
  public void enhance(AssetFormat assetFormat, Asset asset, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (isNull(assetFormat) || isNull(asset)) {
      return;
    }
    asset.setRequired(assetFormat.getReq());
    asset.setId(assetFormat.getId());
    asset.setTitle(titleAssetFormatToNativeTitle(assetFormat.getTitle(), config));
    asset.setImg(imageAssetFormatToNativeImage(assetFormat.getImg(), config));
    asset.setVideo(videoAssetFormatToVideoImage(assetFormat.getVideo(), config));
    asset.setData(dataAssetFormatToNativeData(assetFormat.getData(), config));
    asset.setExt(Utils.copyMap(assetFormat.getExt(), config));

    if (assetFormat.getVideo().getComp() != null)
    {
      try {
        Collection<Banner> banners = new ArrayList<>();
        Converter<Companion, Banner> companionToBannerConverter = converterProvider.fetch(new Conversion<>
          (Companion.class, Banner.class));
        for (Companion companion : assetFormat.getVideo().getComp()) {
          banners.add(companionToBannerConverter.map(companion, config, converterProvider));
        }
        if(asset.getVideo().getExt() == null) {
          asset.getVideo().setExt(new HashMap<>());
        }
        asset.getVideo().getExt().put("companionad",banners);
      } catch (Exception e) {
        throw new OpenRtbConverterException("Error in setting creating companion", e);
      }
  }



  }

  private NativeTitle titleAssetFormatToNativeTitle(TitleAssetFormat titleAssetFormat, Config
    config) {
    if ( titleAssetFormat == null ) {
      return null;
    }

    NativeTitle nativeTitle = new NativeTitle();

    nativeTitle.setLen( titleAssetFormat.getLen() );
    nativeTitle.setExt(Utils.copyMap(titleAssetFormat.getExt(), config));

    return nativeTitle;
  }

  private NativeImage imageAssetFormatToNativeImage(ImageAssetFormat imageAssetFormat, Config
    config) throws OpenRtbConverterException {
    if ( imageAssetFormat == null ) {
      return null;
    }

    NativeImage nativeImage = new NativeImage();

    nativeImage.setMimes(Utils.copyCollection(imageAssetFormat.getMime(), config));
    nativeImage.setType( imageAssetFormat.getType() );
    nativeImage.setW( imageAssetFormat.getW() );
    nativeImage.setWmin( imageAssetFormat.getWmin() );
    nativeImage.setH( imageAssetFormat.getH() );
    nativeImage.setHmin( imageAssetFormat.getHmin() );
    Map<String, Object> map = imageAssetFormat.getExt();
    if ( map != null ) {
      nativeImage.setExt( Utils.copyMap(map, config) );
    }
    try {
      if (imageAssetFormat.getHratio() != null) {
        if (nativeImage.getExt() == null)
          nativeImage.setExt(new HashMap<>());
        imageAssetFormat.getExt().put("hratio", imageAssetFormat.getHratio());
      }
      if (imageAssetFormat.getWratio() != null) {
        if (nativeImage.getExt() == null)
          nativeImage.setExt(new HashMap<>());
        imageAssetFormat.getExt().put("wratio", imageAssetFormat.getWratio());
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException(e);
    }

    return nativeImage;
  }

  private NativeVideo videoAssetFormatToVideoImage(VideoPlacement videoPlacement, Config config) {
    if ( videoPlacement == null ) {
      return null;
    }

    NativeVideo nativeVideo = new NativeVideo();

    nativeVideo.setProtocols(Utils.copyCollection(videoPlacement.getCtype(), config));
    nativeVideo.setMinduration( videoPlacement.getMindur() );
    nativeVideo.setMaxduration( videoPlacement.getMaxdur() );
    nativeVideo.setMimes(Utils.copyCollection(videoPlacement.getMime(), config));
    nativeVideo.setMimes(Utils.copyCollection(videoPlacement.getMime(), config));
    nativeVideo.setExt(Utils.copyMap(videoPlacement.getExt(), config));

    return nativeVideo;
  }

  private NativeData dataAssetFormatToNativeData(DataAssetFormat dataAssetFormat, Config config) {
    if ( dataAssetFormat == null ) {
      return null;
    }

    NativeData nativeData = new NativeData();

    nativeData.setType( dataAssetFormat.getType() );
    nativeData.setLen( dataAssetFormat.getLen() );
    nativeData.setExt(Utils.copyMap(dataAssetFormat.getExt(), config));

    return nativeData;
  }
}
