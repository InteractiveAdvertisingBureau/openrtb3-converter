package net.media.mapper;

import net.media.openrtb24.request.Asset;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.NativeImage;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb24.request.NativeVideo;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.ImageAssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.VideoPlacement;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by rajat.go on 27/12/18.
 */

@Mapper
public interface NativeRequestBodyToNativeFormatMapper {

  @Mappings({
    @Mapping(source = "nativeRequestBody.assets", target = "asset"),
    @Mapping(source = "nativeRequestBody.ext", target = "ext"),
  })
  NativeFormat map(NativeRequestBody nativeRequestBody);

  @InheritInverseConfiguration
  NativeRequestBody map(NativeFormat nativeFormat);

  @Mappings({
    @Mapping(source = "required", target = "req"),
  })
  AssetFormat map(Asset asset);

  @InheritInverseConfiguration
  Asset map(AssetFormat assetFormat);

  @Mappings({
    @Mapping(source = "mimes", target = "mime"),
  })
  ImageAssetFormat map(NativeImage nativeImage);

  @InheritInverseConfiguration
  NativeImage map(ImageAssetFormat imageAssetFormat);

  @Mappings({
    @Mapping(source = "nativeVideo.mimes", target = "mime"),
    @Mapping(source = "nativeVideo.minduration", target = "mindur"),
    @Mapping(source = "nativeVideo.maxduration", target = "maxdur"),
    @Mapping(source = "nativeVideo.protocols", target = "ctype"),
  })
  VideoPlacement map(NativeVideo nativeVideo);

  @InheritInverseConfiguration
  NativeVideo map(VideoPlacement videoPlacement);
}
