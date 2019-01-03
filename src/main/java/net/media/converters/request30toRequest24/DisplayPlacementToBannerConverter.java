package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Banner;
import net.media.openrtb3.DisplayPlacement;

public class DisplayPlacementToBannerConverter implements Converter<DisplayPlacement, Banner> {
  @Override
  public Banner map(DisplayPlacement source, Config config) {
    return null;
  }

  @Override
  public void inhance(DisplayPlacement source, Banner target, Config config) {

  }
}
