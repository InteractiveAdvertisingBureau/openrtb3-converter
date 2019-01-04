package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Publisher;

import java.util.HashMap;
import java.util.Map;

public class PublisherToPublisherConverter implements Converter<Publisher, net.media.openrtb3.Publisher> {
  @Override
  public net.media.openrtb3.Publisher map(Publisher source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Publisher publisher1 = new net.media.openrtb3.Publisher();

    publisher1.setId( source.getId() );
    publisher1.setName( source.getName() );
    publisher1.setDomain( source.getDomain() );
    publisher1.setCat( source.getCat() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      publisher1.setExt( new HashMap<String, Object>( map ) );
    }

    return publisher1;
  }

  @Override
  public void inhance(Publisher source, net.media.openrtb3.Publisher target, Config config) {

  }
}
