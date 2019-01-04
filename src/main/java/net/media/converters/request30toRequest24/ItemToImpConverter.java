package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.Item;

public class ItemToImpConverter implements Converter<Item, Imp> {

  @Override
  public Imp map(Item source, Config config) {
    return null;
  }

  @Override
  public void inhance(Item source, Imp target, Config config) {

  }
}
