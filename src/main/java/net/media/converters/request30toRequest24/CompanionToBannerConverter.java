package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Companion;

public class CompanionToBannerConverter implements Converter<Companion, Banner> {
  @Override
  public Banner map(Companion source, Config config) {
    return null;
  }

  @Override
  public void inhance(Companion source, Banner target, Config config) {

  }
}
