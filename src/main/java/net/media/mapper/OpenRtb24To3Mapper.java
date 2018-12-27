package net.media.mapper;

import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.*;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.response.BidResponse;


import net.media.openrtb3.Context;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import org.mapstruct.*;

import java.util.*;

@Mapper(uses = BidRequestMapper.class)
public interface OpenRtb24To3Mapper {

  OpenRtb24To3Mapper MAPPER = Mappers.getMapper(OpenRtb24To3Mapper.class);

  @Mappings({
    @Mapping(source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
    @Mapping(source = "imp.pmp.deals", target = "deal"),
    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
    @Mapping(source = "imp.banner", target = "spec.placement.display")
  })
  Item map(Imp imp);

  /*@Mappings({
    @Mapping(source="cdata", target = "ext.cdata"),
  })
  Response map(BidResponse bidResponse);

  @Mappings({
    @Mapping(source = "seat", target = "bidResponse.seat"),
    @Mapping(source = "package", target = "bidResponse.group"),
  })
  Seatbid seatMapper(BidResponse bidResponse);

  @Mappings({
    @Mapping(target = "purl", source = "nurl"),
    @Mapping(target = "deal", source = "dealid"),
    @Mapping(target = "tactic", source = "ext.tactic"),
    @Mapping(target = "burl", source = "ext.burl"),
    @Mapping(target = "lurl", source = "lurl"),
    @Mapping(target = "mid", source = "ext.mid"),
    @Mapping(target = "media", source = "ext.media")
  })
  Bid map(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "id", source = "crid"),
    @Mapping(target = "lang", source = "language"),
    @Mapping(target = "cattax", source = "ext.cattax"),
    @Mapping(target = "secure", source = "ext.secure"),
    @Mapping(target = "mrating", source = "ext.mrating"),
    @Mapping(target = "init", source = "ext.init"),
    @Mapping(target = "lastmod", source = "ext.lastmod")
  })
  Ad adMapper(net.media.openrtb24.response.Bid bid);


  @Mappings({
    @Mapping(target="ctype", source = "ext.ctype"),
    @Mapping(target="mime", source = "ext.mime"),
    @Mapping(target="api", source = "ext.api"),
    @Mapping(target="wratio", source = "ext.wratio"),
    @Mapping(target="hratio", source = "ext.hratio"),
    @Mapping(target="priv", source = "ext.priv"),
    @Mapping(target="curl", source = "ext.curl"),
  })
  Display displayMapper(net.media.openrtb24.response.Bid bid);


  @Mappings({
    @Mapping(target = "img", source = "ext.img"),
    @Mapping(target = "link", source = "ext.link"),
  })
  Banner bannerMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "link", source = "ext.link"),
    @Mapping(target = "asset", source = "ext.asset"),
  })
  net.media.openrtb3.Native nativeMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target="id", source = "ext.assetFormatId"),
    @Mapping(target="req", source = "ext.req"),
  })
  Asset AssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "url", source = "ext.url"),
    @Mapping(target = "urlfb", source = "ext.urlfb"),
    @Mapping(target = "trkr", source = "ext.trkr")
  })
  LinkAsset linkAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "text", source = "ext.text"),
    @Mapping(target = "len", source = "ext.len"),
  })
  TitleAsset titleAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "url", source = "ext.url"),
    @Mapping(target = "type", source = "ext.imageAssetType"),
  })
  ImageAsset imageAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "curl", source = "ext.curl")
  })
  VideoAsset videoAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "value", source = "ext.dataValue"),
    @Mapping(target = "len", source = "ext.dataLen"),
    @Mapping(target = "type", source = "ext.type"),
  })
  DataAsset dataAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "type", source = "ext.eventType"),
    @Mapping(target = "method", source = "ext.trackMethod"),
    @Mapping(target = "api", source = "ext.eventApi"),
    @Mapping(target = "url", source = "ext.evenUrl"),
    @Mapping(target = "cdata", source = "ext.eventCdata")

  })
  Event eventMapper(net.media.openrtb24.response.Bid bid);

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
  void updateRequestFromBidRequest(BidRequest bidRequest, @MappingTarget Request request);*/

  void updateItemFromImp(Imp imp, @MappingTarget Item item);

  public static void main(String[] args) {
    BidRequest bidRequest = new BidRequest();
    bidRequest.setAllimps(1);
    net.media.openrtb24.request.User user = new net.media.openrtb24.request.User();
    user.setAge(1);
    user.setBuyeruid("adad");
    user.setExt(new HashMap<String, Object>(){{put("consent", "1"); put("random", 1);}});
    bidRequest.setUser(user);
    bidRequest.setBadv(new HashSet<String>(){{add("usse.ff");}});
    Imp imp = new Imp();
    imp.setId("1");
    net.media.openrtb24.request.Banner banner = new net.media.openrtb24.request.Banner();
    banner.setBattr(new HashSet<Integer>(){{add(8);}});
    imp.setBanner(banner);
    bidRequest.setImp(new ArrayList<Imp>(){{add(imp);}});
    /*Request request = MAPPER.mapRequestToBidRequest(bidRequest);
    System.out.println(request.getPack());*/
    /*Imp imp = new Imp();
    imp.setId("1");
    Item item = a.map(imp);
    System.out.println(item.getId());
    imp.setId("2");
    a.updateItemFromImp(imp, item);
    System.out.println(item.getId());*/
  }
}