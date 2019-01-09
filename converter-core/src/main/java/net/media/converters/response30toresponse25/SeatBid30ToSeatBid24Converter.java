package net.media.converters.response30toresponse25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Seatbid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SeatBid30ToSeatBid24Converter implements Converter<Seatbid,SeatBid>{

  Converter<net.media.openrtb3.Bid,Bid> bidBidConverter;
  public SeatBid30ToSeatBid24Converter(Converter<net.media.openrtb3.Bid,Bid> bidBidConverter){
    this.bidBidConverter = bidBidConverter;
  }

  @Override
  public SeatBid map(Seatbid source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    SeatBid  seatBid = new SeatBid();
    enhance(source,seatBid,config);
    return seatBid;
  }

  @Override
  public void enhance(Seatbid source, SeatBid target, Config config)  throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return;


    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
    else {
      target.setExt( null );
    }
    target.setGroup( source.get_package() );
    List<Bid> bidList = new ArrayList<>();
    if(nonNull(source.getBid())){
      for (net.media.openrtb3.Bid bid : source.getBid()) {
        bidList.add(bidBidConverter.map(bid, config));
      }
    }
    target.setBid( bidList);
    target.setSeat( source.getSeat() );
  }
}
