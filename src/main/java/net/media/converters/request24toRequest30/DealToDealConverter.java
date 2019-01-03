package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Deal;

/**
 * Created by rajat.go on 03/01/19.
 */
public class DealToDealConverter implements Converter<Deal, net.media.openrtb3.Deal> {
  @Override
  public net.media.openrtb3.Deal map(Deal source, Config config) {
    return null;
  }

  @Override
  public void inhance(Deal source, net.media.openrtb3.Deal target, Config config) {

  }
}
