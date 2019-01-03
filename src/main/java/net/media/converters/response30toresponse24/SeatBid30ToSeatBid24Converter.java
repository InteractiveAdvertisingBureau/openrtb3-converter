package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Seatbid;

public class SeatBid30ToSeatBid24Converter implements Converter<Seatbid,SeatBid>{
  @Override
  public SeatBid map(Seatbid source, Config config) {
    return null;
  }

  @Override
  public void inhance(Seatbid source,SeatBid target, Config config) {

  }
}
