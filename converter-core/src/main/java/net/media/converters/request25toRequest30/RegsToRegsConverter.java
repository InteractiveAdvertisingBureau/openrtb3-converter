package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Regs;

import java.util.HashMap;
import java.util.Map;

public class RegsToRegsConverter implements Converter<Regs, net.media.openrtb3.Regs> {

  @Override
  public net.media.openrtb3.Regs map(Regs source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Regs regs1 = new net.media.openrtb3.Regs();

    enhance( source, regs1, config );

    return regs1;
  }

  @Override
  public void enhance(Regs source, net.media.openrtb3.Regs target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setCoppa( source.getCoppa() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getExt() == null)
      return;
    try {
      target.setGdpr((Integer) source.getExt().get("gdpr"));
      target.getExt().remove("gdpr");
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Regs", e);    }
  }
}
