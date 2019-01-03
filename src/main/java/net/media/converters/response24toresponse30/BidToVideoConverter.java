package net.media.converters.response24toresponse30;


import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Video;

/**
 * @author shiva.b
 */
public class BidToVideoConverter implements Converter<Bid, Video> {

  @Override
  public Video map(Bid source, Config config) {
    return null;
  }

  @Override
  public void inhance(Bid source, Video target, Config config) {

  }
}
