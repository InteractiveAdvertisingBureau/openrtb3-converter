package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Asset;
import net.media.openrtb24.request.NativeData;
import net.media.openrtb24.request.NativeImage;
import net.media.openrtb24.request.NativeTitle;
import net.media.openrtb24.request.NativeVideo;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.DataAssetFormat;
import net.media.openrtb3.ImageAssetFormat;
import net.media.openrtb3.TitleAssetFormat;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 04/01/19.
 */
public class AssetFormatToAssetConverter implements Converter<AssetFormat, Asset> {
  @Override
  public Asset map(AssetFormat assetFormat, Config config) {
    if ( assetFormat == null ) {
      return null;
    }
    Asset asset = new Asset();
    enhance(assetFormat, asset, config);
    return asset;
  }

  @Override
  public void enhance(AssetFormat assetFormat, Asset asset, Config config) {
    if (isNull(assetFormat) || isNull(asset)) {
      return;
    }
    asset.setRequired( assetFormat.getReq() );
    asset.setId( assetFormat.getId() );
    asset.setTitle( titleAssetFormatToNativeTitle( assetFormat.getTitle(), config ) );
    asset.setImg( imageAssetFormatToNativeImage( assetFormat.getImg(), config ) );
    asset.setVideo( videoAssetFormatToVideoImage( assetFormat.getVideo(), config ) );
    asset.setData( dataAssetFormatToNativeData( assetFormat.getData(), config ) );
    asset.setExt(Utils.copyMap(assetFormat.getExt(), config));
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
    config) {
    if ( imageAssetFormat == null ) {
      return null;
    }

    NativeImage nativeImage = new NativeImage();

    nativeImage.setMimes(Utils.copyList(imageAssetFormat.getMime(), config));
    nativeImage.setType( imageAssetFormat.getType() );
    nativeImage.setW( imageAssetFormat.getW() );
    nativeImage.setWmin( imageAssetFormat.getWmin() );
    nativeImage.setH( imageAssetFormat.getH() );
    nativeImage.setHmin( imageAssetFormat.getHmin() );
    Map<String, Object> map = imageAssetFormat.getExt();
    if ( map != null ) {
      nativeImage.setExt( new HashMap<>( map ) );
    }

    return nativeImage;
  }

  private NativeVideo videoAssetFormatToVideoImage(VideoPlacement videoPlacement, Config config) {
    if ( videoPlacement == null ) {
      return null;
    }

    NativeVideo nativeVideo = new NativeVideo();

    nativeVideo.setProtocols(Utils.copySet(videoPlacement.getCtype(), config));
    nativeVideo.setMinduration( videoPlacement.getMindur() );
    nativeVideo.setMaxduration( videoPlacement.getMaxdur() );
    nativeVideo.setMimes(Utils.copySet(videoPlacement.getMime(), config));
    nativeVideo.setMimes(Utils.copySet(videoPlacement.getMime(), config));
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
