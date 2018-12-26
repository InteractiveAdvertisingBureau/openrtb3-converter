package net.media.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.media.enums.AdType;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.*;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Asset;

import net.media.openrtb3.DataAsset;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.ImageAsset;
import net.media.openrtb24.request.Native;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Display;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;
import net.media.openrtb3.TitleAsset;
import net.media.openrtb3.VideoAsset;


import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.Context;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Arrays;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

//@Mapper(imports = Collections.class)

public class OpenRtb24To3Mapper {

//  OpenRtb24To3Mapper MAPPER = Mappers.getMapper(OpenRtb24To3Mapper.class);

//  @BeforeMapping
//  default void beforeMapping(BidRequest bidRequest, @MappingTarget Request request) {
//    if (CollectionUtils.isEmpty(bidRequest.getWseat())) {
//      request.setWseat(1);
//      request.setSeat(bidRequest.getWseat());
//    }
//  }
//
//  @Mappings({
//    @Mapping(target = "wseat", ignore = true),
//    @Mapping(target = "seat", ignore = true),
//    @Mapping(source = "bidRequest.imp", target = "item")
//  })
//  Request map(BidRequest bidRequest);
//
//  @Mappings({
//    @Mapping(source = "imp.bidfloor", target = "flr"),
//    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
//    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
//    @Mapping(source = "imp.pmp.deals", target = "deal"),
//    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
//    @Mapping(source = "imp.banner", target = "spec.placement.display")
//  })
//  Item map(Imp imp);





//  @Mappings({
//    @Mapping(target = "purl", source = "nurl"),
//    @Mapping(target = "deal", source = "dealid"),
//    @Mapping(target = "tactic", source = "tactic"),
//    @Mapping(target = "burl", source = "burl"),
//    @Mapping(target = "lurl", source = "lurl"),
//    @Mapping(target = "mid", source = "ext.mid"),
//    @Mapping(target = "media", source = "ext.media")
//  })
//  Bid map(net.media.openrtb24.response.Bid bid);

//  @Mappings({
//    @Mapping(target = "id", source = "crid"),
//    @Mapping(target = "lang", source = "language"),
//    @Mapping(target = "cattax", source = "ext.cattax"),
//    @Mapping(target = "secure", source = "ext.secure"),
//    @Mapping(target = "mrating", source = "ext.mrating"),
//    @Mapping(target = "init", source = "ext.init"),
//    @Mapping(target = "lastmod", source = "ext.lastmod")
//  })
//  Ad adMapper(net.media.openrtb24.response.Bid bid);


//  @Mappings({
//    @Mapping(target="ctype", source = "ext.ctype"),
//    @Mapping(target="mime", source = "ext.mime"),
//    @Mapping(target="api", source = "ext.api"),
//    @Mapping(target="wratio", source = "ext.wratio"),
//    @Mapping(target="hratio", source = "ext.hratio"),
//    @Mapping(target="priv", source = "ext.priv"),
//    @Mapping(target="curl", source = "ext.curl"),
//  })
//  Display displayMapper(net.media.openrtb24.response.Bid bid);


//  @Mappings({
//    @Mapping(target = "img", source = "ext.img"),
//    @Mapping(target = "link", source = "ext.link"),
//  })
//  Banner bannerMapper(net.media.openrtb24.response.Bid bid);

//  @Mappings({
//    @Mapping(target = "link", source = "ext.link"),
//    @Mapping(target = "asset", source = "ext.asset"),
//  })
//  net.media.openrtb3.Native nativeMapper(net.media.openrtb24.response.Bid bid);

//  @Mappings({
//    @Mapping(target="id", source = "ext.assetFormatId"),
//    @Mapping(target="req", source = "ext.req"),
//  })
//  Asset AssetMapper(net.media.openrtb24.response.Bid bid);

//  @Mappings({
//    @Mapping(target = "url", source = "ext.url"),
//    @Mapping(target = "urlfb", source = "ext.urlfb"),
//    @Mapping(target = "trkr", source = "ext.trkr")
//  })
//  LinkAsset linkAssetMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(target = "text", source = "ext.text"),
//    @Mapping(target = "len", source = "ext.len"),
//  })
//  TitleAsset titleAssetMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(target = "url", source = "ext.url"),
//    @Mapping(target = "type", source = "ext.imageAssetType"),
//  })
//  ImageAsset imageAssetMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(target = "curl", source = "ext.curl")
//  })
//  VideoAsset videoAssetMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(target = "value", source = "ext.dataValue"),
//    @Mapping(target = "len", source = "ext.dataLen"),
//    @Mapping(target = "type", source = "ext.type"),
//  })
//  DataAsset dataAssetMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(target = "type", source = "ext.eventType"),
//    @Mapping(target = "ext.eventType", ignore= true),
//    @Mapping(target = "method", source = "ext.trackMethod"),
//    @Mapping(target = "ext.trackMethod", ignore= true),
//    @Mapping(target = "api", source = "ext.eventApi"),
//    @Mapping(target = "ext.eventApi", ignore= true),
//    @Mapping(target = "url", source = "ext.evenUrl"),
//    @Mapping(target = "ext.evenUrl", ignore= true),
//    @Mapping(target = "cdata", source = "ext.eventCdata"),
//    @Mapping(target = "ext.eventCdata", ignore= true)
//  })
//  Event eventMapper(net.media.openrtb24.response.Bid bid);

//  @Mappings({
//    @Mapping(source = "banner.ext", target = "ext"),
//    @Mapping(source = "imp.instl", target = "instl"),
//    @Mapping(source = "imp.iframebuster", target = "ifrbust"),
//    @Mapping(source = "imp.clickbrowser", target = "clktype")
//  })
//  DisplayPlacement map(Banner banner, Native _native, Imp imp);
//
//
//  @Mappings({
//    @Mapping(source = "deal.bidFloor", target = "flr"),
//    @Mapping(source = "deal.bidFloorCur", target = "flrcur")
//  })
//  Deal map(net.media.openrtb24.request.Deal deal);
//
//  @Mappings({
//    @Mapping(target = "wseat", ignore = true),
//    @Mapping(target = "seat", ignore = true),
//    @Mapping(source = "bidRequest.imp", target = "item")
//  })
//  void updateRequestFromBidRequest(BidRequest bidRequest, @MappingTarget Request request);
//
//  @Mappings({
//    @Mapping(source = "ext.status", target = "status"),
//    @Mapping(source = "ext.feedback", target = "feedback"),
//    @Mapping(source = "ext.init", target = "init"),
//    @Mapping(source = "ext.lastmod", target = "lastmod"),
//    @Mapping(source = "ext.corr", target = "corr")
//  })
//  Audit auditMapper(net.media.openrtb24.response.Bid bid);
//
//
//
//  @Mappings({
//    @Mapping(source = "ext.mime", target = "mime"),
//    @Mapping(target = "ext.mime", ignore= true),
//    @Mapping(source = "ext.api", target = "api"),
//    @Mapping(target = "ext.api", ignore= true),
//    @Mapping(source = "ext.ctype", target = "ctype"),
//    @Mapping(target = "ext.ctype", ignore= true),
//    @Mapping(source = "ext.dur", target = "dur"),
//    @Mapping(target = "ext.dur", ignore= true),
//    @Mapping(source = "ext.adm", target = "adm"),
//    @Mapping(target = "ext.adm", ignore= true),
//    @Mapping(source = "ext.curl", target = "curl"),
//    @Mapping(target = "ext.curl", ignore= true)
//  })
//  Audio audioMapper(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(source = "ext.mime", target = "mime"),
//    @Mapping(target = "ext.mime", ignore= true),
//    @Mapping(source = "ext.api", target = "api"),
//    @Mapping(target = "ext.api", ignore= true),
//    @Mapping(source = "ext.ctype", target = "ctype"),
//    @Mapping(target = "ext.ctype", ignore= true),
//    @Mapping(source = "ext.dur", target = "dur"),
//    @Mapping(target = "ext.dur", ignore= true),
//    @Mapping(source = "ext.adm", target = "adm"),
//    @Mapping(target = "ext.adm", ignore= true),
//    @Mapping(source = "ext.curl", target = "curl"),
//    @Mapping(target = "ext.curl", ignore= true)
//  })
//  Video videoMapper(net.media.openrtb24.response.Bid bid);


//  Response map(BidResponse bidResponse);
//  @InheritInverseConfiguration
//  BidResponse map(Response response);
//
//  @AfterMapping
//  default
//
//  @AfterMapping
//  default

//  @Mappings({
//    @Mapping(target = "_package", source = "seatBid.group"),
//    @Mapping(target = "ext",source = "seatBid.ext"),
//  })
//  Seatbid seatMapper(SeatBid  seatBid,@org.mapstruct.Context AdType adType);
//  @InheritInverseConfiguration
//  @Mappings({
//    @Mapping(target = "ext",source = "seatBid.ext")
//  })
//  SeatBid seatMapper(Seatbid seatBid,@org.mapstruct.Context AdType adType);
//
//  @Mappings({
//    @Mapping(source = "bid.impid",target = "item"),
//    @Mapping(target = "purl", source = "bid.nurl"),
//    @Mapping(target = "deal", source = "bid.dealid"),
//    @Mapping(target = "media", source="bid"),
//    @Mapping(target = "id", source = "bid.id"),
//    @Mapping(target = "ext", source = "bid.ext")
//  })
//  net.media.openrtb3.Bid  bidMapper(Bid bid, SeatBid  seatBid, BidResponse bidResponse,@org.mapstruct.Context AdType adType);
//  @InheritInverseConfiguration
//  @Mappings({
//    @Mapping(target = "id", source = "bid.id"),
//    @Mapping(target = "ext", source = "bid.ext")
//  })
//  Bid bidMapper(net.media.openrtb3.Bid bid,Seatbid seatbid ,Response response, @org.mapstruct.Context AdType adType);
//
//  @Mappings({
//    @Mapping(target = "ad", source = "bid")
//  })
//  Media map(Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType adType);
//
//  @Mappings({
//    @Mapping(source = "bid.crid", target = "id"),
////    @Mapping(target = "bundle",  expression = "java(bid.getBundle() == null ? null : Collections.singleton(bid.getBundle()))"),
//    @Mapping(source = "bid.language", target = "lang"),
//    @Mapping(source = "bid.ext",target = "ext")
//  })
//  Ad mapAd(Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType adType);
//
//  @AfterMapping
//  default
//
//  @Mappings({
//    @Mapping(source = "bid.adm", target = "adm"),
//    @Mapping(source = "bid.h", target = "h"),
//    @Mapping(source = "bid.w", target = "w"),
//    @Mapping(source = "bid.wratio", target = "wratio"),
//    @Mapping(source = "bid.hratio", target = "hratio"),
////    @Mapping(target = "api",  expression = "java(bid.getApi() == null ? null : Collections.singleton(bid.getApi()))")
//  })
//  abstract Display mapDisplay(Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType
//    adType);
//
//  default
//
////  @Mappings({
////    @Mapping(source = "bid.adm", target = "adm"),
////    @Mapping(source = "bid.ext",target = "ext"),
//////    @Mapping(target = "api",  expression = "java(bid.getApi() == null ? null : Collections.singleton(bid.getApi()))")
////  })
////  abstract Video mapVideo(Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType
////    adType);
//
//  default
//
//  @Mappings({
//    @Mapping(source = "bid.adm", target = "adm"),
//    @Mapping(source = "bid.ext",target = "ext"),
////    @Mapping(target = "api",  expression = "java(bid.getApi() == null ? null : Collections.singleton(bid.getApi()))")
//  })
//  abstract Video mapAudio(Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType
//    adType);
//
//  default

