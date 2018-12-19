package net.media.mapper;


import net.media.enums.AdType;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.Media;
import net.media.openrtb3.Native;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class MediaMapper {

  abstract Media map(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType);

  @Mappings({
    @Mapping(source = "bid.crid", target = "id"),
    @Mapping(target = "bundle",  expression = "bid.bundle == null ? null : (Collections" +
      ".singletonList(bid.bundle)"),
    @Mapping(source = "language", target = "lang")
  })
  abstract Ad mapAd(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType);

  @AfterMapping
  public void mapAd(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType,
                    @MappingTarget Ad ad){
    if (nonNull(bid) && nonNull(bid.getExt()) && nonNull(ad)) {
      Map<String,Object> ext = ad.getExt();
      ad.setSecure((Integer) ext.get("secure"));
      ad.setInit((Integer) ext.get("init"));
      ad.setLastmod((Integer) ext.get("lastMod"));
      ad.setMrating((Integer) ext.get("mrating"));
      ad.setCattax((Integer) ext.get("cattax"));
    }
  }

  @Mappings({
    @Mapping(source = "bid.adm", target = "adm"),
    @Mapping(source = "bid.h", target = "h"),
    @Mapping(source = "bid.w", target = "w"),
    @Mapping(source = "bid.wratio", target = "wratio"),
    @Mapping(source = "bid.hratio", target = "hratio"),
    @Mapping(target = "api",  expression = "bid.api == null ? null : (Collections.singletonList" +
      "bid.api)")
  })
  abstract Display mapDisplay(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType
    adType);

  public void mapDisplay(Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
                         Display display, @Context AdType adType) {
    if (isNull(bid) || isNull(display) || isNull(bid.getExt())) {
      return;
    }
    Map<String, Object> ext = bid.getExt();
    display.setCtype((Integer) ext.get("ctype"));
    display.setPriv((String) ext.get("priv"));
    display.setCurl((String) ext.get("curl"));
    if (adType == AdType.BANNER) {
      display.setBanner((Banner) ext.get("banner"));
    }
    else if (adType == AdType.NATIVE) {
      display.set_native((Native) ext.get("native"));
    }
    display.setEvent((List<Event>) ext.get(ext.get("event")));
  }
}
