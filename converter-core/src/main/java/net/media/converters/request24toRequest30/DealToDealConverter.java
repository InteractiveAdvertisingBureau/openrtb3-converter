package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Deal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajat.go on 03/01/19.
 */
public class DealToDealConverter implements Converter<Deal, net.media.openrtb3.Deal> {
  @Override
  public net.media.openrtb3.Deal map(Deal deal, Config config) {
    if ( deal == null ) {
      return null;
    }

    net.media.openrtb3.Deal deal1 = new net.media.openrtb3.Deal();
    inhance(deal, deal1, config);
    return deal1;
  }

  @Override
  public void inhance(Deal deal, net.media.openrtb3.Deal deal1, Config config) {
    if ( deal == null ) {
      return;
    }
    deal1.setFlrcur( deal.getBidFloorCur() );
    deal1.setFlr( deal.getBidFloor() );
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
