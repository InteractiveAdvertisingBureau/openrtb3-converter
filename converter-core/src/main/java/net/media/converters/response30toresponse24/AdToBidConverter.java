package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.*;
import net.media.utils.Utils;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AdToBidConverter implements Converter<Ad,Bid>{

  Converter<Display,Bid> displayBidConverter;
  Converter<Video,Bid> videoBidConverter;
  Converter<Audio,Bid> audioBidConverter;
  Converter<Audit,Bid> auditBidConverter;

  public AdToBidConverter(Converter<Display,Bid> displayBidConverter,Converter<Video,Bid> videoBidConverter,Converter<Audio,Bid>  audioBidConverter,Converter<Audit,Bid> auditBidConverter){
    this.displayBidConverter =displayBidConverter;
    this.videoBidConverter = videoBidConverter;
    this.audioBidConverter  = audioBidConverter;
    this.auditBidConverter = auditBidConverter;
  }

  public Bid map(Ad source, Config config){
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    inhance(source,bid,config);
    return bid;
  }

  public void inhance(Ad source, Bid target, Config config){
    if(isNull(source) || isNull(target) || isNull(config))
      return ;

    target.setCrid(source.getId());

    target.setAdomain(Utils.copyList(source.getAdomain(),config));
    target.setBundle(Utils.copyList(source.getBundle(),config));
    target.setIurl( source.getIurl() );
    target.setCat(Utils.copyList(source.getCat(),config));
    target.setAttr(Utils.copyList(source.getAttr(),config));
    target.setLanguage(source.getLang());
    target.setExt(Utils.copyMap(source.getExt(),config));
    if(isNull(target.getExt()))
      target.setExt(new HashMap<>());
    if (nonNull(source.getSecure())) {
      target.getExt().put("secure", source.getSecure());
    }
    if (nonNull(source.getInit())) {
      target.getExt().put("init", source.getInit());
    }
    if (nonNull(source.getLastmod())) {
      target.getExt().put("lastMod", source.getLastmod());
    }
    if (nonNull(source.getMrating())) {
      target.getExt().put("mrating", source.getMrating());
    }
    if (nonNull(source.getCattax())) {
      target.getExt().put("cattax", source.getCattax());
    }
    AdType adType = config.getAdType();
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayBidConverter.inhance(source.getDisplay(),target,config);
        break;
      case VIDEO:
        videoBidConverter.inhance(source.getVideo(),target,config);
        break;
      case AUDIO:
        audioBidConverter.inhance(source.getAudio(),target,config);
        break;
      case AUDIT:
        auditBidConverter.inhance(source.getAudit(),target,config);
        break;
    }

  }
}
