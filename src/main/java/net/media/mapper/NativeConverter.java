package net.media.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;
import net.media.openrtb24.request.Asset;
import net.media.openrtb24.request.NativeData;
import net.media.openrtb24.request.NativeImage;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb24.request.NativeTitle;
import net.media.openrtb24.request.NativeVideo;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.DataAssetFormat;
import net.media.openrtb3.ImageAssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.TitleAssetFormat;
import net.media.openrtb3.VideoPlacement;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T02:44:29+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class NativeConverter {

  public NativeFormat map(NativeRequestBody nativeRequestBody) {
    if ( nativeRequestBody == null ) {
      return null;
    }

    NativeFormat nativeFormat = new NativeFormat();

    Map<String, Object> map = nativeRequestBody.getExt();
    if ( map != null ) {
      nativeFormat.setExt( new HashMap<String, Object>( map ) );
    }
    nativeFormat.setAsset( assetListToAssetFormatList( nativeRequestBody.getAssets() ) );

    return nativeFormat;
  }

  public NativeRequestBody map(NativeFormat nativeFormat) {
    if ( nativeFormat == null ) {
      return null;
    }

    NativeRequestBody nativeRequestBody = new NativeRequestBody();

    Map<String, Object> map = nativeFormat.getExt();
    if ( map != null ) {
      nativeRequestBody.setExt( new HashMap<String, Object>( map ) );
    }
    nativeRequestBody.setAssets( assetFormatListToAssetList( nativeFormat.getAsset() ) );

    return nativeRequestBody;
  }

  public AssetFormat map(Asset asset) {
    if ( asset == null ) {
      return null;
    }

    AssetFormat assetFormat = new AssetFormat();

    assetFormat.setReq( asset.getRequired() );
    assetFormat.setId( asset.getId() );
    assetFormat.setTitle( nativeTitleToTitleAssetFormat( asset.getTitle() ) );
    assetFormat.setImg( map( asset.getImg() ) );
    assetFormat.setVideo( map( asset.getVideo() ) );
    assetFormat.setData( nativeDataToDataAssetFormat( asset.getData() ) );
    Map<String, Object> map = asset.getExt();
    if ( map != null ) {
      assetFormat.setExt( new HashMap<String, Object>( map ) );
    }

    return assetFormat;
  }

  public Asset map(AssetFormat assetFormat) {
    if ( assetFormat == null ) {
      return null;
    }

    Asset asset = new Asset();

    asset.setRequired( assetFormat.getReq() );
    asset.setId( assetFormat.getId() );
    asset.setTitle( titleAssetFormatToNativeTitle( assetFormat.getTitle() ) );
    asset.setImg( map( assetFormat.getImg() ) );
    asset.setVideo( map( assetFormat.getVideo() ) );
    asset.setData( dataAssetFormatToNativeData( assetFormat.getData() ) );
    Map<String, Object> map = assetFormat.getExt();
    if ( map != null ) {
      asset.setExt( new HashMap<String, Object>( map ) );
    }

    return asset;
  }

  public ImageAssetFormat map(NativeImage nativeImage) {
    if ( nativeImage == null ) {
      return null;
    }

    ImageAssetFormat imageAssetFormat = new ImageAssetFormat();

    List<String> list = nativeImage.getMimes();
    if ( list != null ) {
      imageAssetFormat.setMime( new ArrayList<String>( list ) );
    }
    imageAssetFormat.setType( nativeImage.getType() );
    imageAssetFormat.setW( nativeImage.getW() );
    imageAssetFormat.setH( nativeImage.getH() );
    imageAssetFormat.setWmin( nativeImage.getWmin() );
    imageAssetFormat.setHmin( nativeImage.getHmin() );
    Map<String, Object> map = nativeImage.getExt();
    if ( map != null ) {
      imageAssetFormat.setExt( new HashMap<String, Object>( map ) );
    }

    return imageAssetFormat;
  }

  public NativeImage map(ImageAssetFormat imageAssetFormat) {
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
      nativeImage.setExt( new HashMap<String, Object>( map ) );
    }

    return nativeImage;
  }

  public VideoPlacement map(NativeVideo nativeVideo) {
    if ( nativeVideo == null ) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();

    videoPlacement.setMaxdur( nativeVideo.getMaxduration() );
    videoPlacement.setMindur( nativeVideo.getMinduration() );
    Set<Integer> set = nativeVideo.getProtocols();
    if ( set != null ) {
      videoPlacement.setCtype( new HashSet<Integer>( set ) );
    }
    Set<String> set1 = nativeVideo.getMimes();
    if ( set1 != null ) {
      videoPlacement.setMime( new HashSet<String>( set1 ) );
    }
    Map<String, Object> map = nativeVideo.getExt();
    if ( map != null ) {
      videoPlacement.setExt( new HashMap<String, Object>( map ) );
    }

    return videoPlacement;
  }

  public NativeVideo map(VideoPlacement videoPlacement) {
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

  protected List<AssetFormat> assetListToAssetFormatList(List<Asset> list) {
    if ( list == null ) {
      return null;
    }

    List<AssetFormat> list1 = new ArrayList<AssetFormat>( list.size() );
    for ( Asset asset : list ) {
      list1.add( map( asset ) );
    }

    return list1;
  }

  protected List<Asset> assetFormatListToAssetList(List<AssetFormat> list) {
    if ( list == null ) {
      return null;
    }

    List<Asset> list1 = new ArrayList<Asset>( list.size() );
    for ( AssetFormat assetFormat : list ) {
      list1.add( map( assetFormat ) );
    }

    return list1;
  }

  protected TitleAssetFormat nativeTitleToTitleAssetFormat(NativeTitle nativeTitle) {
    if ( nativeTitle == null ) {
      return null;
    }

    TitleAssetFormat titleAssetFormat = new TitleAssetFormat();

    titleAssetFormat.setLen( nativeTitle.getLen() );
    Map<String, Object> map = nativeTitle.getExt();
    if ( map != null ) {
      titleAssetFormat.setExt( new HashMap<String, Object>( map ) );
    }

    return titleAssetFormat;
  }

  protected DataAssetFormat nativeDataToDataAssetFormat(NativeData nativeData) {
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

  protected NativeTitle titleAssetFormatToNativeTitle(TitleAssetFormat titleAssetFormat) {
    if ( titleAssetFormat == null ) {
      return null;
    }

    NativeTitle nativeTitle = new NativeTitle();

    nativeTitle.setLen( titleAssetFormat.getLen() );
    Map<String, Object> map = titleAssetFormat.getExt();
    if ( map != null ) {
      nativeTitle.setExt( new HashMap<String, Object>( map ) );
    }

    return nativeTitle;
  }

  protected NativeData dataAssetFormatToNativeData(DataAssetFormat dataAssetFormat) {
    if ( dataAssetFormat == null ) {
      return null;
    }

    NativeData nativeData = new NativeData();

    nativeData.setType( dataAssetFormat.getType() );
    nativeData.setLen( dataAssetFormat.getLen() );
    Map<String, Object> map = dataAssetFormat.getExt();
    if ( map != null ) {
      nativeData.setExt( new HashMap<String, Object>( map ) );
    }

    return nativeData;
  }
}
