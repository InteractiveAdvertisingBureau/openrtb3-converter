package net.media.converters.request30toRequest24;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    inhance(assetFormat, asset, config);
    return asset;
  }

  @Override
  public void inhance(AssetFormat assetFormat, Asset asset, Config config) {
    if (isNull(assetFormat) || isNull(asset)) {
      return;
    }
    asset.setRequired( assetFormat.getReq() );
    asset.setId( assetFormat.getId() );
    asset.setTitle( titleAssetFormatToNativeTitle( assetFormat.getTitle() ) );
    asset.setImg( imageAssetFormatToNativeImage( assetFormat.getImg() ) );
    asset.setVideo( videoAssetFormatToVideoImage( assetFormat.getVideo() ) );
    asset.setData( dataAssetFormatToNativeData( assetFormat.getData() ) );
    Map<String, Object> map = assetFormat.getExt();
    if ( map != null ) {
      asset.setExt( new HashMap<>( map ) );
    }
  }

  private NativeTitle titleAssetFormatToNativeTitle(TitleAssetFormat titleAssetFormat) {
    if ( titleAssetFormat == null ) {
      return null;
    }

    NativeTitle nativeTitle = new NativeTitle();

    nativeTitle.setLen( titleAssetFormat.getLen() );
    Map<String, Object> map = titleAssetFormat.getExt();
    if ( map != null ) {
      nativeTitle.setExt( new HashMap<>( map ) );
    }

    return nativeTitle;
  }

  private NativeImage imageAssetFormatToNativeImage(ImageAssetFormat imageAssetFormat) {
    if ( imageAssetFormat == null ) {
      return null;
    }

    NativeImage nativeImage = new NativeImage();

    List<String> list = imageAssetFormat.getMime();
    if ( list != null ) {
      nativeImage.setMimes( new ArrayList<String>( list ) );
    }
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

  private NativeVideo videoAssetFormatToVideoImage(VideoPlacement videoPlacement) {
    if ( videoPlacement == null ) {
      return null;
    }

    NativeVideo nativeVideo = new NativeVideo();

    Set<Integer> set = videoPlacement.getCtype();
    if ( set != null ) {
      nativeVideo.setProtocols( new HashSet<Integer>( set ) );
    }
    nativeVideo.setMinduration( videoPlacement.getMindur() );
    nativeVideo.setMaxduration( videoPlacement.getMaxdur() );
    Set<String> set1 = videoPlacement.getMime();
    if ( set1 != null ) {
      nativeVideo.setMimes( new HashSet<String>( set1 ) );
    }
    Map<String, Object> map = videoPlacement.getExt();
    if ( map != null ) {
      nativeVideo.setExt( new HashMap<String, Object>( map ) );
    }

    return nativeVideo;
  }

  private NativeData dataAssetFormatToNativeData(DataAssetFormat dataAssetFormat) {
    if ( dataAssetFormat == null ) {
      return null;
    }

    NativeData nativeData = new NativeData();

    nativeData.setType( dataAssetFormat.getType() );
    nativeData.setLen( dataAssetFormat.getLen() );
    Map<String, Object> map = dataAssetFormat.getExt();
    if ( map != null ) {
      nativeData.setExt( new HashMap<>( map ) );
    }

    return nativeData;
  }
}
