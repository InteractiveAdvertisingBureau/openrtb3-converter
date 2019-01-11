package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Regs;

import java.util.HashMap;
import java.util.Map;

public class RegsToRegsConverter implements Converter<Regs, net.media.openrtb25.request.Regs> {
  @Override
  public net.media.openrtb25.request.Regs map(Regs source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Regs regs1 = new net.media.openrtb25.request.Regs();

    enhance( source, regs1, config );

    return regs1;
  }

  @Override
  public void enhance(Regs source, net.media.openrtb25.request.Regs target, Config config) {
    if(source == null)
      return;
    target.setCoppa( source.getCoppa() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getGdpr() == null)
      return;
    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("gdpr", source.getGdpr());
  }
}
