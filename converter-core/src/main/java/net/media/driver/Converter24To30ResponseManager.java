package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response24toResponse30.Bid24ToBid30Converter;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Converter24To30ResponseManager {

  public Converter24To30ResponseManager(Provider<Conversion, Converter> converterProvider) {
    converterProvider.register(new Conversion(Bid.class, net.media.openrtb3.Bid.class), new
      Bid24ToBid30Converter());
  }
}
