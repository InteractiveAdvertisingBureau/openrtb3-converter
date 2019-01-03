package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;

public class Bid30ToBid24Converter  implements Converter<net.media.openrtb3.Bid, Bid> {
  @Override
  public Bid map(net.media.openrtb3.Bid source, Config config) {
    return null;
  }

  @Override
  public void inhance(net.media.openrtb3.Bid source,Bid target, Config config) {

  }
}
