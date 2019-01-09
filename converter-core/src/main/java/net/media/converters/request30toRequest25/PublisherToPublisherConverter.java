package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Publisher;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class PublisherToPublisherConverter implements Converter<Publisher, net.media.openrtb24.request.Publisher> {
  @Override
  public net.media.openrtb24.request.Publisher map(Publisher source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Publisher publisher1 = new net.media.openrtb24.request.Publisher();

    inhance( source, publisher1, config );

    return publisher1;
  }

  @Override
  public void inhance(Publisher source, net.media.openrtb24.request.Publisher target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setCat( Utils.copyList(source.getCat(), config) );
    target.setDomain( source.getDomain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
