package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Deal;
import net.media.utils.Utils;

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
    deal1.setWseat(Utils.copyList(deal.getWseat(), config));
    deal1.setWadomain(deal.getWadomain());
    deal1.setExt(Utils.copyMap(deal.getExt(), config));
  }
}
