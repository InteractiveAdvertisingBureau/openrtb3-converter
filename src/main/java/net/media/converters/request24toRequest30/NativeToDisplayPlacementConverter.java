package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Native;
import net.media.openrtb3.DisplayPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class NativeToDisplayPlacementConverter implements Converter<Native, DisplayPlacement> {
  @Override
  public DisplayPlacement map(Native source, Config config) {
    return null;
  }

  @Override
  public void inhance(Native source, DisplayPlacement target, Config config) {

  }
}
