package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.enums.AdType;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.*;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AdToBidConverter implements Converter<Ad, Bid> {

  public Bid map(Ad source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) return null;
    Bid bid = new Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  public void enhance(Ad source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    Converter<Display, Bid>displayBidConverter = converterProvider.fetch(new Conversion<>(Display.class,
      Bid.class));
    Converter<Video, Bid> videoBidConverter = converterProvider.fetch(new Conversion<>(Video.class,
      Bid.class));
    Converter<Audio, Bid> audioBidConverter = converterProvider.fetch(new Conversion<>(Audio.class,
      Bid.class));

    target.setCrid(source.getId());

    target.setAdomain(Utils.copyCollection(source.getAdomain(), config));
    if (nonNull(source.getBundle()) && source.getBundle().size() > 0)
      target.setBundle(source.getBundle().iterator().next());
    target.setIurl(source.getIurl());
    target.setCat(Utils.copyCollection(source.getCat(), config));
    target.setAttr(Utils.copyCollection(source.getAttr(), config));
    target.setLanguage(source.getLang());

    if (isNull(target.getExt())) target.setExt(new HashMap<>());
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
      target.getExt().put("lastmod", source.getLastmod());
    }
    if (nonNull(source.getCattax())) {
      target.getExt().put("cattax", source.getCattax());
    }
    if (nonNull(source.getAudit())) {
      target.getExt().put("audit", source.getAudit());
    }
    target.setQagmediarating(source.getMrating());
    AdType adType = config.getAdType(target.getId());
    switch (adType) {
      case BANNER:
      case NATIVE:
        displayBidConverter.enhance(source.getDisplay(), target, config, converterProvider);
        break;
      case VIDEO:
        videoBidConverter.enhance(source.getVideo(), target, config, converterProvider);
        break;
      case AUDIO:
        audioBidConverter.enhance(source.getAudio(), target, config, converterProvider);
        break;
    }
  }
}
