package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToAudioConverter implements Converter<Bid, Audio> {
  @Override
  public Audio map(Bid source, Config config) {
    if (isNull(source)) {
      return null;
    }
    Audio audio = new Audio();
    inhance(source, audio, config);
    return null;
  }

  @Override
  public void inhance(Bid source, Audio target, Config config) {

    if (isNull(source) || isNull(target)) {
      return;
    }

    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
    else {
      target.setExt( null );
    }
    target.setAdm( source.getAdm() );
    List<Integer> list = source.getApi();
    if ( list != null ) {
      target.setApi(new ArrayList<>(list) );
    }
    else {
      target.setApi( null );
    }
    target.setCurl(source.getNurl());

    if (nonNull(source.getExt())) {
      Map<String, Object> ext = source.getExt();
      target.setCtype((Integer) ext.get("ctype"));
      target.setDur((Integer) ext.get("dur"));
      target.setMime((List<String>) ext.get("mime"));
    }
  }
}
