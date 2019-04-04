package net.media.driver;

import net.media.converters.Converter;
import net.media.converters.response30toResponse23.Bid30ToBid23Converter;
import net.media.openrtb3.Bid;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 04/04/19.
 */

@SuppressWarnings("unchecked")
public class Converter30To23ResponseManager {

  public Converter30To23ResponseManager(Provider<Conversion, Converter> converterProvider) {
    converterProvider.register(new Conversion(Bid.class, net.media.openrtb25.response.Bid.class),
      new Bid30ToBid23Converter());
  }
}
