package net.media.mapper;

import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.*;
import net.media.openrtb3.Context;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mapper(uses = ImpItemConverter.class)
public interface BidRequestMapper {

  BidRequestMapper bidReqMapper = Mappers.getMapper(BidRequestMapper.class);

  @AfterMapping
  default void mapWseatAndExt(@MappingTarget Request target, BidRequest source) {
    if(source == null)
      return;
    if (source.getWseat()!=null && source.getWseat().size()>0){
      target.setWseat(1);
      target.setSeat(source.getWseat());
    } else {
      target.setWseat(0);
      target.setSeat(source.getBseat());
    }
    if(target.getExt() == null)
      return;
    target.getExt().remove("cattax");
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("dooh")) {
      if(target.getContext() == null)
        target.setContext(new Context());
      Dooh dooh = (Dooh)source.getExt().get("dooh");
      target.getContext().setDooh(dooh);
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
    @Mapping(source = "bidRequest.imp", target = "item"),
  })
  Request mapRequestToBidRequest(BidRequest bidRequest);

  @AfterMapping
  default void mapSourceExt(@MappingTarget Source target, net.media.openrtb25.request.Source source) {
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
  })
  Source mapRtb24SourcetoRtb3Source(net.media.openrtb25.request.Source source);

  @AfterMapping
  default void mapSiteExt(@MappingTarget Site target, net.media.openrtb25.request.Site source) {
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
  Site mapRtb24SitetoRtb3Site(net.media.openrtb25.request.Site site);

  @AfterMapping
  default void mapAppExt(@MappingTarget App target, net.media.openrtb25.request.App source) {
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
  App mapRtb24ApptoRtb3App(net.media.openrtb25.request.App app);

  @AfterMapping
  default void mapContentExt(@MappingTarget Content target, net.media.openrtb25.request.Content source) {
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
  Content mapRtb24ContenttoRtb3Content(net.media.openrtb25.request.Content content);

  @AfterMapping
  default void mapProducerExt(@MappingTarget Producer target, net.media.openrtb25.request.Producer source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }

  @Mappings({

  })
  Producer mapRtb24ProducertoRtb3Producer(net.media.openrtb25.request.Producer producer);

  @AfterMapping
  default void mapUserExt(@MappingTarget User target, net.media.openrtb25.request.User source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setConsent((String) source.getExt().get("consent"));
    target.getExt().remove("consent");
  }

  @Mappings({

  })
  User mapRtb24UsertoRtb3User(net.media.openrtb25.request.User user);

  @AfterMapping
  default void mapRegsExt(@MappingTarget Regs target, net.media.openrtb25.request.Regs source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setGdpr((Integer) source.getExt().get("gdpr"));
    target.getExt().remove("gdpr");
  }

  @Mappings({

  })
  Regs mapRtb24RegstoRtb3Regs(net.media.openrtb25.request.Regs regs);

  @Mappings({
    @Mapping(source = "ipservice", target = "ipserv"),
    @Mapping(source = "accuracy", target = "accur")
  })
  Geo mapRtb24GeotoRtb3Geo(net.media.openrtb25.request.Geo geo);

  @AfterMapping
  default void mapDeviceExt(@MappingTarget Device target, net.media.openrtb25.request.Device source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setXff((String) source.getExt().get("xff"));
    target.getExt().remove("xff");
    target.setIptr((Integer) source.getExt().get("iptr"));
    target.getExt().remove("iptr");
    target.setIptr((Integer) source.getExt().get("mccmncsim"));
    target.getExt().remove("mccmncsim");
  }

  @Mappings({
    @Mapping(source = "devicetype", target = "type"),
    @Mapping(source = "language", target = "lang"),
    @Mapping(source = "connectiontype", target = "contype")
  })
  Device mapRtb24DevicetoRtb3Device(net.media.openrtb25.request.Device device);

  @AfterMapping
  default void updateBattrRestriction(@MappingTarget Restrictions restrictions, BidRequest bidRequest) {
    if(bidRequest == null)
      return;
    if(bidRequest.getImp() == null)
      return;
    if(bidRequest.getImp().size() == 0)
      return;
    Set<Integer> battr = new HashSet<>();
    for(Imp imp : bidRequest.getImp()) {
      if(imp.getBanner() != null && imp.getBanner().getBattr() != null) {
        battr.addAll(imp.getBanner().getBattr());
      } else if(imp.getVideo() != null && imp.getVideo().getBattr() != null) {
        battr.addAll(imp.getVideo().getBattr());
      } else if(imp.getNat() != null && imp.getNat().getBattr() != null) {
        battr.addAll(imp.getNat().getBattr());
      }
    }
    if(battr.size()>0) {
      restrictions.setBattr(battr);
    }
    if(bidRequest.getExt() == null)
      return;
    restrictions.setCattax((Integer) bidRequest.getExt().get("cattax"));
    if(bidRequest.getExt().containsKey("restrictionsExt"))
      restrictions.setExt((Map<String, Object>) bidRequest.getExt().get("restrictionsExt"));
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
      target.getExt().put("restrictionsExt", source.getContext().getRestrictions().getExt());
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

    if(source.getContext().getDooh() == null)
      return;

    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("dooh", source.getContext().getDooh());
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
    @Mapping(source = "request.item", target = "imp"),
  })
  BidRequest mapRtb3RequestToRtb24BidRequest(Request request);

  @AfterMapping
  default void mapDeviceTo24(@MappingTarget net.media.openrtb25.request.Device target, Device source) {
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

    if(source.getIptr() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("mccmncsim", source.getMccmncsim());
    }

    if(source.getExt() == null)
      return;

    if(source.getExt().containsKey("didsha1")) {
      target.setDidsha1((String) source.getExt().get("didsha1"));
      source.getExt().remove("didsha1");
    }

    if(source.getExt().containsKey("didmd5")) {
      target.setDidmd5((String) source.getExt().get("didmd5"));
      source.getExt().remove("didmd5");
    }

    if(source.getExt().containsKey("dpidsha1")) {
      target.setDpidsha1((String) source.getExt().get("dpidsha1"));
      source.getExt().remove("dpidsha1");
    }

    if(source.getExt().containsKey("dpidmd5")) {
      target.setDpidmd5((String) source.getExt().get("dpidmd5"));
      source.getExt().remove("dpidmd5");
    }

    if(source.getExt().containsKey("macsha1")) {
      target.setMacsha1((String) source.getExt().get("macsha1"));
      source.getExt().remove("macsha1");
    }

    if(source.getExt().containsKey("macmd5")) {
      target.setMacmd5((String) source.getExt().get("macmd5"));
      source.getExt().remove("macmd5");
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb25.request.Device mapRtb3DevicetoRtb24Device(Device device);

  @AfterMapping
  default void mapRegsTo24(@MappingTarget net.media.openrtb25.request.Regs target, Regs source) {
    if(source == null)
      return;
    if(source.getGdpr() == null)
      return;
    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("gdpr", source.getGdpr());
  }

  @InheritInverseConfiguration
  net.media.openrtb25.request.Regs mapRtb3RegstoRtb24Regs(Regs regs);

  @AfterMapping
  default void mapGeoTo24(@MappingTarget net.media.openrtb25.request.Geo target, Geo source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("regionfips104")) {
      target.setRegionfips104((String) source.getExt().get("regionfips104"));
      source.getExt().remove("regionfips104");
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb25.request.Geo mapRtb3GeotoRtb24Geo(Geo geo);

  @AfterMapping
  default void mapSourceTo24(@MappingTarget net.media.openrtb25.request.Source target, Source source) {
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

    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("fd")) {
      target.setFd((Integer) source.getExt().get("fd"));
      target.getExt().remove("fd");
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb25.request.Source mapRtb3SourcetoRtb24Source(Source source);

  @AfterMapping
  default void mapSiteTo24(@MappingTarget net.media.openrtb25.request.Site target, Site source) {
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
  net.media.openrtb25.request.Site mapRtb3SitetoRtb24Site(Site site);

  @AfterMapping
  default void mapAppTo24(@MappingTarget net.media.openrtb25.request.App target, App source) {
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
  net.media.openrtb25.request.App mapRtb24ApptoRtb3App(App app);

  @AfterMapping
  default void mapContentTo24(@MappingTarget net.media.openrtb25.request.Content target, Content source) {
    if(source == null)
      return;
    if(source.getCattax() == null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }

  @InheritInverseConfiguration
  net.media.openrtb25.request.Content mapRtb3ContenttoRtb24Content(Content content);

  @AfterMapping
  default void mapProducerTo24(@MappingTarget net.media.openrtb25.request.Producer target, Producer source) {
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
  net.media.openrtb25.request.Producer mapRtb3ProducertoRtb24Producer(Producer producer);

  @AfterMapping
  default void mapUser24(@MappingTarget net.media.openrtb25.request.User target, User source) {
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
  net.media.openrtb25.request.User mapRtb3UsertoRtb24User(User user);
}
