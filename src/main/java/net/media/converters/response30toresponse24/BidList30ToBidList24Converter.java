package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Seatbid;

import java.util.List;

public class BidList30ToBidList24Converter implements Converter<List<Bid>,List<net.media.openrtb24.response.Bid>> {

  @Override
  public List<net.media.openrtb24.response.Bid> map(List<Bid> source, Config config) {
    return null;
  }

  @Override
  public void inhance(List<Bid> source, List<net.media.openrtb24.response.Bid> target, Config config) {

  }
}
