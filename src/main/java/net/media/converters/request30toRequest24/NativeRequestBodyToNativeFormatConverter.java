package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.NativeFormat;

public class NativeRequestBodyToNativeFormatConverter implements Converter<NativeRequestBody, NativeFormat> {
  @Override
  public NativeFormat map(NativeRequestBody source, Config config) {
    return null;
  }

  @Override
  public void inhance(NativeRequestBody source, NativeFormat target, Config config) {

  }
}
