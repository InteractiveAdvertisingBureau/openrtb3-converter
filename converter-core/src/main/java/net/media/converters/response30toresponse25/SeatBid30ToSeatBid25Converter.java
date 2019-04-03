package net.media.converters.response30toresponse25;

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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SeatBid30ToSeatBid25Converter implements Converter<Seatbid,SeatBid>{

  @Override
  public SeatBid map(Seatbid source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    SeatBid  seatBid = new SeatBid();
    enhance(source,seatBid,config, converterProvider);
    return seatBid;
  }

  @Override
  public void enhance(Seatbid source, SeatBid target, Config config, Provider<Conversion, Converter> converterProvider)  throws OpenRtbConverterException {

    Converter<net.media.openrtb3.Bid, Bid> bidBidConverter = converterProvider.fetch(new
      Conversion(net.media.openrtb3.Bid.class, Bid.class));
    if(isNull(source) || isNull(target) || isNull(config))
      return;


    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
    else {
      target.setExt( null );
    }
    target.setGroup( source.get_package() );
    List<Bid> bidList = new ArrayList<>();
    if(nonNull(source.getBid())){
      for (net.media.openrtb3.Bid bid : source.getBid()) {
        bidList.add(bidBidConverter.map(bid, config, converterProvider));
      }
    }
    target.setBid( bidList);
    target.setSeat( source.getSeat() );
  }
}
