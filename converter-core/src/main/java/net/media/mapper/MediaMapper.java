package net.media.mapper;


import org.mapstruct.*;

@Mapper
public interface MediaMapper {

//  Media map(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType);
//
//  @Mappings({
//    @Mapping(source = "bid.crid", target = "id"),
////    @Mapping(target = "bundle",  expression = "bid.bundle == null ? null : (Collections" +
////      ".singletonList(bid.bundle)"),
//    @Mapping(source = "bid.language", target = "lang"),
//    @Mapping(source = "bid.ext",target = "ext")
//  })
//  Ad mapAd(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType);
//
//  @AfterMapping
//  default public void mapAd(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType adType,
//                    @MappingTarget Ad ad){
//    if (nonNull(bid) && nonNull(bid.getExt()) && nonNull(ad)) {
//      Map<String,Object> ext = ad.getExt();
//      ad.setSecure((Integer) ext.get("secure"));
//      ad.setInit((Integer) ext.get("init"));
//      ad.setLastmod((Integer) ext.get("lastMod"));
//      ad.setMrating((Integer) ext.get("mrating"));
//      ad.setCattax((Integer) ext.get("cattax"));
//    }
//  }
//
//  @Mappings({
//    @Mapping(source = "bid.adm", target = "adm"),
//    @Mapping(source = "bid.h", target = "h"),
//    @Mapping(source = "bid.w", target = "w"),
//    @Mapping(source = "bid.wratio", target = "wratio"),
//    @Mapping(source = "bid.hratio", target = "hratio"),
////    @Mapping(target = "api",  expression = "bid.api == null ? null : (Collections.singletonList" +
////      "bid.api)")
//  })
//  abstract Display mapDisplay(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType
//    adType);
//
//  default public void mapDisplay(Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
//                         Display display, @Context AdType adType) {
//    if (isNull(bid) || isNull(display) || isNull(bid.getExt())) {
//      return;
//    }
//    Map<String, Object> ext = bid.getExt();
//    display.setCtype((Integer) ext.get("ctype"));
//    display.setPriv((String) ext.get("priv"));
//    display.setCurl((String) ext.get("curl"));
//    if (adType == AdType.BANNER) {
//      display.setBanner((Banner) ext.get("banner"));
//    }
//    else if (adType == AdType.NATIVE) {
//      display.set_native((Native) ext.get("native"));
//    }
//    display.setEvent((List<Event>) ext.get(ext.get("event")));
//  }
//
//  @Mappings({
//    @Mapping(source = "bid.adm", target = "adm"),
//    @Mapping(source = "bid.ext",target = "ext")
////    @Mapping(target = "api",  expression = "bid.api == null ? null : (Collections.singletonList" +
////      "bid.api)")
//  })
//  abstract Video mapVideo(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType
//    adType);
//
//  default public void mapVideo(Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
//    Video video, @Context AdType adType) {
//    if (isNull(bid) || isNull(video) || isNull(bid.getExt())) {
//      return;
//    }
//    Map<String, Object> ext = bid.getExt();
//    video.setCtype((Integer) ext.get("ctype"));
//    video.setCurl((String) ext.get("curl"));
//    video.setDur((Integer) ext.get("dur"));
//
//  }
//
//  @Mappings({
//    @Mapping(source = "bid.adm", target = "adm"),
//    @Mapping(source = "bid.ext",target = "ext")
////    @Mapping(target = "api",  expression = "bid.api == null ? null : (Collections.singletonList" +
////      "bid.api)")
//  })
//  abstract Video mapAudio(Bid bid, SeatBid seatBid, BidResponse bidResponse, @Context AdType
//    adType);
//
//  default  public void mapAudio(Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
//    Audio audio, @Context AdType adType) {
//    if (isNull(bid) || isNull(audio) || isNull(bid.getExt())) {
//      return;
//    }
//    Map<String, Object> ext = bid.getExt();
//    audio.setCtype((Integer) ext.get("ctype"));
//    audio.setCurl((String) ext.get("curl"));
//    audio.setDur((Integer) ext.get("dur"));
//
//  }
}
