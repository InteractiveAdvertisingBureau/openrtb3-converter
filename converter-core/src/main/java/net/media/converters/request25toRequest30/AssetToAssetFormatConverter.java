package net.media.converters.request25toRequest30;

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
 * Created by rajat.go on 03/01/19.
 */
public class AssetToAssetFormatConverter implements Converter<Asset, AssetFormat> {
  @Override
  public AssetFormat map(Asset asset, Config config) {
    if ( asset == null ) {
      return null;
    }
    AssetFormat assetFormat = new AssetFormat();
    inhance(asset, assetFormat, config);
    return assetFormat;
  }

  @Override
  public void inhance(Asset asset, AssetFormat assetFormat, Config config) {
    if (isNull(asset) || isNull(assetFormat)) {
      return;
    }
    assetFormat.setReq( asset.getRequired() );
    assetFormat.setId( asset.getId() );
    assetFormat.setTitle( nativeTitleToTitleAssetFormat( asset.getTitle() ) );
    assetFormat.setImg( nativeImageToNativeImageAssetFormat( asset.getImg() ) );
    assetFormat.setVideo( nativeVideoToVideoPlacement( asset.getVideo() ) );
    assetFormat.setData( nativeDataToDataAssetFormat( asset.getData() ) );
    Map<String, Object> map = asset.getExt();
    if ( map != null ) {
      assetFormat.setExt( new HashMap<>( map ) );
    }
  }

  private TitleAssetFormat nativeTitleToTitleAssetFormat(NativeTitle nativeTitle) {
    if ( nativeTitle == null ) {
      return null;
    }

    TitleAssetFormat titleAssetFormat = new TitleAssetFormat();

    titleAssetFormat.setLen( nativeTitle.getLen() );
    Map<String, Object> map = nativeTitle.getExt();
    if ( map != null ) {
      titleAssetFormat.setExt( new HashMap<>( map ) );
    }

    return titleAssetFormat;
  }

  private ImageAssetFormat nativeImageToNativeImageAssetFormat(NativeImage nativeImage) {
    if ( nativeImage == null ) {
      return null;
    }

    ImageAssetFormat imageAssetFormat = new ImageAssetFormat();

    List<String> list = nativeImage.getMimes();
    if ( list != null ) {
      imageAssetFormat.setMime( new ArrayList<>( list ) );
    }
    imageAssetFormat.setType( nativeImage.getType() );
    imageAssetFormat.setW( nativeImage.getW() );
    imageAssetFormat.setH( nativeImage.getH() );
    imageAssetFormat.setWmin( nativeImage.getWmin() );
    imageAssetFormat.setHmin( nativeImage.getHmin() );
    Map<String, Object> map = nativeImage.getExt();
    if ( map != null ) {
      imageAssetFormat.setExt( new HashMap<>( map ) );
    }

    return imageAssetFormat;
  }

  private VideoPlacement nativeVideoToVideoPlacement(NativeVideo nativeVideo) {
    if ( nativeVideo == null ) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();

    videoPlacement.setMaxdur( nativeVideo.getMaxduration() );
    videoPlacement.setMindur( nativeVideo.getMinduration() );
    Set<Integer> set = nativeVideo.getProtocols();
    if ( set != null ) {
      videoPlacement.setCtype( new HashSet<>( set ) );
    }
    Set<String> set1 = nativeVideo.getMimes();
    if ( set1 != null ) {
      videoPlacement.setMime( new HashSet<>( set1 ) );
    }
    Map<String, Object> map = nativeVideo.getExt();
    if ( map != null ) {
      videoPlacement.setExt( new HashMap<>( map ) );
    }

    return videoPlacement;
  }

  private DataAssetFormat nativeDataToDataAssetFormat(NativeData nativeData) {
    if ( nativeData == null ) {
      return null;
    }

    DataAssetFormat dataAssetFormat = new DataAssetFormat();

    dataAssetFormat.setType( nativeData.getType() );
    dataAssetFormat.setLen( nativeData.getLen() );
    Map<String, Object> map = nativeData.getExt();
    if ( map != null ) {
      dataAssetFormat.setExt( new HashMap<String, Object>( map ) );
    }

    return dataAssetFormat;
  }
}
