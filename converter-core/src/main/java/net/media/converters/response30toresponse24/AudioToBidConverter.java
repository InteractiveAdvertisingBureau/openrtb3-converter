package net.media.converters.response30toresponse24;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AudioToBidConverter implements Converter<Audio,Bid> {

  public AudioToBidConverter(){

  }

  public Bid map(Audio source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    inhance(source,bid,config);
    return bid;
  }

  public  void inhance(Audio source, Bid target, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;

    target.setAdm(source.getAdm());
    target.setApi(source.getApi());
    if(isNull(target.getExt())){
      target.setExt(new HashMap<>());
    }
    target.getExt().put("ctype",source.getCtype());
    target.getExt().put("dur",source.getDur());
    target.getExt().put("curl",source.getCurl());
    if (isEmpty(target.getNurl())) {
      target.setNurl(source.getCurl());
    }
    target.getExt().put("mime",source.getMime());
    if(nonNull(source.getExt()))
      target.getExt().putAll(source.getExt());

  }
}
