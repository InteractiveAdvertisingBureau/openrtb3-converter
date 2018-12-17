package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface OpenRtb24To3Mapper {

  @Mappings({
  })
  Request map(BidRequest request);

  @Mappings({
    @Mapping( source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
  })
  Item map(Imp imp);

  @Mappings({
  })
  Response map(BidResponse bidResponse);

  @Mappings({
    @Mapping(source = "seat", target = "bidResponse.seat"),
    @Mapping(source = "package", target = "bidResponse.group"),
  })
  Seatbid seatMapper(BidResponse bidResponse);

  @Mappings({
    @Mapping(source = "purl", target = "nurl"),
  })
  Bid map(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(source = "id", target = "crid"),
    @Mapping(source = "lang", target = "language"),
    @Mapping(source = "lang", target = "language"),
  })
  Ad adMapper(net.media.openrtb24.response.Bid bid);


}
