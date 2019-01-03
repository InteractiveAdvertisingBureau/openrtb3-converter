package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;

public class OpenRtbToRequestConverter implements Converter<OpenRTB, Request> {
  @Override
  public Request map(OpenRTB source, Config config) {
    return null;
  }

  @Override
  public void inhance(OpenRTB source, Request target, Config config) {

  }
}
