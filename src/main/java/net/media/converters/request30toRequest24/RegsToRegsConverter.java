package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Regs;

import java.util.HashMap;
import java.util.Map;

public class RegsToRegsConverter implements Converter<Regs, net.media.openrtb24.request.Regs> {
  @Override
  public net.media.openrtb24.request.Regs map(Regs source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Regs regs1 = new net.media.openrtb24.request.Regs();

    regs1.setCoppa( source.getCoppa() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      regs1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, regs1, config );

    return regs1;
  }

  @Override
  public void inhance(Regs source, net.media.openrtb24.request.Regs target, Config config) {
    if(source == null)
      return;
    if(source.getGdpr() == null)
      return;
    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("gdpr", source.getGdpr());
  }
}
