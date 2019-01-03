package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Seatbid;

import java.util.List;

public class SeatBidList30ToSeatBidList24Converter implements Converter<List<Seatbid>,List<SeatBid>> {

  @Override
  public List<SeatBid> map(List<Seatbid> source, Config config) {
    return null;
  }

  @Override
  public void inhance(List<Seatbid> source,List<SeatBid> target, Config config) {

  }
}
