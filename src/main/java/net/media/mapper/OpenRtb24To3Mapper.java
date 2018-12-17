package net.media.mapper;

import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Bid;
import net.media.openrtb24.request.Native;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Display;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Mapper
public interface OpenRtb24To3Mapper {

  OpenRtb24To3Mapper MAPPER = Mappers.getMapper(OpenRtb24To3Mapper.class);

  @BeforeMapping
  default void beforeMapping(BidRequest bidRequest, @MappingTarget Request request) {
    if (CollectionUtils.isEmpty(bidRequest.getWseat())) {
      request.setWseat(1);
      request.setSeat(bidRequest.getWseat());
    }
  }

  @Mappings({
    @Mapping(target = "wseat", ignore = true),
    @Mapping(target = "seat", ignore = true),
    @Mapping(source = "bidRequest.imp", target = "item")
  })
  Request map(BidRequest bidRequest);

  @Mappings({
    @Mapping(source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
    @Mapping(source = "imp.pmp.deals", target = "deal"),
    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
    @Mapping(source = "imp.banner", target = "spec.placement.display")
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



  @Mappings({
    @Mapping(source = "banner.ext", target = "ext"),
    @Mapping(source = "imp.instl", target = "instl"),
    @Mapping(source = "imp.iframebuster", target = "ifrbust"),
    @Mapping(source = "imp.clickbrowser", target = "clktype")
  })
  DisplayPlacement map(Banner banner, Native _native, Imp imp);


  @Mappings({
    @Mapping(source = "deal.bidFloor", target = "flr"),
    @Mapping(source = "deal.bidFloorCur", target = "flrcur")
  })
  Deal map(net.media.openrtb24.request.Deal deal);

  @Mappings({
    @Mapping(target = "wseat", ignore = true),
    @Mapping(target = "seat", ignore = true),
    @Mapping(source = "bidRequest.imp", target = "item")
  })
  void updateRequestFromBidRequest(BidRequest bidRequest, @MappingTarget Request request);

  public static void main(String[] args) {
    BidRequest bidRequest = new BidRequest();
    Imp imp = new Imp();
    imp.setId("1");
    imp.setBidfloor(1.4);
    bidRequest.setImp(Arrays.asList(imp));
    Request request = MAPPER.map(bidRequest);
    imp.setId("2");
    MAPPER.updateRequestFromBidRequest(bidRequest, request);
    System.out.println("a");
  }
}
