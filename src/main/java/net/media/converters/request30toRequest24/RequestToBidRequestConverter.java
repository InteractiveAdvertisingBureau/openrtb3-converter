package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.Request;

public class RequestToBidRequestConverter implements Converter<Request, BidRequest> {
  @Override
  public BidRequest map(Request source, Config config) {
    return null;
  }

  @Override
  public void inhance(Request source, BidRequest target, Config config) {

  }
}
