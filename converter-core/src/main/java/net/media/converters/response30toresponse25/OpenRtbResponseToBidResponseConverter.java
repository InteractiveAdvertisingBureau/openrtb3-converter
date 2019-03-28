package net.media.converters.response30toresponse25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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
    enhance(source,bidResponse,config);
    return bidResponse;
  }

  @Override
  public void enhance(OpenRTB source, BidResponse target, Config config) throws OpenRtbConverterException  {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    Response response = source.getResponse();
    if ( response == null )
      return;

    target.setId( response.getId() );
    List<SeatBid> seatBidList = new ArrayList<>();
    if(nonNull(response.getSeatbid())){
      for (Seatbid seatBid : response.getSeatbid()) {
        seatBidList.add(seatBid30ToSeatBid24Converter.map(seatBid,config));
      }
    }
    target.setSeatbid(seatBidList);
    target.setBidid( response.getBidid() );
    target.setCur( response.getCur() );
    target.setNbr( response.getNbr() );
    target.setExt(Utils.copyMap(response.getExt(),config));
    target.setCustomdata(response.getCdata());
//    try {
//      if (nonNull(response.getExt()) && nonNull(target.getExt())) {
//        target.setCustomdata((String) response.getExt().get("customdata"));
//        target.getExt().remove("customdata");
//        target.getExt().put("cdata", response.getCdata());
//      } else if (nonNull(target.getCustomdata())) {
//        Map<String, Object> ext = new HashMap<>();
//        ext.put("cdata", response.getCdata());
//        target.setExt(ext);
//      }
//    }
//    catch (Exception e) {
//      throw new OpenRtbConverterException("Error occured while type casting ext in openRtb", e);
//    }
  }
}
