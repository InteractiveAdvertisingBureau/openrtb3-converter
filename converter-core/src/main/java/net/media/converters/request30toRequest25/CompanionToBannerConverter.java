package net.media.converters.request30toRequest25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class CompanionToBannerConverter implements Converter<Companion, Banner> {

  private Converter<DisplayPlacement, Banner> displayPlacementBannerConverter;

  @java.beans.ConstructorProperties({"displayPlacementBannerConverter"})
  public CompanionToBannerConverter(Converter<DisplayPlacement, Banner> displayPlacementBannerConverter) {
    this.displayPlacementBannerConverter = displayPlacementBannerConverter;
  }

  @Override
  public Banner map(Companion companion, Config config) throws OpenRtbConverterException {
    if ( companion == null ) {
      return null;
    }
    Banner banner = new Banner();
    enhance(companion, banner, config);
    return banner;
  }

  @Override
  public void enhance(Companion companion, Banner banner, Config config) throws OpenRtbConverterException {
    if (isNull(companion) || isNull(banner)) {
      return;
    }
    displayPlacementBannerConverter.enhance(companion.getDisplay(), banner, config);
    banner.setVcm( companion.getVcm() );
    banner.setId( companion.getId() );
    Map<String, Object> map = companion.getExt();
    if ( map != null ) {
      banner.setExt(Utils.copyMap(map, config));
    }
  }
}
