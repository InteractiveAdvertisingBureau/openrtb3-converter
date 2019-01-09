package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.User;
import net.media.openrtb3.Request;

public class RequestToUserConverter implements Converter<Request, User> {
  @Override
  public User map(Request source, Config config) {
    return null;
  }

  @Override
  public void inhance(Request source, User target, Config config) {
    if ( source == null ) {
      return;
    }

    target.setCustomdata( source.getCdata() );
  }
}
