package net.media.converters.response24toresponse30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;

import net.media.openrtb24.response.nativeresponse.AssetResponse;
import net.media.openrtb24.response.nativeresponse.Link;
import net.media.openrtb24.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Asset;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Native;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author shiva.b
 */
public class Native24ToNative30Converter implements Converter<NativeResponse, Native> {

  private Converter<Link, LinkAsset> linkLinkAssetConverter;

  private Converter<AssetResponse, Asset> assetResponseAssetConverter;

  public Native24ToNative30Converter(Converter<Link, LinkAsset> linkLinkAssetConverter,
                                     Converter<AssetResponse, Asset> assetResponseAssetConverter) {
    this.linkLinkAssetConverter = linkLinkAssetConverter;
    this.assetResponseAssetConverter = assetResponseAssetConverter;
  }

  @Override
  public Native map(NativeResponse source, Config config) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Native _native = new Native();
    inhance(source, _native, config);
    return _native;
  }

  @Override
  public void inhance(NativeResponse source, Native target, Config config)throws OpenRtbConverterException {
    if (source == null || target == null || source.getNativeResponseBody() == null) {
      return;
    }
    if (isNull(target.getExt())) {
      target.setExt(new HashMap<>());
    }
    target.getExt().put("jsTracker", source.getNativeResponseBody().getJstracker());
    target.getExt().put("impTrackers", source.getNativeResponseBody().getImptrackers());
    linkLinkAssetConverter.map(source.getNativeResponseBody().getLink(), config);
    if (!isEmpty(source.getNativeResponseBody().getAssets())) {
      List<Asset> assetList = new ArrayList<>();
      for(AssetResponse assetResponse : source.getNativeResponseBody().getAssets()) {
        Asset asset = assetResponseAssetConverter.map(assetResponse, config);
        if(isNull(asset)) {
          assetList.add(asset);
        }
      });
      target.setAsset(assetList);
    }
  }
}
