package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class BannerToCompanionConverter implements Converter<Banner, Companion> {

  @Override
  public Companion map(Banner banner, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( banner == null ) {
      return null;
    }

    Companion companion = new Companion();
    enhance(banner, companion, config, converterProvider);

    return companion;
  }

  @Override
  public void enhance(Banner banner, Companion companion, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(banner == null || companion == null) {
      return;
    }
    if (nonNull(banner.getId())) {
      companion.setId(banner.getId());
    }
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter = converterProvider.fetch(new Conversion
            (Banner.class, DisplayPlacement.class));
    companion.setVcm( banner.getVcm() );
    companion.setDisplay( bannerDisplayPlacementConverter.map( banner, config, converterProvider) );
    companion.setId( banner.getId() );
  }
}
