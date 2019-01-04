package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Producer;

import java.util.HashMap;
import java.util.Map;

public class ProducerToProducerConverter implements Converter<Producer, net.media.openrtb3.Producer> {
  @Override
  public net.media.openrtb3.Producer map(Producer source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Producer producer1 = new net.media.openrtb3.Producer();

    producer1.setId( source.getId() );
    producer1.setName( source.getName() );
    producer1.setDomain( source.getDomain() );
    producer1.setCat( source.getCat() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      producer1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, producer1, config );

    return producer1;
  }

  @Override
  public void inhance(Producer source, net.media.openrtb3.Producer target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }
}
