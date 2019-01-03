package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;

/**
 * Created by rajat.go on 03/01/19.
 */
public class BidRequestToRequestConverter implements Converter<BidRequest, Request> {

  Converter<Imp, Item> impItemConverter;

  public BidRequestToRequestConverter(Converter<Imp, Item> impItemConverter) {
    this.impItemConverter = impItemConverter;
  }

  @Override
  public Request map(BidRequest source, Config config) {
    return null;
  }

  @Override
  public void inhance(BidRequest source, Request target, Config config) {

  }
}
