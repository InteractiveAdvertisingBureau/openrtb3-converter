package net.media.converters.response25toresponse30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeData;
import net.media.openrtb25.response.nativeresponse.NativeImage;
import net.media.openrtb25.response.nativeresponse.NativeTitle;
import net.media.openrtb25.response.nativeresponse.NativeVideo;
import net.media.openrtb3.Asset;
import net.media.openrtb3.DataAsset;
import net.media.openrtb3.ImageAsset;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.TitleAsset;
import net.media.openrtb3.VideoAsset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class Asset25ToAsset30Converter implements Converter<AssetResponse, Asset> {

  private Converter<Link, LinkAsset> linkLinkAssetConverter;

  public Asset25ToAsset30Converter(Converter<Link, LinkAsset> linkLinkAssetConverter) {
    this.linkLinkAssetConverter = linkLinkAssetConverter;
  }

  @Override
  public Asset map(AssetResponse source, Config config)throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Asset asset = new Asset();
    enhance(source, asset, config);
    return asset;
  }

  @Override
  public void enhance(AssetResponse source, Asset target, Config config) throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setData(nativeDataToData(source.getData()));
    target.setId(source.getId());
    target.setReq(source.getRequired());
    target.setImage(nativeImageToImageAsset(source.getImg()));
    target.setTitle(nativeTittleToTittleAsset(source.getTitle()));
    target.setVideo(nativeVideoToVideoAsset(source.getVideo()));
    target.setLink(linkLinkAssetConverter.map(source.getLink(), config));
  }

  private DataAsset nativeDataToData(NativeData nativeData) throws OpenRtbConverterException {
    if(isNull(nativeData))
      return null;
    DataAsset dataAsset = new DataAsset();
    dataAsset.setExt(nativeData.getExt());
    List<String> value = new ArrayList<>();
    value.add(nativeData.getValue());
    dataAsset.setValue(value);
    try {
      if (nonNull(nativeData.getExt())) {
        dataAsset.setType((Integer) nativeData.getExt().get("type"));
        dataAsset.setLen((Integer) nativeData.getExt().get("len"));
      }
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while typecasting ext for nativeData", e);
    }
    if(isNull(dataAsset.getExt()))
      dataAsset.setExt(new HashMap<>());
    dataAsset.getExt().put("label",nativeData.getLabel());
    return new DataAsset();
  }

  private ImageAsset nativeImageToImageAsset(NativeImage nativeImage) throws OpenRtbConverterException {
    if(isNull(nativeImage)){
      return null;
    }
    ImageAsset imageAsset = new ImageAsset();
    imageAsset.setH(nativeImage.getH());
    imageAsset.setW(nativeImage.getW());
    imageAsset.setUrl(nativeImage.getUrl());
    imageAsset.setExt(nativeImage.getExt());
    try {
      if (nonNull(nativeImage.getExt()))
        imageAsset.setType((Integer) nativeImage.getExt().get("type"));
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext for image asset" ,e);
    }
    return new ImageAsset();
  }

  private TitleAsset nativeTittleToTittleAsset(NativeTitle nativeTitle) throws OpenRtbConverterException {
    if(isNull(nativeTitle))
      return null;
    TitleAsset titleAsset = new TitleAsset();
    titleAsset.setExt(nativeTitle.getExt());
    titleAsset.setText(nativeTitle.getText());
    try {
      if (nonNull(nativeTitle.getExt()))
        titleAsset.setLen((Integer) nativeTitle.getExt().get("len"));
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext for title asset", e);
    }
    return new TitleAsset();
  }

  private VideoAsset nativeVideoToVideoAsset(NativeVideo nativeVideo) throws OpenRtbConverterException {
    if(isNull(nativeVideo))
      return null;
    VideoAsset  videoAsset = new VideoAsset();
    videoAsset.setAdm(nativeVideo.getVasttag());
    videoAsset.setExt(nativeVideo.getExt());
    try {
      if (nonNull(nativeVideo.getExt()))
        videoAsset.setCurl((String) nativeVideo.getExt().get("curl"));
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while casting ext for videoAsset", e);
    }
    return new VideoAsset();
  }
}
