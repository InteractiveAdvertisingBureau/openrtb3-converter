package net.media.converters.response25toresponse30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToAdConverter implements Converter<Bid, Ad> {

  private Converter<Bid, Display> bidDisplayConverter;

  private Converter<Bid, Audio> bidAudioConverter;

  private Converter<Bid, Video> bidVideoConverter;

  private Converter<Bid, Audit> bidAuditConverter;

  public BidToAdConverter(
    Converter<Bid, Display> bidDisplayConverter,
    Converter<Bid, Audio> bidAudioConverter,
    Converter<Bid, Video> bidVideoConverter,
    Converter<Bid, Audit> bidAuditConverter) {
    this.bidAudioConverter = bidAudioConverter;
    this.bidAuditConverter = bidAuditConverter;
    this.bidDisplayConverter = bidDisplayConverter;
    this.bidVideoConverter = bidVideoConverter;
  }

  @Override
  public Ad map(Bid source, Config config) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Ad ad = new Ad();
    enhance(source, ad, config);
    return ad;
  }

  @Override
  public void enhance(Bid source, Ad target, Config config) throws OpenRtbConverterException{
    if (isNull(source) || isNull(target)) {
      return;
    }
    target.setId( source.getCrid() );
    target.setAdomain(Utils.copyList(source.getAdomain(),config));
    if(nonNull(source.getBundle())){
      List<String> bundle = new ArrayList<>();
      bundle.add(source.getBundle());
      target.setBundle(bundle);
    }
    target.setIurl( source.getIurl() );
    target.setCat(Utils.copySet(source.getCat(),config));
    target.setLang(source.getLanguage());
    target.setAttr(Utils.copyList(source.getAttr(),config));
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map));
    }
    try {
      if (nonNull(source) && nonNull(source.getExt())) {
        Map<String, Object> ext = source.getExt();
        target.setSecure((Integer) ext.get("secure"));
        target.setInit((Integer) ext.get("init"));
        target.setLastmod((Integer) ext.get("lastMod"));
        target.setMrating((Integer) ext.get("mrating"));
        target.setCattax((Integer) ext.get("cattax"));
      }
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext in bid", e);
    }
    switch (config.getAdType()) {
      case BANNER:
      case NATIVE:
        target.setDisplay(bidDisplayConverter.map(source, config));
        break;
      case VIDEO:
        target.setVideo(bidVideoConverter.map(source, config));
        break;
      case AUDIO:
        target.setAudio(bidAudioConverter.map(source, config));
        break;
      case AUDIT:
        target.setAudit(bidAuditConverter.map(source, config));
        break;
    }
  }
}
