package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

/**
 * @author shiva.b
 */
public class BidToAdConverter implements Converter<Bid, Ad> {

  @Override
  public Ad map(Bid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Ad ad = new Ad();
    enhance(source, ad, config, converterProvider);
    return ad;
  }

  @Override
  public void enhance(Bid source, Ad target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    if (isNull(source) || isNull(target)) {
      return;
    }
    target.setId( source.getCrid() );
    target.setAdomain(Utils.copyCollection(source.getAdomain(),config));
    if(nonNull(source.getBundle())){
      List<String> bundle = new ArrayList<>();
      bundle.add(source.getBundle());
      target.setBundle(bundle);
    }
    target.setIurl( source.getIurl() );
    target.setCat(Utils.copyCollection(source.getCat(),config));
    target.setLang(source.getLanguage());
    target.setAttr(Utils.copyCollection(source.getAttr(),config));
    target.setMrating(source.getQagmediarating());
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
    try {
      if (nonNull(source.getExt())) {
        Map<String, Object> ext = source.getExt();
        target.setSecure((Integer) ext.get("secure"));
        target.getExt().remove("secure");
        target.setInit((Integer) ext.get("init"));
        target.getExt().remove("init");
        target.setLastmod((Integer) ext.get("lastMod"));
        target.getExt().remove("lastMod");
        target.setMrating((Integer) ext.get("mrating"));
        target.getExt().remove("mrating");
        if(ext.containsKey("cattax")) {
          target.setCattax((Integer) ext.get("cattax"));
        }
        else {
          target.setCattax(DEFAULT_CATTAX_TWODOTX);
        }
        target.getExt().remove("cattax");
      }
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("error while type casting ext in bid", e);
    }

    switch (config.getAdType(source.getId())) {
      case BANNER:
      case NATIVE:
        Converter<Bid, Display> bidDisplayConverter = converterProvider.fetch(new Conversion<>(Bid.class, Display.class));
        target.setDisplay(bidDisplayConverter.map(source, config, converterProvider));
        break;
      case VIDEO:
        Converter<Bid, Video> bidVideoConverter = converterProvider.fetch(new Conversion<>(Bid.class, Video.class));
        target.setVideo(bidVideoConverter.map(source, config, converterProvider));
        break;
      case AUDIO:
        Converter<Bid, Audio> bidAudioConverter = converterProvider.fetch(new Conversion<>(Bid.class, Audio.class));
        target.setAudio(bidAudioConverter.map(source, config, converterProvider));
        break;
      case AUDIT:
        Converter<Bid, Audit> bidAuditConverter = converterProvider.fetch(new Conversion<>(Bid.class, Audit.class));
        target.setAudit(bidAuditConverter.map(source, config, converterProvider));
        break;
    }
  }
}
