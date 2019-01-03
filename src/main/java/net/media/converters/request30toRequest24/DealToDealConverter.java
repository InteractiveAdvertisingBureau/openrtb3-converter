package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Deal;

public class DealToDealConverter implements Converter<Deal, net.media.openrtb24.request.Deal> {
  @Override
  public net.media.openrtb24.request.Deal map(Deal source, Config config) {
    return null;
  }

  @Override
  public void inhance(Deal source, net.media.openrtb24.request.Deal target, Config config) {

  }
}
