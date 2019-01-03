package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audio;

/**
 * @author shiva.b
 */
public class BidToAudioConverter implements Converter<Bid, Audio> {
  @Override
  public Audio map(Bid source, Config config) {
    return null;
  }

  @Override
  public void inhance(Bid source, Audio target, Config config) {

  }
}
