package net.media.converters;

import net.media.config.Config;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.Request;

/**
 * Created by shiva.b on 02/01/19.
 */
public class BidRequestToRequestConverter implements Converter<BidRequest, Request> {
  @Override
  public Request map(BidRequest source, Config config) {
    return null;
  }

  @Override
  public void inhance(BidRequest source, Request target, Config config) {

  }
}
