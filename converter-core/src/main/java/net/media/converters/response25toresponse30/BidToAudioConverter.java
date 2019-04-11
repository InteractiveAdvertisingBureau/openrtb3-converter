package net.media.converters.response25toresponse30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audio;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToAudioConverter implements Converter<Bid, Audio> {

  @Override
  public Audio map(Bid source, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Audio audio = new Audio();
    enhance(source, audio, config, converterProvider);
    return audio;
  }

  @Override
  public void enhance(Bid source, Audio target, Config config, Provider converterProvider) throws OpenRtbConverterException{

    if (isNull(source) || isNull(target)) {
      return;
    }

    target.setAdm( source.getAdm() );

    if(nonNull(source.getApi())) { target.setApi(new ArrayList<>(Arrays.asList(source.getApi()))); }
    target.setCurl(source.getNurl());

    if (nonNull(source.getExt())) {
      try {
        Map<String, Object> ext = source.getExt();
        target.setCtype((Integer) ext.get("ctype"));
        source.getExt().remove("ctype");
        target.setDur((Integer) ext.get("dur"));
        source.getExt().remove("dur");
        target.setMime((List<String>) ext.get("mime"));
        source.getExt().remove("mime");
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while type casting in bid.ext", e);
      }
    }
  }
}