  public static void main(String[] args) {
//    BidRequest bidRequest = new BidRequest();
//    Imp imp = new Imp();
//    imp.setId("1");
//    imp.setBidfloor(1.4);
//    bidRequest.setImp(Arrays.asList(imp));
//    Request request = MAPPER.map(bidRequest);
//    imp.setId("2");
//    MAPPER.updateRequestFromBidRequest(bidRequest, request);
//    System.out.println("a");
    try {
      OpenRtb24To3MapperImpl impl = new OpenRtb24To3MapperImpl();

      byte[] jsonData = Files.readAllBytes(Paths.get("/Users/samya.p/openrtb-converter/src/main/java/net/media/mapper/Response3.0.json"));
      ObjectMapper objectMapper = new ObjectMapper();
      Response response = objectMapper.readValue(jsonData, Response.class);
      BidResponse bidResponse = impl.map(response,AdType.BANNER);

      /*byte[] jsonData1 = Files.readAllBytes(Paths.get("/Users/samya.p/openrtb-converter/src/main/java/net/media/mapper/Response2.5.json"));
      ObjectMapper  objectMapper1 = new ObjectMapper();
      BidResponse bidResponse1 = objectMapper1.readValue(jsonData1,BidResponse.class);
      Response response1 = impl.map(bidResponse1,AdType.BANNER);
*/
//      System.out.println(response1.toString());




      System.out.println(objectMapper.writeValueAsString(bidResponse));
    }catch (IOException e){
      System.out.println("Phatna  hihe"+e.getMessage());
    }

//    BidResponse bidResponse = new BidResponse();
//    SeatBid seatBid = new SeatBid();
//    Bid bid =  new Bid();
//    bid.setImpid("1");
//    bid.setBurl("dsddsdddddd");
//    List<Bid> bids = new ArrayList<>();
//    bids.add(bid);
//    seatBid.setBid(bids);
//    List<SeatBid> seatBids = new ArrayList<>();
//    seatBids.add(seatBid);
//    bidResponse.setSeatbid(seatBids);

  }

}
