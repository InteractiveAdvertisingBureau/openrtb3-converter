package net.media.converters.response25toresponse30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidResponseToResponseConverter implements Converter<BidResponse, Response> {

  private Converter<List<SeatBid>, List<Seatbid>> seatBidListToSeatbidList;

  public BidResponseToResponseConverter(Converter<List<SeatBid>, List<Seatbid>> seatBidListToSeatbidList) {
    this.seatBidListToSeatbidList = seatBidListToSeatbidList;
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
    inhance(bidResponse, response, config);
    return response;
  }

  /**
   *
   * @param bidResponse
   * @param response
   */
  @Override
  public void inhance(BidResponse bidResponse, Response response, Config config)throws OpenRtbConverterException {
    response.setId( bidResponse.getId() );
    response.setBidid( bidResponse.getBidid() );
    response.setNbr( bidResponse.getNbr() );
    response.setCur( bidResponse.getCur() );
    response.setSeatbid(seatBidListToSeatbidList.map(bidResponse.getSeatbid(), config));
    Map<String, Object> map = bidResponse.getExt();
    if ( map != null ) {
      response.setExt(new HashMap<>(map) );
    }
    else {
      response.setExt( null );
    }
    if(nonNull(response.getExt()) && nonNull(bidResponse.getExt())){
      response.getExt().put("customerData",bidResponse.getCustomdata());
      try {
        response.setCdata((String) bidResponse.getExt().get("cdata"));
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while casting cdata in bidResponse.ext", e);
      }
      response.getExt().remove("cdata");
    }
    else if(nonNull(bidResponse.getCustomdata())) {
      Map<String,Object>  ext = new HashMap<>();
      ext.put("customData",bidResponse.getCustomdata());
      response.setExt(ext);
    }
  }
}
