package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Seatbid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shiva.b
 */
public class SeatBidList24ToSeatBidList30Converter implements Converter<List<SeatBid>, List<Seatbid>> {

  private Converter<SeatBid, Seatbid> seatBid24ToSeatBid30Converter;

  public SeatBidList24ToSeatBidList30Converter(Converter<SeatBid, Seatbid> seatBid24ToSeatBid30Converter) {
    this.seatBid24ToSeatBid30Converter = seatBid24ToSeatBid30Converter;
  }

  @Override
  public List<Seatbid> map(List<SeatBid> list, Config config) {
    if ( list == null ) {
      return null;
    }
    List<Seatbid> list1 = new ArrayList<>(list.size());
    inhance(list, list1, config);
    return list1;
  }

  @Override
  public void inhance(List<SeatBid> sourceList, List<Seatbid> targetList, Config config) {
    if (sourceList == null || targetList == null) {
      return;
    }
    for ( SeatBid seatBid : sourceList ) {
      targetList.add(seatBid24ToSeatBid30Converter.map(seatBid, config));
    }
  }
}
