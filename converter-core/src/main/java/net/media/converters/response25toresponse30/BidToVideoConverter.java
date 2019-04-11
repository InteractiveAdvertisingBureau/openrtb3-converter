package net.media.converters.response25toresponse30;


import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Video;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToVideoConverter implements Converter<Bid, Video> {

  @Override
  public Video map(Bid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Video video = new Video();
    enhance(source, video, config, converterProvider);
    return video;
  }

  @Override
  public void enhance(Bid source, Video target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    if (source == null || target == null) {
      return;
    }
    target.setAdm( source.getAdm() );
    if(nonNull(source.getApi())) {
      List<Integer> api = new ArrayList<>();
      api.add(source.getApi());
      target.setApi(api);
    }
    target.setCurl(source.getNurl());

    if (nonNull(source.getExt())) {
      Map<String, Object> ext = source.getExt();
      try {
        target.setCtype((Integer) ext.get("ctype"));
        source.getExt().remove("ctype");
        target.setDur((Integer) ext.get("dur"));
        source.getExt().remove("dur");
        target.setMime((List<String>) ext.get("mime"));
        source.getExt().remove("mime");
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while type casting bid.ext content", e);
      }
    }
  }


}
