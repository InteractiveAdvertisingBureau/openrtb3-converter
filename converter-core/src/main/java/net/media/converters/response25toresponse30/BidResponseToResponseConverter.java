package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.CollectionUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidResponseToResponseConverter implements Converter<BidResponse, Response> {

  /**
   * @param bidResponse
   * @param config
   * @return
   */
  @Override
  public Response map(BidResponse bidResponse, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if ( bidResponse == null ) {
      return null;
    }
    Response response = new Response();
    enhance(bidResponse, response, config, converterProvider);
    return response;
  }

  /**
   *
   * @param bidResponse
   * @param response
   */
  @Override
  public void enhance(BidResponse bidResponse, Response response, Config config, Provider converterProvider)throws OpenRtbConverterException {
    response.setId( bidResponse.getId() );
    response.setBidid( bidResponse.getBidid() );
    response.setNbr( bidResponse.getNbr() );
    response.setCur( bidResponse.getCur() );
    Converter<SeatBid, Seatbid> converter = converterProvider.fetch(new Conversion(SeatBid.class, SeatBid.class));
    if (!CollectionUtils.isEmpty(bidResponse.getSeatbid())) {
      List<Seatbid> seatbids = new ArrayList<>();
      for (SeatBid seatBid : bidResponse.getSeatbid()) {
        seatbids.add(converter.map(seatBid, config, converterProvider));
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
    if(nonNull(response.getExt()) && nonNull(bidResponse.getExt())){
      response.getExt().put("customData",bidResponse.getCustomdata());
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
