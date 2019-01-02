package net.media.converters;

import net.media.config.Config;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.OpenRTB;

/**
 * Created by shiva.b on 02/01/19.
 */
public class BidResponseToOpenRtbConverter implements Converter<BidResponse, OpenRTB> {
  @Override
  public OpenRTB map(BidResponse source, Config config) {
    return null;
  }

  @Override
  public void inhance(BidResponse source, OpenRTB target) {

  }
}
