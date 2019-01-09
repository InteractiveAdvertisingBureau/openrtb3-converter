package net.media.converters.request25toRequest30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Created by rajat.go on 03/01/19.
 */

@AllArgsConstructor
public class NativeRequestBodyToNativeFormatConverter implements Converter<NativeRequestBody,
  NativeFormat> {

  private Converter<Asset, AssetFormat> assetAssetFormatConverter;

  @Override
  public NativeFormat map(NativeRequestBody nativeRequestBody, Config config) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return null;
    }
    NativeFormat nativeFormat = new NativeFormat();
    inhance(nativeRequestBody, nativeFormat, config);
    return nativeFormat;
  }

  @Override
  public void inhance(NativeRequestBody nativeRequestBody, NativeFormat nativeFormat, Config
    config) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return;
    }

    nativeFormat.setExt(Utils.copyMap(nativeRequestBody.getExt(), config));
    nativeFormat.setAsset( assetListToAssetFormatList( nativeRequestBody.getAssets(), config ) );

  }

  private List<AssetFormat> assetListToAssetFormatList(List<Asset> list, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<AssetFormat> list1 = new ArrayList<>( list.size() );
    for ( Asset asset : list ) {
      list1.add( assetAssetFormatConverter.map( asset, config ) );
    }

    return list1;
  }
}
