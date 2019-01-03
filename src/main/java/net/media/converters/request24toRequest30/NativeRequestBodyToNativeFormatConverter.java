package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.NativeFormat;

/**
 * Created by rajat.go on 03/01/19.
 */
public class NativeRequestBodyToNativeFormatConverter implements Converter<NativeRequestBody,
  NativeFormat> {

  @Override
  public NativeFormat map(NativeRequestBody source, Config config) {
    return null;
  }

  @Override
  public void inhance(NativeRequestBody source, NativeFormat target, Config config) {

  }
}
