package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rajat.go on 03/01/19.
 */

public class NativeRequestBodyToNativeFormatConverter implements Converter<NativeRequestBody,
  NativeFormat> {

  @Override
  public NativeFormat map(NativeRequestBody nativeRequestBody, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return null;
    }
    NativeFormat nativeFormat = new NativeFormat();
    enhance(nativeRequestBody, nativeFormat, config, converterProvider);
    return nativeFormat;
  }

  @Override
  public void enhance(NativeRequestBody nativeRequestBody, NativeFormat nativeFormat, Config
    config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return;
    }
    Converter<Asset, AssetFormat> assetAssetFormatConverter = converterProvider.fetch(new Conversion
            (Asset.class, AssetFormat.class));
    nativeFormat.setExt(Utils.copyMap(nativeRequestBody.getExt(), config));
    nativeFormat.setAsset( CollectionToCollectionConverter.convert( nativeRequestBody.getAssets(),  assetAssetFormatConverter, config ) );

  }


}
