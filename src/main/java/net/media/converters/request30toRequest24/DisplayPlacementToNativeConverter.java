package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.mapper.DisplayConverter;
import net.media.openrtb24.request.Native;

public class DisplayPlacementToNativeConverter implements Converter<DisplayConverter, Native> {
  @Override
  public Native map(DisplayConverter source, Config config) {
    return null;
  }

  @Override
  public void inhance(DisplayConverter source, Native target, Config config) {

  }
}
