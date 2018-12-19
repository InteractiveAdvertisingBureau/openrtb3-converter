package net.media.mapper;

import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Media;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.Map;

import static java.util.Objects.nonNull;

@Mapper(uses = {MediaMapper.class})
public abstract class ResponseMapper implements OpenRtb24To3Mapper{
  @Mappings({
    @Mapping(source = "impid",target = "item"),
    @Mapping(target = "purl", source = "nurl"),
    @Mapping(target = "deal", source = "dealid"),
    @Mapping(target = "bid.media", source="bid")
  })
  abstract net.media.openrtb3.Bid  bidMapper(Bid bid, SeatBid  seatBid, BidResponse bidResponse);



  @AfterMapping
   public void bidMapper(net.media.openrtb24.response.Bid bid, @MappingTarget net.media.openrtb3.Bid openRtb3){
      if(nonNull(bid) && nonNull(openRtb3)){
        bi
      }
  }

  @Mappings({

  })
  abstract Media  mediaMapper(Bid bid);



}
