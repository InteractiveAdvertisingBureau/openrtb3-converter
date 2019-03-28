package net.media.converters.response25toresponse30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Seatbid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author shiva.b
 */
public class SeatBid24ToSeatBid30Converter implements Converter<SeatBid, Seatbid> {

  private Converter<Bid, net.media.openrtb3.Bid> bid24ToBid30Converter;

  public SeatBid24ToSeatBid30Converter(Converter<Bid, net.media.openrtb3.Bid> bid24ToBid30Converter) {
    this.bid24ToBid30Converter = bid24ToBid30Converter;
  }

  @Override
  public Seatbid map(SeatBid source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }
    Seatbid seatbid = new Seatbid();
    enhance(source, seatbid, config);
    return seatbid;
  }

  @Override
  public void enhance(SeatBid source, Seatbid seatbid, Config config)throws OpenRtbConverterException {
    if (source == null || seatbid == null) {
      return;
    }

    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      seatbid.setExt(new HashMap<>(map) );
    }
    else {
      seatbid.setExt(null);
    }
    seatbid.set_package(source.getGroup());
    seatbid.setSeat(source.getSeat());
    if (!isEmpty(source.getBid())) {
      List<net.media.openrtb3.Bid> bidList = new ArrayList<>();
      for (Bid bid : source.getBid()) {
        net.media.openrtb3.Bid bid30 = bid24ToBid30Converter.map(bid, config);
        if (nonNull(bid)) {
          bidList.add(bid30);
        }
      }
      seatbid.setBid(bidList);
    }
  }
}
