package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Seatbid;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author shiva.b
 */
public class SeatBid25ToSeatBid30Converter implements Converter<SeatBid, Seatbid> {

  @Override
  public Seatbid map(SeatBid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }
    Seatbid seatbid = new Seatbid();
    enhance(source, seatbid, config, converterProvider);
    return seatbid;
  }

  @Override
  public void enhance(SeatBid source, Seatbid seatbid, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (source == null || seatbid == null) {
      return;
    }

    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      seatbid.setExt(Utils.copyMap(map, config));
    }
    else {
      seatbid.setExt(null);
    }
    seatbid.set_package(source.getGroup());
    seatbid.setSeat(source.getSeat());
    Converter<Bid, net.media.openrtb3.Bid> bid25ToBid30Converter = converterProvider.fetch(new Conversion(Bid.class, net.media.openrtb3.Bid.class));
    if (!isEmpty(source.getBid())) {
      List<net.media.openrtb3.Bid> bidList = new ArrayList<>();
      for (Bid bid : source.getBid()) {
        net.media.openrtb3.Bid bid30 = bid25ToBid30Converter.map(bid, config, converterProvider);
        if (nonNull(bid)) {
          bidList.add(bid30);
        }
      }
      seatbid.setBid(bidList);
    }
  }
}
