package net.media.converters.response25toresponse30;


import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToVideoConverter implements Converter<Bid, Video> {

  @Override
  public Video map(Bid source, Config config) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Video video = new Video();
    inhance(source, video, config);
    return video;
  }

  @Override
  public void inhance(Bid source, Video target, Config config) throws OpenRtbConverterException{
    if (source == null || target == null) {
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
        target.setDur((Integer) ext.get("dur"));
        target.setMime((List<String>) ext.get("mime"));
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while type casting bid.ext content", e);
      }
    }
  }


}
