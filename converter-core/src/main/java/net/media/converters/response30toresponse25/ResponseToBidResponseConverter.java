package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ResponseToBidResponseConverter
  implements Converter<Response, BidResponse2_X> {

  @Override
  public BidResponse2_X map(Response source, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    BidResponse2_X bidResponse = new BidResponse2_X();
    enhance(source, bidResponse, config, converterProvider);
    return bidResponse;
  }

  @Override
  public void enhance(
    Response source, BidResponse2_X target, Config config, Provider converterProvider)
    throws OpenRtbConverterException {

    Converter<Seatbid, SeatBid> seatBid30ToSeatBid25Converter =
      converterProvider.fetch(new Conversion<>(Seatbid.class, SeatBid.class));
    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }

    target.setId(source.getId());
    List<SeatBid> seatBidList = new ArrayList<>();
    if (nonNull(source.getSeatbid())) {
      for (Seatbid seatBid : source.getSeatbid()) {
        seatBidList.add(seatBid30ToSeatBid25Converter.map(seatBid, config, converterProvider));
      }
    }
    target.setSeatbid(seatBidList);
    target.setBidid(source.getBidid());
    target.setCur(source.getCur());
    target.setNbr(source.getNbr());
    if (nonNull(source.getExt())) {
      target.setExt(new HashMap<>(source.getExt()));
    }
    target.setCustomdata(source.getCdata());
  }
}
