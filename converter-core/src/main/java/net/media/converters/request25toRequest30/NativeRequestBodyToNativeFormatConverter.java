package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Asset;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.NativeFormat;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class NativeRequestBodyToNativeFormatConverter implements Converter<NativeRequestBody,
  NativeFormat> {

  private Converter<Asset, AssetFormat> assetAssetFormatConverter;

  @java.beans.ConstructorProperties({"assetAssetFormatConverter"})
  public NativeRequestBodyToNativeFormatConverter(Converter<Asset, AssetFormat> assetAssetFormatConverter) {
    this.assetAssetFormatConverter = assetAssetFormatConverter;
  }

  @Override
  public NativeFormat map(NativeRequestBody nativeRequestBody, Config config) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return null;
    }
    NativeFormat nativeFormat = new NativeFormat();
    enhance(nativeRequestBody, nativeFormat, config);
    return nativeFormat;
  }

  @Override
  public void enhance(NativeRequestBody nativeRequestBody, NativeFormat nativeFormat, Config
    config) throws OpenRtbConverterException {
    if ( nativeRequestBody == null ) {
      return;
    }

    nativeFormat.setExt(Utils.copyMap(nativeRequestBody.getExt(), config));
    if (nonNull(nativeRequestBody.getContextsubtype())) {
      if (isNull(nativeFormat.getExt())) {
        nativeFormat.setExt(new HashMap<>());
      }
      nativeFormat.getExt().put("contextsubtype", nativeRequestBody.getContextsubtype());
    }
    if (nonNull(nativeRequestBody.getAdunit())) {
      if (isNull(nativeFormat.getExt())) {
        nativeFormat.setExt(new HashMap<>());
      }
      nativeFormat.getExt().put("adunit", nativeRequestBody.getAdunit());
    }
    if (nonNull(nativeRequestBody.getLayout())) {
      if (isNull(nativeFormat.getExt())) {
        nativeFormat.setExt(new HashMap<>());
      }
      nativeFormat.getExt().put("layout", nativeRequestBody.getLayout());
    }
    if (nonNull(nativeRequestBody.getVer())) {
      if (isNull(nativeFormat.getExt())) {
        nativeFormat.setExt(new HashMap<>());
      }
      nativeFormat.getExt().put("ver", nativeRequestBody.getVer());
    }
    nativeFormat.setAsset( CollectionToCollectionConverter.convert( nativeRequestBody.getAssets(),  assetAssetFormatConverter, config ) );

  }


}
