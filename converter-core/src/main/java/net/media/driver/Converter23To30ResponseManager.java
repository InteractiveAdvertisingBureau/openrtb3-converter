package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response23toResponse30.Bid23ToBid30Converter;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Converter23To30ResponseManager {

  public Converter23To30ResponseManager(Provider<Conversion, Converter> converterProvider) {
    converterProvider.register(new Conversion(Bid.class, net.media.openrtb3.Bid.class), new
      Bid23ToBid30Converter());
  }
}
