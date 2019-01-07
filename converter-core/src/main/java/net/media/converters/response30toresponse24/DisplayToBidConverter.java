package net.media.converters.response30toresponse24;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Native;

import java.io.IOException;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class DisplayToBidConverter implements Converter<Display,Bid> {

  Converter<Native,NativeResponse> nativeBidConverter;

  public DisplayToBidConverter(Converter<Native,NativeResponse> nativeBidConverter){
    this.nativeBidConverter = nativeBidConverter;
  }

  public Bid map(Display source, Config config){
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    inhance(source,bid,config);
    return bid;
  }

  public  void inhance(Display source, Bid target, Config config){
    if(isNull(source) || isNull(target) || isNull(config))
      return ;

    if(nonNull(target) && nonNull(source)){
      //bid.setAdm(display.getAdm());
      ObjectMapper mapper = new ObjectMapper();
      target.setH(source.getH());
      target.setW(source.getW());
      target.setWratio(source.getWratio());
      target.setHratio(source.getHratio());
      target.setApi(source.getApi());
      if(isNull(target.getExt())){
        target.setExt(new HashMap<>());
      }
      target.getExt().put("ctype",source.getCtype());
      target.getExt().put("banner",source.getBanner());
      target.getExt().put("native",source.get_native());
      target.getExt().put("priv",source.getPriv());
      target.getExt().put("curl",source.getCurl());
      if (isEmpty(target.getNurl())) {
        target.setNurl(source.getCurl());
      }
      target.getExt().put("event",source.getEvent());
      target.getExt().put("mime",source.getMime());
      if(nonNull(source.getExt()))
        target.getExt().putAll(source.getExt());

      if (config.getAdType() == AdType.NATIVE) {
        if (nonNull(source.get_native())) {
          NativeResponse _native = nativeBidConverter.map(source.get_native(),config);
          target.setAdm(_native);
        }
        else if (nonNull(source.getAdm())){
          try {
            Native native3 = mapper.readValue((String) source.getAdm(), Native.class);
            NativeResponse _native = nativeBidConverter.map(native3,config);
            target.setAdm(_native);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      else {
        if (nonNull(source.getBanner())) {
          target.setAdm(source.getBanner());
        }
        else if (nonNull(source.getAdm())){
          target.setAdm(source.getAdm());
        }
      }
      if(nonNull(source.getExt()))
        target.getExt().putAll(source.getExt());
    }
  }
}
