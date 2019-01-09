package net.media.converters.response30toresponse25;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb24.response.Bid;
import net.media.openrtb24.response.nativeresponse.NativeResponse;
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

  public Bid map(Display source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    enhance(source,bid,config);
    return bid;
  }

  public  void enhance(Display source, Bid target, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;

    //bid.setAdm(display.getAdm());
    ObjectMapper mapper = new ObjectMapper();
    target.setH(source.getH());
    target.setW(source.getW());
    target.setWratio(source.getWratio());
    target.setHratio(source.getHratio());
    if(nonNull(source.getApi())  && source.getApi().size()>0)
      target.setApi(source.getApi().get(0));
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
          throw new OpenRtbConverterException("error occured while mapping native object", e);
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
