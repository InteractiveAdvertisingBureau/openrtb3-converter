package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class BannerToCompanionConverter implements Converter<Banner, Companion> {

  private Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter;

  public BannerToCompanionConverter(Converter<Banner, DisplayPlacement>
                                      bannerDisplayPlacementConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
  }

  @Override
  public Companion map(Banner source, Config config) {
    return null;
  }

  @Override
  public void inhance(Banner source, Companion target, Config config) {

  }
}
