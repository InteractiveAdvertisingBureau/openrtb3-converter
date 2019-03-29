package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Publisher;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class PublisherToPublisherConverter implements Converter<Publisher, net.media.openrtb3.Publisher> {
  @Override
  public net.media.openrtb3.Publisher map(Publisher source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Publisher publisher1 = new net.media.openrtb3.Publisher();

    enhance( source, publisher1, config );

    return publisher1;
  }

  @Override
  public void enhance(Publisher source, net.media.openrtb3.Publisher target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setDomain( source.getDomain() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
