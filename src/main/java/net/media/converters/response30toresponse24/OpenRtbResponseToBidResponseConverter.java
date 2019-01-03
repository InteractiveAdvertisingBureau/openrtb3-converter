package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;

import java.util.HashMap;
import java.util.Map;

public class OpenRtbResponseToBidResponseConverter implements Converter<OpenRTB,BidResponse> {

  private Converter<Seatbid, SeatBid> seatBid30ToSeatBid24Converter;

  public OpenRtbResponseToBidResponseConverter(Converter<Seatbid,SeatBid> seatBid30ToSeatBid24Converter ){
    this.seatBid30ToSeatBid24Converter = seatBid30ToSeatBid24Converter;
  }
  @Override
  public BidResponse map(OpenRTB source, Config config) {
    Response response = source.getResponse();
    if ( response == null ) {
      return null;
    }

    BidResponse bidResponse = new BidResponse();

    bidResponse.setId( response.getId() );
    bidResponse.setSeatbid( seatbidListToSeatBidList( response.getSeatbid() ,response,config) );
    bidResponse.setBidid( response.getBidid() );
    bidResponse.setCur( response.getCur() );
    bidResponse.setNbr( response.getNbr() );
    Map<String, Object> map = response.getExt();
    if ( map != null ) {
      bidResponse.setExt(new HashMap<>(map) );
    }
    else {
      bidResponse.setExt( null );
    }
    mapResponse(response,adType,bidResponse);
    return bidResponse;
  }

  @Override
  public void inhance(OpenRTB source,BidResponse target, Config config) {

  }
}
