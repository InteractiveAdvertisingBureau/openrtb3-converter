package net.media.converters.request30toRequest25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class CompanionToBannerConverter implements Converter<Companion, Banner> {

  private Converter<DisplayPlacement, Banner> displayPlacementBannerConverter;

  @Override
  public Banner map(Companion companion, Config config) throws OpenRtbConverterException {
    if ( companion == null ) {
      return null;
    }
    Banner banner = new Banner();
    inhance(companion, banner, config);
    return banner;
  }

  @Override
  public void inhance(Companion companion, Banner banner, Config config) throws OpenRtbConverterException {
    if (isNull(companion) || isNull(banner)) {
      return;
    }
    displayPlacementBannerConverter.inhance(companion.getDisplay(), banner, config);
    banner.setVcm( companion.getVcm() );
    banner.setId( companion.getId() );
    Map<String, Object> map = companion.getExt();
    if ( map != null ) {
      banner.setExt( new HashMap<>( map ) );
    }
  }
}
