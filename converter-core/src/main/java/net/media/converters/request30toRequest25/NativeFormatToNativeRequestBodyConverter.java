package net.media.converters.request30toRequest25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.ListToListConverter;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class NativeFormatToNativeRequestBodyConverter implements Converter<NativeFormat,
  NativeRequestBody> {

  private Converter<AssetFormat, Asset> assetFormatAssetConverter;

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
    nativeRequestBody.setAssets( ListToListConverter.convert( nativeFormat.getAsset(), assetFormatAssetConverter, config ) );

  }

}
