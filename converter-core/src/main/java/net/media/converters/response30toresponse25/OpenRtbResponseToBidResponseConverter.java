package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OpenRtbResponseToBidResponseConverter implements Converter<OpenRTB,BidResponse> {

  @Override
  public BidResponse map(OpenRTB source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    BidResponse  bidResponse = new BidResponse();
    enhance(source,bidResponse,config, converterProvider);
    return bidResponse;
  }

  @Override
  public void enhance(OpenRTB source, BidResponse target, Config config, Provider converterProvider) throws OpenRtbConverterException  {

    Converter<Seatbid, SeatBid> seatBid30ToSeatBid25Converter = converterProvider.fetch(new
      Conversion<>(Seatbid.class, SeatBid.class));
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    Response response = source.getResponse();
    if ( response == null )
      return;

    target.setId( response.getId() );
    List<SeatBid> seatBidList = new ArrayList<>();
    if(nonNull(response.getSeatbid())){
      for (Seatbid seatBid : response.getSeatbid()) {
        seatBidList.add(seatBid30ToSeatBid25Converter.map(seatBid,config, converterProvider));
      }
    }
    target.setSeatbid(seatBidList);
    target.setBidid( response.getBidid() );
    target.setCur( response.getCur() );
    target.setNbr( response.getNbr() );
    target.setExt(Utils.copyMap(response.getExt(),config));
    target.setCustomdata(response.getCdata());
  }
}
