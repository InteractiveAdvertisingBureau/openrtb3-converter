package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;

/**
 * Created by shiva.b on 03/01/19.
 */
public class Bid24ToBid30Converter implements Converter<Bid, net.media.openrtb3.Bid> {
  @Override
  public net.media.openrtb3.Bid map(Bid source, Config config) {
    return null;
  }

  @Override
  public void inhance(Bid source, net.media.openrtb3.Bid target, Config config) {

  }
}
