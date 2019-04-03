package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.Source;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajat.go on 03/01/19.
 */
public class SourceToSourceConverter implements Converter<Source, net.media.openrtb3.Source> {
  @Override
  public net.media.openrtb3.Source map(Source source, Config config, Provider<Conversion, Converter> converterProvider) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Source source1 = new net.media.openrtb3.Source();

    enhance( source, source1, config, converterProvider);

    return source1;
  }

  @Override
  public void enhance(Source source, net.media.openrtb3.Source target, Config config, Provider<Conversion, Converter> converterProvider) {
    if(source == null || target == null)
      return;
    target.setTid( source.getTid() );
    target.setPchain( source.getPchain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
    if(source.getFd() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("fd", source.getFd());
    }
    if(source.getExt() == null)
      return;
    target.setTs((Integer) source.getExt().get("ts"));
    target.setDs((String) source.getExt().get("ds"));
    target.setDsmap((String) source.getExt().get("dsMap"));
    target.setCert((String) source.getExt().get("cert"));
    target.setDigest((String) source.getExt().get("digest"));
    target.getExt().remove("ts");
    target.getExt().remove("ds");
    target.getExt().remove("dsMap");
    target.getExt().remove("cert");
    target.getExt().remove("digest");

  }
}
