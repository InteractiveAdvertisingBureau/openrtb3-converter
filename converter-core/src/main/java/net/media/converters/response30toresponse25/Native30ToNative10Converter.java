package net.media.converters.response30toresponse25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb25.response.nativeresponse.NativeResponseBody;
import net.media.openrtb3.Asset;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Native;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Native30ToNative10Converter implements Converter<Native,NativeResponse> {

  Converter<Asset,AssetResponse> assetAssetResponseConverter;
  Converter<LinkAsset,Link> linkAssetLinkConverter;
  public Native30ToNative10Converter(Converter<Asset,AssetResponse> assetAssetResponseConverter,Converter<LinkAsset,Link> linkAssetLinkConverter) {
    this.assetAssetResponseConverter = assetAssetResponseConverter;
    this.linkAssetLinkConverter = linkAssetLinkConverter;
  }

  public NativeResponse map(Native source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    NativeResponse  nativeResponse = new NativeResponse();
    NativeResponseBody nativeResponseBody = new NativeResponseBody();
    nativeResponse.setNativeResponseBody(nativeResponseBody);
    enhance(source,nativeResponse,config);
    return nativeResponse;
  }

  public void enhance(Native source, NativeResponse target, Config config) throws OpenRtbConverterException  {

    if(isNull(source) || isNull(target) || isNull(config))
      return;

    NativeResponseBody nativeResponseBody = target.getNativeResponseBody();
    List<AssetResponse> assetResponseList = new ArrayList<>();
    if(nonNull(source.getAsset())){
      for (Asset asset : source.getAsset()) {
        assetResponseList.add(assetAssetResponseConverter.map(asset,config));
      }
    }
    nativeResponseBody.setAssets(assetResponseList);
    nativeResponseBody.setLink(linkAssetLinkConverter.map(source.getLink(),config));
    nativeResponseBody.setExt(source.getExt());
    try {
      if (nonNull(source.getExt())) {
        nativeResponseBody.setJstracker((String) source.getExt().get("_jsTracker"));
        nativeResponseBody.setImptrackers((List<String>) source.getExt().get("impTrackers"));
      }
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext objects in native", e);
    }
    target.setNativeResponseBody(nativeResponseBody);
  }
}