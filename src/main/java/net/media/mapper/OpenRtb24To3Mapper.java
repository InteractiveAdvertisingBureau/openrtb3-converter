package net.media.mapper;

import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.*;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;


import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Mapper
public interface OpenRtb24To3Mapper {

  OpenRtb24To3Mapper MAPPER = Mappers.getMapper(OpenRtb24To3Mapper.class);

  @AfterMapping
  default void updateWseat(@MappingTarget Request target, BidRequest source) {
    if(source == null)
      return;
    if (source.getWseat()!=null && source.getWseat().size()>0){
      target.setWseat(1);
      target.setSeat(source.getWseat());
    } else {
      target.setWseat(0);
      target.setSeat(source.getBseat());
    }
  }

  @Mappings({
    @Mapping(target = "seat", ignore = true),
    @Mapping(target = "wseat", ignore = true),
    @Mapping(source = "allimps", target = "pack"),
    @Mapping(source = "user.customdata", target = "cdata"),
    @Mapping(source = "site", target = "context.site"),
    @Mapping(source = "app", target = "context.app"),
    @Mapping(source = "user", target = "context.user"),
    @Mapping(source = "device", target = "context.device"),
    @Mapping(source = "regs", target = "context.regs"),
    @Mapping(source = "bidRequest", target = "context.restrictions"),
  })
  Request mapRequestToBidRequest(BidRequest bidRequest);

  @AfterMapping
  default void mapSourceExt(@MappingTarget Source target, net.media.openrtb24.request.Source source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setTs((Integer) source.getExt().get("ts"));
    target.setDs((String) source.getExt().get("ds"));
    target.setDsmap((String) source.getExt().get("dsMap"));
    target.setCert((String) source.getExt().get("cert"));
    target.setDigest((String) source.getExt().get("digest"));
    target.getExt().remove("ts");
    target.getExt().remove("ds");
    target.getExt().remove("dsMap");
    target.getExt().remove("cert");
    target.getExt().remove("digest");
  }

