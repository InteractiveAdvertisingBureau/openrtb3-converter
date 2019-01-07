package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

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
  public Companion map(Banner banner, Config config) {
    if ( banner == null ) {
      return null;
    }

    Companion companion = new Companion();
    inhance(banner, companion, config);

    return companion;
  }

  @Override
  public void inhance(Banner banner, Companion companion, Config config) {
    if (nonNull(banner.getId())) {
      companion.setId(banner.getId());
    }
    companion.setVcm( banner.getVcm() );
    companion.setDisplay( bannerDisplayPlacementConverter.map( banner, config) );
    companion.setId( banner.getId() );
  }
}
