package net.media.converters.request30toRequest25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class NativeFormatToNativeRequestBodyConverter implements Converter<NativeFormat,
  NativeRequestBody> {

  private Converter<AssetFormat, Asset> assetFormatAssetConverter;

  @java.beans.ConstructorProperties({"assetFormatAssetConverter"})
  public NativeFormatToNativeRequestBodyConverter(Converter<AssetFormat, Asset> assetFormatAssetConverter) {
    this.assetFormatAssetConverter = assetFormatAssetConverter;
  }

  @Override
  public NativeRequestBody map(NativeFormat nativeFormat, Config config) throws OpenRtbConverterException {
    if ( nativeFormat == null ) {
      return null;
    }
    NativeRequestBody nativeRequestBody = new NativeRequestBody();
    enhance(nativeFormat, nativeRequestBody, config);
    return nativeRequestBody;
  }

  @Override
  public void enhance(NativeFormat nativeFormat, NativeRequestBody nativeRequestBody, Config config) throws OpenRtbConverterException {
    if (isNull(nativeFormat) || isNull(nativeRequestBody)) {
      return;
    }
    nativeRequestBody.setExt(Utils.copyMap(nativeFormat.getExt(), config));
    nativeRequestBody.setAssets( assetFormatListToAssetList( nativeFormat.getAsset(), config ) );

  }

  private List<Asset> assetFormatListToAssetList(List<AssetFormat> list, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<Asset> list1 = new ArrayList<Asset>( list.size() );
    for ( AssetFormat assetFormat : list ) {
      list1.add( assetFormatAssetConverter.map( assetFormat, config ) );
    }

    return list1;
  }
}