  @Mappings({
    @Mapping(source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
    @Mapping(source = "imp.pmp.deals", target = "deal"),
    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
    @Mapping(source = "imp.banner", target = "spec.placement.display")
  })
  Source mapRtb24SourcetoRtb3Source(net.media.openrtb24.request.Source source);

  @AfterMapping
  default void mapSiteExt(@MappingTarget Site target, net.media.openrtb24.request.Site source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setAmp((Integer) source.getExt().get("amp"));
    target.getExt().remove("cattax");
    target.getExt().remove("amp");
  }

  @Mappings({
    @Mapping(source = "sectioncat", target = "sectcat"),
    @Mapping(source = "privacypolicy", target = "privpolicy"),
    @Mapping(source = "publisher", target = "pub")
  })
  Site mapRtb24SitetoRtb3Site(net.media.openrtb24.request.Site site);

  @AfterMapping
  default void mapAppExt(@MappingTarget App target, net.media.openrtb24.request.App source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setStoreid((String) source.getExt().get("storeid"));
    target.getExt().remove("cattax");
    target.getExt().remove("storeid");
  }

  @Mappings({
    @Mapping(source = "sectioncat", target = "sectcat"),
    @Mapping(source = "privacypolicy", target = "privpolicy"),
    @Mapping(source = "publisher", target = "pub")
  })
  App mapRtb24ApptoRtb3App(net.media.openrtb24.request.App app);

  @AfterMapping
  default void mapContentExt(@MappingTarget Content target, net.media.openrtb24.request.Content source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }

  @Mappings({
    @Mapping(source = "contentrating", target = "rating"),
    @Mapping(source = "userrating", target = "urating"),
    @Mapping(source = "qagmediarating", target = "mrating"),
    @Mapping(source = "language", target = "lang"),
    @Mapping(source = "embeddable", target = "embed"),
    @Mapping(source = "livestream", target = "live"),
    @Mapping(source = "sourcerelationship", target = "srcrel"),
  })
  Content mapRtb24ContenttoRtb3Content(net.media.openrtb24.request.Content content);

  @AfterMapping
  default void mapProducerExt(@MappingTarget Producer target, net.media.openrtb24.request.Producer source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }

  @Mappings({

  })
  Producer mapRtb24ProducertoRtb3Producer(net.media.openrtb24.request.Producer producer);

  @AfterMapping
  default void mapUserExt(@MappingTarget User target, net.media.openrtb24.request.User source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setConsent((String) source.getExt().get("consent"));
    target.getExt().remove("consent");
  }

  @Mappings({

  })
  User mapRtb24UsertoRtb3User(net.media.openrtb24.request.User user);

  @AfterMapping
  default void mapRegsExt(@MappingTarget Regs target, net.media.openrtb24.request.Regs source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setGdpr((Integer) source.getExt().get("gdpr"));
    target.getExt().remove("gdpr");
  }

  @Mappings({

  })
  Regs mapRtb24RegstoRtb3Regs(net.media.openrtb24.request.Regs regs);

  @Mappings({
    @Mapping(source = "ipservice", target = "ipserv"),
    @Mapping(source = "accuracy", target = "accur")
  })
  Geo mapRtb24GeotoRtb3Geo(net.media.openrtb24.request.Geo geo);

  @AfterMapping
  default void mapDeviceExt(@MappingTarget Device target, net.media.openrtb24.request.Device source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setXff((String) source.getExt().get("xff"));
    target.getExt().remove("xff");
    target.setIptr((Integer) source.getExt().get("iptr"));
    target.getExt().remove("iptr");
  }

  @Mappings({
    @Mapping(source = "devicetype", target = "type"),
    @Mapping(source = "language", target = "lang"),
    @Mapping(source = "connectiontype", target = "contype")
  })
  Device mapRtb24DevicetoRtb3Device(net.media.openrtb24.request.Device device);

  @AfterMapping
  default void updateBattrRestriction(@MappingTarget Restrictions restrictions, BidRequest bidRequest) {
    if(bidRequest == null)
      return;
    if(bidRequest.getImp() == null)
      return;
    if(bidRequest.getImp().size() == 0)
      return;
    if(bidRequest.getImp().get(0).getBanner() != null && bidRequest.getImp().get(0).getBanner().getBattr() != null) {
      restrictions.setBattr(bidRequest.getImp().get(0).getBanner().getBattr());
    } else if(bidRequest.getImp().get(0).getVideo() != null && bidRequest.getImp().get(0).getVideo().getBattr() != null) {
      restrictions.setBattr(bidRequest.getImp().get(0).getVideo().getBattr());
    } else if(bidRequest.getImp().get(0).getNat() != null && bidRequest.getImp().get(0).getNat().getBattr() != null) {
      restrictions.setBattr(bidRequest.getImp().get(0).getNat().getBattr());
    }
    if(bidRequest.getExt() == null)
      return;
    restrictions.setCattax((Integer) bidRequest.getExt().get("cattax"));
    restrictions.getExt().remove("cattax");
  }

  //todo check how to map ext of restrictions as ext is present in request also
  //todo check for dooh
  @Mappings({
    @Mapping(source = "bidRequest.badv", target = "badv"),
    @Mapping(source = "bidRequest.bapp", target = "bapp"),
    @Mapping(source = "bidRequest.bcat", target = "bcat"),
    @Mapping(target = "battr", ignore = true),
    @Mapping(target = "ext", ignore = true)
  })
  Restrictions mapRtb3Restriction(BidRequest bidRequest);

  @AfterMapping
  default void updateRtb3ToRtb24BidRequest(@MappingTarget BidRequest target, Request source) {
    if(source == null)
      return;

    if(source.getWseat() == null)
      return;

    if (source.getWseat() == 0){
      target.setBseat(source.getSeat());
    } else {
      target.setWseat(source.getSeat());
    }

    if(source.getContext() == null)
      return;

    if(source.getContext().getRestrictions() == null)
      return;

    if(source.getContext().getRestrictions().getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getContext().getRestrictions().getCattax());
    }

    if(source.getContext().getRestrictions().getExt() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().putAll(source.getContext().getRestrictions().getExt());
    }

    if(target.getImp() == null)
      return;
    for(Imp imp : target.getImp()) {
      if(imp.getBanner() != null)
        imp.getBanner().setBattr(source.getContext().getRestrictions().getBattr());
      if(imp.getVideo() != null)
        imp.getVideo().setBattr(source.getContext().getRestrictions().getBattr());
      if(imp.getNat() != null)
        imp.getNat().setBattr(source.getContext().getRestrictions().getBattr());
    }
  }

