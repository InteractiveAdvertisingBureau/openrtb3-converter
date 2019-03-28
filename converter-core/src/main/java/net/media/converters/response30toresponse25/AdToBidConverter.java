package net.media.converters.response30toresponse25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;
import net.media.utils.Utils;

import java.util.HashMap;

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

  public Bid map(Ad source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    enhance(source,bid,config);
    return bid;
  }

  public void enhance(Ad source, Bid target, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;

    target.setCrid(source.getId());

    target.setAdomain(Utils.copyList(source.getAdomain(),config));
    if(nonNull(source.getBundle()) && source.getBundle().size()>0)
      target.setBundle(source.getBundle().get(0));
    target.setIurl( source.getIurl() );
    target.setCat(Utils.copySet(source.getCat(),config));
    target.setAttr(Utils.copyList(source.getAttr(),config));
    target.setLanguage(source.getLang());

    if(isNull(target.getExt()))
      target.setExt(new HashMap<>());
    if (nonNull(source.getExt())) {
      target.getExt().putAll(source.getExt());
    }
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
    target.setQagmediarating(source.getMrating());
    AdType adType = config.getAdType();
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayBidConverter.enhance(source.getDisplay(),target,config);
        break;
      case VIDEO:
        videoBidConverter.enhance(source.getVideo(),target,config);
        break;
      case AUDIO:
        audioBidConverter.enhance(source.getAudio(),target,config);
        break;
      case AUDIT:
        auditBidConverter.enhance(source.getAudit(),target,config);
        break;
    }

  }
}
