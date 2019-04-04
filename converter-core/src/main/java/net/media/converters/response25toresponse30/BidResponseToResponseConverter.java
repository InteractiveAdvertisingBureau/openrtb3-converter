package net.media.converters.response25toresponse30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidResponseToResponseConverter implements Converter<BidResponse, Response> {

  private Converter<SeatBid, Seatbid> seatBidSeatbidConverter;

  public BidResponseToResponseConverter(Converter<SeatBid, Seatbid> seatBidSeatbidConverter) {
    this.seatBidSeatbidConverter = seatBidSeatbidConverter;
  }

  /**
   * @param bidResponse
   * @param config
   * @return
   */
  @Override
  public Response map(BidResponse bidResponse, Config config)throws OpenRtbConverterException {
    if ( bidResponse == null ) {
      return null;
    }
    Response response = new Response();
    enhance(bidResponse, response, config);
    return response;
  }

  /**
   *
   * @param bidResponse
   * @param response
   */
  @Override
  public void enhance(BidResponse bidResponse, Response response, Config config)throws OpenRtbConverterException {
    response.setId( bidResponse.getId() );
    response.setBidid( bidResponse.getBidid() );
    response.setNbr( bidResponse.getNbr() );
    response.setCur( bidResponse.getCur() );
    if (!CollectionUtils.isEmpty(bidResponse.getSeatbid())) {
      List<Seatbid> seatbids = new ArrayList<>();
      for (SeatBid seatBid : bidResponse.getSeatbid()) {
        seatbids.add(seatBidSeatbidConverter.map(seatBid, config));
      }
      response.setSeatbid(seatbids);
    }
    Map<String, Object> map = bidResponse.getExt();
    if ( map != null ) {
      response.setExt(new HashMap<>(map) );
    }
    else {
      response.setExt( null );
    }
    response.setCdata(bidResponse.getCustomdata());
  }
}
