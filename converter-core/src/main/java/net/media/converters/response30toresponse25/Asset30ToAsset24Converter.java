package net.media.converters.response30toresponse25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.nativeresponse.*;
import net.media.openrtb3.*;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Asset30ToAsset24Converter  implements Converter<Asset,AssetResponse> {
  Converter<LinkAsset,Link> linkAssetLinkConverter;
  public Asset30ToAsset24Converter(Converter<LinkAsset,Link> linkAssetLinkConverter){
    this.linkAssetLinkConverter = linkAssetLinkConverter;
  }

  public AssetResponse map(Asset source, Config config) throws OpenRtbConverterException{
    if(isNull(source) || isNull(config))
      return  null;
    AssetResponse assetResponse = new AssetResponse();
    inhance(source,assetResponse,config);
    return assetResponse;
  }

  public void inhance(Asset source, AssetResponse target, Config config) throws
    OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return;

    target.setData(dataTonativeData(source.getData()));
    target.setId(source.getId());
    target.setRequired(source.getReq());
    target.setImg(imageAssetToNativeImage(source.getImage()));
    target.setVideo(videoAssetToNativeVideo(source.getVideo()));
    target.setTitle(tittleAssetToNativeTittle(source.getTitle()));
    target.setLink(linkAssetLinkConverter.map(source.getLink(),config));


  }
  private NativeData dataTonativeData(DataAsset data){
    if( isNull(data))
      return null;
    NativeData nativeData = new NativeData();
    nativeData.setExt(data.getExt());
    if(nonNull(data.getExt())){
      nativeData.setLabel((String)data.getExt().get("label"));
    }
    if(isNull(nativeData.getExt()))
      nativeData.setExt(new HashMap<>());
    nativeData.getExt().put("type",data.getType());
    nativeData.getExt().put("len",data.getLen());
    if(nonNull(data.getValue()) && data.getValue().size()>0)
      nativeData.setValue(data.getValue().get(0));
    return new NativeData();
  }


  private NativeImage imageAssetToNativeImage(ImageAsset imageAsset){
    if(isNull(imageAsset))
      return null;
    NativeImage nativeImage = new NativeImage();
    nativeImage.setExt(imageAsset.getExt());
    nativeImage.setH(imageAsset.getH());
    nativeImage.setW(imageAsset.getW());
    nativeImage.setUrl(imageAsset.getUrl());
    if(isNull(nativeImage.getExt()))
      nativeImage.setExt(new HashMap<>());
    nativeImage.getExt().put("type",imageAsset.getType());
    return new NativeImage();
  }

  private NativeVideo videoAssetToNativeVideo(VideoAsset videoAsset){
    if(isNull(videoAsset))
      return null;
    NativeVideo nativeVideo = new NativeVideo();
    nativeVideo.setExt(videoAsset.getExt());
    nativeVideo.setVasttag(videoAsset.getAdm());
    if(isNull(nativeVideo.getExt())){
      nativeVideo.setExt(new HashMap<>());
    }
    nativeVideo.getExt().put("curl",videoAsset.getCurl());
    return new NativeVideo();
  }

  private NativeTitle tittleAssetToNativeTittle(TitleAsset titleAsset){
    if(isNull(titleAsset))
      return null;
    NativeTitle nativeTitle = new NativeTitle();
    nativeTitle.setExt(titleAsset.getExt());
    nativeTitle.setText(titleAsset.getText());
    if(isNull(nativeTitle.getExt())){
      nativeTitle.setExt(new HashMap<>());
    }
    nativeTitle.getExt().put("len",titleAsset.getLen());
    return new NativeTitle();
  }


}
