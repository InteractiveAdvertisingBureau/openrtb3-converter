package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class BannerToCompanionConverter implements Converter<Banner, Companion> {

  private Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter;

  @java.beans.ConstructorProperties({"bannerDisplayPlacementConverter"})
  public BannerToCompanionConverter(Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
  }

  @Override
  public Companion map(Banner banner, Config config) throws OpenRtbConverterException {
    if ( banner == null ) {
      return null;
    }

    Companion companion = new Companion();
    enhance(banner, companion, config);

    return companion;
  }

  @Override
  public void enhance(Banner banner, Companion companion, Config config) throws OpenRtbConverterException {
    if (nonNull(banner.getId())) {
      companion.setId(banner.getId());
    }
    companion.setVcm( banner.getVcm() );
    companion.setDisplay( bannerDisplayPlacementConverter.map( banner, config) );
    companion.setId( banner.getId() );
  }
}
