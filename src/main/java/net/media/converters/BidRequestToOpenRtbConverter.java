package net.media.converters;

import net.media.OpenRtbConverter;
import net.media.config.Config;
import net.media.openrtb24.request.BidRequest;

/**
 * Created by shiva.b on 02/01/19.
 */
public class BidRequestToOpenRtbConverter implements Converter<BidRequest, OpenRtbConverter> {
  @Override
  public OpenRtbConverter map(BidRequest source, Config config) {
    return null;
  }

  @Override
  public void inhance(BidRequest source, OpenRtbConverter target) {

  }
}
