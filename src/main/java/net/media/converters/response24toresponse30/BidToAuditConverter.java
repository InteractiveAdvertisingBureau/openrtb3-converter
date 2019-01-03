package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audit;

/**
 * Created by shiva.b on 03/01/19.
 */
public class BidToAuditConverter implements Converter<Bid, Audit> {
  @Override
  public Audit map(Bid source, Config config) {
    return null;
  }

  @Override
  public void inhance(Bid source, Audit target, Config config) {

  }
}