  @Mappings({
    @Mapping(target = "bseat", ignore = true),
    @Mapping(target = "wseat", ignore = true),
    @Mapping(source = "pack", target = "allimps"),
    @Mapping(source = "cdata", target = "user.customdata"),
    @Mapping(source = "context.site", target = "site"),
    @Mapping(source = "context.app", target = "app"),
    @Mapping(source = "context.user", target = "user"),
    @Mapping(source = "context.device", target = "device"),
    @Mapping(source = "context.regs", target = "regs"),
    @Mapping(source = "context.restrictions.badv", target = "badv"),
    @Mapping(source = "context.restrictions.bapp", target = "bapp"),
    @Mapping(source = "context.restrictions.bcat", target = "bcat"),
    @Mapping(source = "request.ext", target = "ext"),
  })
  BidRequest mapRtb3RequestToRtb24BidRequest(Request request);

  @AfterMapping
  default void mapDeviceTo24(@MappingTarget net.media.openrtb24.request.Device target, Device source) {
    if(source == null)
      return;

    if(source.getXff() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("xff", source.getXff());
    }

    if(source.getIptr() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("iptr", source.getXff());
    }

    if(source.getExt() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().putAll(source.getExt());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.Device mapRtb24DevicetoRtb3Device(Device device);

  @AfterMapping
  default void mapRegsTo24(@MappingTarget net.media.openrtb24.request.Regs target, Regs source) {
    if(source == null)
      return;
    if(source.getGdpr() == null)
      return;
    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("gdpr", source.getGdpr());
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.Regs mapRtb24RegstoRtb3Regs(Regs regs);

  @InheritInverseConfiguration
  net.media.openrtb24.request.Geo mapRtb24GeotoRtb3Geo(Geo geo);

  @AfterMapping
  default void mapSourceTo24(@MappingTarget net.media.openrtb24.request.Source target, Source source) {
    if(source == null)
      return;
    if(source.getTs() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ts", source.getTs());
    }
    if(source.getDs() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ds", source.getDs());
    }
    if(source.getDsmap() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("dsMap", source.getDsmap());
    }
    if(source.getCert() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cert", source.getCert());
    }
    if(source.getDigest() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("digest", source.getDigest());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.Source mapRtb3SourcetoRtb24Source(Source source);

  @AfterMapping
  default void mapSiteTo24(@MappingTarget net.media.openrtb24.request.Site target, Site source) {
    if(source == null)
      return;
    if(source.getCattax() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getAmp() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getAmp());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.Site mapRtb3SitetoRtb24Site(Site site);

  @AfterMapping
  default void mapAppTo24(@MappingTarget net.media.openrtb24.request.App target, App source) {
    if(source == null)
      return;
    if(source.getCattax() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getStoreid() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getStoreid());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.App mapRtb24ApptoRtb3App(App app);

  @AfterMapping
  default void mapContentTo24(@MappingTarget net.media.openrtb24.request.Content target, Content source) {
    if(source == null)
      return;
    if(source.getCattax() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb24.request.Content mapRtb3ContenttoRtb24Content(Content content);

  @AfterMapping
  default void mapProducerTo24(@MappingTarget net.media.openrtb24.request.Producer target, Producer source) {
    if(source == null)
      return;
    if(source.getCattax() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }

  @Mappings({

  })
  net.media.openrtb24.request.Producer mapRtb3ProducertoRtb24Producer(Producer producer);

  @AfterMapping
  default void mapUser24(@MappingTarget net.media.openrtb24.request.User target, User source) {
    if(source == null)
      return;
    if(source.getConsent() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("consent", source.getConsent());
    }
  }

  @Mappings({

  })
  net.media.openrtb24.request.User mapRtb3UsertoRtb24User(User user);

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
  void updateRequestFromBidRequest(BidRequest bidRequest, @MappingTarget Request request);

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
    Request request = MAPPER.mapRequestToBidRequest(bidRequest);
    System.out.println(request.getPack());
    /*Imp imp = new Imp();
    imp.setId("1");
    Item item = a.map(imp);
    System.out.println(item.getId());
    imp.setId("2");
    a.updateItemFromImp(imp, item);
    System.out.println(item.getId());*/
  }
}