package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Producer;

import java.util.HashMap;
import java.util.Map;

public class ProducerToProducerConverter implements Converter<Producer, net.media.openrtb24.request.Producer> {
  @Override
  public net.media.openrtb24.request.Producer map(Producer source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Producer producer1 = new net.media.openrtb24.request.Producer();

    producer1.setId( source.getId() );
    producer1.setName( source.getName() );
    producer1.setCat( source.getCat() );
    producer1.setDomain( source.getDomain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      producer1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, producer1, config );

    return producer1;
  }

  @Override
  public void inhance(Producer source, net.media.openrtb24.request.Producer target, Config config) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }
}
