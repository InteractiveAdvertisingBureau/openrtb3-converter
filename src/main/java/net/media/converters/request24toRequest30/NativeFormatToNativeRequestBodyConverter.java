package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.NativeFormat;

/**
 * Created by rajat.go on 03/01/19.
 */
public class NativeFormatToNativeRequestBodyConverter implements Converter<NativeFormat,
  NativeRequestBody> {
  @Override
  public NativeRequestBody map(NativeFormat source, Config config) {
    return null;
  }

  @Override
  public void inhance(NativeFormat source, NativeRequestBody target, Config config) {

  }
}
