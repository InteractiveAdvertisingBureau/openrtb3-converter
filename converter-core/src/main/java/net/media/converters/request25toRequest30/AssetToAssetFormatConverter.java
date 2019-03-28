package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeData;
import net.media.openrtb25.request.NativeImage;
import net.media.openrtb25.request.NativeTitle;
import net.media.openrtb25.request.NativeVideo;
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
 * Created by rajat.go on 03/01/19.
 */
public class AssetToAssetFormatConverter implements Converter<Asset, AssetFormat> {
  @Override
  public AssetFormat map(Asset asset, Config config) {
    if ( asset == null ) {
      return null;
    }
    AssetFormat assetFormat = new AssetFormat();
    enhance(asset, assetFormat, config);
    return assetFormat;
  }

  @Override
  public void enhance(Asset asset, AssetFormat assetFormat, Config config) {
    if (isNull(asset) || isNull(assetFormat)) {
      return;
    }
    assetFormat.setReq( asset.getRequired() );
    assetFormat.setId( asset.getId() );
    assetFormat.setTitle( nativeTitleToTitleAssetFormat( asset.getTitle(), config ) );
    assetFormat.setImg( nativeImageToNativeImageAssetFormat( asset.getImg(), config ) );
    assetFormat.setVideo( nativeVideoToVideoPlacement( asset.getVideo(), config ) );
    assetFormat.setData( nativeDataToDataAssetFormat( asset.getData(), config ) );
    Map<String, Object> map = asset.getExt();
    if ( map != null ) {
      assetFormat.setExt( Utils.copyMap(map, config) );
    }
  }

  private TitleAssetFormat nativeTitleToTitleAssetFormat(NativeTitle nativeTitle, Config config) {
    if ( nativeTitle == null ) {
      return null;
    }

    TitleAssetFormat titleAssetFormat = new TitleAssetFormat();

    titleAssetFormat.setLen( nativeTitle.getLen() );
    Map<String, Object> map = nativeTitle.getExt();
    if ( map != null ) {
      titleAssetFormat.setExt( Utils.copyMap(map, config) );
    }

    return titleAssetFormat;
  }

  private ImageAssetFormat nativeImageToNativeImageAssetFormat(NativeImage nativeImage, Config
    config) {
    if ( nativeImage == null ) {
      return null;
    }

    ImageAssetFormat imageAssetFormat = new ImageAssetFormat();

    imageAssetFormat.setMime(Utils.copyList(nativeImage.getMimes(), config));
    imageAssetFormat.setType( nativeImage.getType() );
    imageAssetFormat.setW( nativeImage.getW() );
    imageAssetFormat.setH( nativeImage.getH() );
    imageAssetFormat.setWmin( nativeImage.getWmin() );
    imageAssetFormat.setHmin( nativeImage.getHmin() );
    Map<String, Object> map = nativeImage.getExt();
    if ( map != null ) {
      imageAssetFormat.setExt( Utils.copyMap(map, config) );
    }

    return imageAssetFormat;
  }

  private VideoPlacement nativeVideoToVideoPlacement(NativeVideo nativeVideo, Config config) {
    if ( nativeVideo == null ) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();

    videoPlacement.setMaxdur( nativeVideo.getMaxduration() );
    videoPlacement.setMindur( nativeVideo.getMinduration() );
    videoPlacement.setCtype(Utils.copySet(nativeVideo.getProtocols(), config));
    videoPlacement.setMime(Utils.copySet(nativeVideo.getMimes(), config));
    videoPlacement.setExt(Utils.copyMap(nativeVideo.getExt(), config));

    return videoPlacement;
  }

  private DataAssetFormat nativeDataToDataAssetFormat(NativeData nativeData, Config config) {
    if ( nativeData == null ) {
      return null;
    }

    DataAssetFormat dataAssetFormat = new DataAssetFormat();

    dataAssetFormat.setType( nativeData.getType() );
    dataAssetFormat.setLen( nativeData.getLen() );
    Map<String, Object> map = nativeData.getExt();
    if ( map != null ) {
      dataAssetFormat.setExt( Utils.copyMap(map, config) );
    }

    return dataAssetFormat;
  }
}
