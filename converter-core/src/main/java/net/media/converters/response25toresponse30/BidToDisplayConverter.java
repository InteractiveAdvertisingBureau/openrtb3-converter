package net.media.converters.response25toresponse30;

import com.fasterxml.jackson.databind.JavaType;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.AdType;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.Native;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class BidToDisplayConverter implements Converter<Bid, Display> {

  private static final JavaType javaTypeForEventCollection = Utils.getMapper().getTypeFactory()
    .constructCollectionType(Collection.class, Event.class);

  @Override
  public Display map(Bid source, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Display display = new Display();
    enhance(source, display, config, converterProvider);
    return display;
  }

  @Override
  public void enhance(Bid source, Display target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    if (source == null || target == null) {
      return;
    }
    target.setH( source.getH() );
    target.setWratio( source.getWratio() );
    target.setW( source.getW() );
    target.setHratio( source.getHratio() );
    if(nonNull(source.getApi()))
      target.setApi(new ArrayList<>(Arrays.asList(source.getApi())));
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("curl")) {
        target.setCurl((String) source.getExt().get("curl"));
      }
    }
    Converter<NativeResponse, Native> converter = converterProvider.fetch(new Conversion<>(NativeResponse.class, Native.class));
    if (config.getAdType(source.getId()) == AdType.NATIVE) {
      if (source.getAdm() instanceof String) {
        try {
          NativeResponse nativeResponse = Utils.getMapper().readValue((String) source.getAdm(),
            NativeResponse.class);
          Native _native = converter.map(nativeResponse, config, converterProvider);
          target.setAdm(_native);
        } catch (IOException e) {
          throw new OpenRtbConverterException("error while deserializing native response object", e);
        }
      } else {
        try {
          NativeResponse nativeResponse = Utils.getMapper().convertValue(source.getAdm(),NativeResponse.class);
          Native _native = converter.map((NativeResponse) source.getAdm(), config, converterProvider);
          target.setAdm(_native);
        }
        catch (Exception e) {
          throw new OpenRtbConverterException("error while casting adm to native response", e);
        }
      }
    }
    else if (config.getAdType(source.getId()) == AdType.BANNER) {
      target.setAdm(source.getAdm());
    }
    if (nonNull(source.getExt())) {
      try {
        Map<String, Object> ext = source.getExt();
        target.setCtype((Integer) ext.get("ctype"));
        target.setPriv((String) ext.get("priv"));
        target.setMime((String) ext.get("mime"));

        if (config.getAdType(source.getId()) == AdType.BANNER) {
          if (ext.containsKey("banner")) {
            target.setBanner(Banner.HashMapToBanner((Map<String, Object>) ext.get("banner")));
          }
        } else if (config.getAdType(source.getId()) == AdType.NATIVE) {
          if (ext.containsKey("native")) {
            Native _native = null;
            try {
              _native = Utils.getMapper().convertValue(ext.get("native"), Native.class);
              target.set_native(_native);
            } catch (Exception e) {
              throw new OpenRtbConverterException("Error in setting displayConverter.native from " +
                "bid.ext.native", e);
            }
          }
        }
        try {
          target.setEvent(Utils.getMapper().convertValue(ext.get("event"),
            javaTypeForEventCollection));
        } catch (IllegalArgumentException e) {
          throw new OpenRtbConverterException("error while setting display.event from bid.ext" +
            ".event", e);
        }
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while casting contents of bid.ext", e);
      }
    }
  }
}
