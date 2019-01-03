package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.mapper.DisplayConverter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.DisplayPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class BannerToDisplayPlacementConverter implements Converter<Banner, DisplayPlacement> {

  @Override
  public DisplayPlacement map(Banner source, Config config) {
    return null;
  }

  @Override
  public void inhance(Banner source, DisplayPlacement target, Config config) {

  }
}
