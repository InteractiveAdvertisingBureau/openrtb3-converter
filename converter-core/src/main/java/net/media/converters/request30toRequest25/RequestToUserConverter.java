package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.User;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

public class RequestToUserConverter implements Converter<Request, User> {
  @Override
  public User map(Request source, Config config, Provider converterProvider) {
    return null;
  }

  @Override
  public void enhance(Request source, User target, Config config, Provider converterProvider) {
    if ( source == null ) {
      return;
    }

    target.setCustomdata( source.getCdata() );
  }
}
