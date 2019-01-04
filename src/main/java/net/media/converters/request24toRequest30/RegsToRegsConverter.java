package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Regs;

import java.util.HashMap;
import java.util.Map;

public class RegsToRegsConverter implements Converter<Regs, net.media.openrtb3.Regs> {

  @Override
  public net.media.openrtb3.Regs map(Regs source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Regs regs1 = new net.media.openrtb3.Regs();

    regs1.setCoppa( source.getCoppa() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      regs1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, regs1, config );

    return regs1;
  }

  @Override
  public void inhance(Regs source, net.media.openrtb3.Regs target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setGdpr((Integer) source.getExt().get("gdpr"));
    target.getExt().remove("gdpr");
  }
}
