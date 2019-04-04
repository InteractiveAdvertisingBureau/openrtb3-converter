package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;


import static java.util.Objects.isNull;

public class NativeFormatToNativeRequestBodyConverter implements Converter<NativeFormat,
  NativeRequestBody> {

  @Override
  public NativeRequestBody map(NativeFormat nativeFormat, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( nativeFormat == null ) {
      return null;
    }
    NativeRequestBody nativeRequestBody = new NativeRequestBody();
    enhance(nativeFormat, nativeRequestBody, config, converterProvider);
    return nativeRequestBody;
  }

  @Override
  public void enhance(NativeFormat nativeFormat, NativeRequestBody nativeRequestBody, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if (isNull(nativeFormat) || isNull(nativeRequestBody)) {
      return;
    }
    Converter<AssetFormat, Asset> assetFormatAssetConverter = converterProvider.fetch(new Conversion(AssetFormat.class, Asset.class));
    nativeRequestBody.setExt(Utils.copyMap(nativeFormat.getExt(), config));
    nativeRequestBody.setAssets( CollectionToCollectionConverter.convert( nativeFormat.getAsset()
      , assetFormatAssetConverter, config, converterProvider ) );

  }

}
