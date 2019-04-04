package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response30toResponse24.Bid30ToBid24Converter;
import net.media.openrtb3.Bid;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Convert30To24ResponseManager {

  public Convert30To24ResponseManager(Provider converterProvider) {
    converterProvider.register(new Conversion(Bid.class, net.media.openrtb25.response.Bid.class),
      new Bid30ToBid24Converter());
  }
}
