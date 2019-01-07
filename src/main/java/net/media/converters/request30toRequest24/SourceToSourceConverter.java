package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Source;

import java.util.HashMap;
import java.util.Map;

public class SourceToSourceConverter implements Converter<Source, net.media.openrtb24.request.Source> {
  @Override
  public net.media.openrtb24.request.Source map(Source source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Source source1 = new net.media.openrtb24.request.Source();

    inhance( source, source1, config );

    return source1;
  }

  @Override
  public void inhance(Source source, net.media.openrtb24.request.Source target, Config config) {
    if(source == null)
      return;
    target.setTid( source.getTid() );
    target.setPchain( source.getPchain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getTs() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ts", source.getTs());
    }
    if(source.getDs() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ds", source.getDs());
    }
    if(source.getDsmap() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("dsMap", source.getDsmap());
    }
    if(source.getCert() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cert", source.getCert());
    }
    if(source.getDigest() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("digest", source.getDigest());
    }

    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("fd")) {
      target.setFd((Integer) source.getExt().get("fd"));
      target.getExt().remove("fd");
    }
  }
}
