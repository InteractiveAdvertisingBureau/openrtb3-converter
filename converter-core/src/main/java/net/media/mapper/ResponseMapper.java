package net.media.mapper;

import org.mapstruct.Mapper;

@Mapper(uses = {MediaMapper.class})
public interface ResponseMapper{
//  @Mappings({
//    @Mapping(source = "bid.impid",target = "item"),
//    @Mapping(target = "purl", source = "bid.nurl"),
//    @Mapping(target = "deal", source = "bid.dealid"),
//    @Mapping(target = "media", source="bid"),
//    @Mapping(target = "id", source = "bid.id")
//  })
//  net.media.openrtb3.Bid  bidMapper(Bid bid, SeatBid  seatBid, BidResponse bidResponse);
//
//  @Mappings({
//    @Mapping(target = "seat", source = "seatBid.seat"),
//    @Mapping(target = "_package", source = "seatBid.group"),
//  })
//   Seatbid seatMapper(Bid bid,SeatBid  seatBid,BidResponse bidResponse);


//  @AfterMapping
//   public void bidMapper(net.media.openrtb25.response.Bid bid, @MappingTarget net.media.openrtb3.Bid openRtb3){
//      if(nonNull(bid) && nonNull(openRtb3) && ){
//
//      }
//  }

//  @Mappings({
//
//  })
//  abstract Media  mediaMapper(Bid bid);



}
