package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response24toResponse30.Bid24ToBid30Converter;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Convert24To30ResponseManager {

  public Convert24To30ResponseManager(Provider converterProvider) {
    converterProvider.register(new Conversion<>(Bid.class, net.media.openrtb3.Bid.class), new
      Bid24ToBid30Converter());
  }
}
