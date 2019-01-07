package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Deal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class DealToDealConverter implements Converter<Deal, net.media.openrtb24.request.Deal> {
  @Override
  public net.media.openrtb24.request.Deal map(Deal deal, Config config) {
    if ( deal == null ) {
      return null;
    }
    net.media.openrtb24.request.Deal deal1 = new net.media.openrtb24.request.Deal();
    inhance(deal, deal1, config);
    return deal1;
  }

  @Override
  public void inhance(Deal deal, net.media.openrtb24.request.Deal deal1, Config config) {
    if (isNull(deal) || isNull(deal1)) {
      return;
    }
    deal1.setBidFloorCur( deal.getFlrcur() );
    if ( deal.getFlr() != null ) {
      deal1.setBidFloor( deal.getFlr() );
    }
    deal1.setId( deal.getId() );
    deal1.setAt( deal.getAt() );
    List<String> list = deal.getWseat();
    if ( list != null ) {
      deal1.setWseat( new ArrayList<>( list ) );
    }
    deal1.setWadomain(deal.getWadomain());
    Map<String, Object> map = deal.getExt();
    if ( map != null ) {
      deal1.setExt( new HashMap<>( map ) );
    }
  }
}
