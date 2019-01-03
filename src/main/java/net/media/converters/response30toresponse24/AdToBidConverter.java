package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
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
    List<String> adomain = source.getAdomain();
    if ( adomain != null ) {
      target.setAdomain(new ArrayList<>(adomain) );
    }
    else {
      target.setAdomain( null );
    }
    List<String> list1 = source.getBundle();
    target.setBundle(Utils.copyList(source.getBundle()));
    if ( list1 != null ) {
      target.setBundle(new ArrayList<>(list1) );
    }
    else {
      target.setBundle( null );
    }
    target.setIurl( source.getIurl() );
    List<String> set = source.getCat();
    if ( set != null ) {
      target.setCat( new HashSet<>( set ) );
    }
    else {
      target.setCat( null );
    }
    target.setLanguage(source.getLang());
    List<Integer> list2 = source.getAttr();
    if ( list2 != null ) {
      target.setAttr(new ArrayList<>(list2) );
    }
    else {
      target.setAttr( null );
    }
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
    else {
      target.setExt( new HashMap<>());
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
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayToBid(target, source.getDisplay(), adType);
        break;
      case VIDEO:
        videoToBid(target, source.getVideo(), adType);
        break;
      case AUDIO:
        audioToBid(target, source.getAudio(), adType);
        break;
      case AUDIT:
        auditToBid(target, source.getAudit(), adType);
        break;
    }
    displayBidConverter.inhance(source.getDisplay(),target,config);
    videoBidConverter.inhance(source.getVideo(),target,config);
    audioBidConverter.inhance(source.getAudio(),target,config);
    auditBidConverter.inhance(source.getAudit(),target,config);

  }
}
