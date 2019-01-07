package net.media.converters.response30toresponse25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OpenRtbResponseToBidResponseConverter implements Converter<OpenRTB,BidResponse> {

  private Converter<Seatbid, SeatBid> seatBid30ToSeatBid24Converter;

  public OpenRtbResponseToBidResponseConverter(Converter<Seatbid,SeatBid>
                                                 seatBid30ToSeatBid24Converter ) {
    this.seatBid30ToSeatBid24Converter = seatBid30ToSeatBid24Converter;
  }
  @Override
  public BidResponse map(OpenRTB source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    BidResponse  bidResponse = new BidResponse();
    inhance(source,bidResponse,config);
    return bidResponse;
  }

  @Override
  public void inhance(OpenRTB source,BidResponse target, Config config) throws OpenRtbConverterException  {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    Response response = source.getResponse();
    if ( response == null )
      return;

    target.setId( response.getId() );
    List<SeatBid> seatBidList = new ArrayList<>();
    if(nonNull(response.getSeatbid())){
      for (Seatbid seatBid : response.getSeatbid()) {
        seatBid30ToSeatBid24Converter.map(seatBid,config);
      }
    }
    target.setSeatbid(seatBidList);
    target.setBidid( response.getBidid() );
    target.setCur( response.getCur() );
    target.setNbr( response.getNbr() );
    target.setExt(Utils.copyMap(response.getExt(),config));

    try {
      if (nonNull(response.getExt()) && nonNull(target.getExt())) {
        target.setCustomdata((String) response.getExt().get("customdata"));
        target.getExt().remove("customdata");
        target.getExt().put("cdata", response.getCdata());
      } else if (nonNull(target.getCustomdata())) {
        Map<String, Object> ext = new HashMap<>();
        ext.put("cdata", response.getCdata());
        target.setExt(ext);
      }
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("Error occured while type casting ext in openRtb", e);
    }
  }
}
