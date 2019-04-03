package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Producer;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ProducerToProducerConverter implements Converter<Producer, net.media.openrtb25.request.Producer> {
  @Override
  public net.media.openrtb25.request.Producer map(Producer source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Producer producer1 = new net.media.openrtb25.request.Producer();

    enhance( source, producer1, config );

    return producer1;
  }

  @Override
  public void enhance(Producer source, net.media.openrtb25.request.Producer target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    target.setDomain( source.getDomain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap( map, config ) );
    }
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }
}
