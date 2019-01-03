package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;

/**
 * Created by rajat.go on 03/01/19.
 */
public class NativeToDisplayPlacementConverter implements Converter<Native, DisplayPlacement> {

  private Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter;

  public NativeToDisplayPlacementConverter(Converter<NativeRequestBody, NativeFormat>
                                             nativeRequestBodyNativeFormatConverter) {
    this.nativeRequestBodyNativeFormatConverter = nativeRequestBodyNativeFormatConverter;
  }

  @Override
  public DisplayPlacement map(Native source, Config config) {
    return null;
  }

  @Override
  public void inhance(Native source, DisplayPlacement target, Config config) {

  }
}
