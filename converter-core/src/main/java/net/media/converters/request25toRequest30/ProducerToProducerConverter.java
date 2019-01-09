package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Producer;

import java.util.HashMap;
import java.util.Map;

public class ProducerToProducerConverter implements Converter<Producer, net.media.openrtb3.Producer> {
  @Override
  public net.media.openrtb3.Producer map(Producer source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Producer producer1 = new net.media.openrtb3.Producer();

    inhance( source, producer1, config );

    return producer1;
  }

  @Override
  public void inhance(Producer source, net.media.openrtb3.Producer target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setDomain( source.getDomain() );
    target.setCat( source.getCat() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }
}
