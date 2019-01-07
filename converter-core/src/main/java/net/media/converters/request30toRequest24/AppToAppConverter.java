package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.App;
import net.media.openrtb3.Content;
import net.media.openrtb3.Publisher;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AppToAppConverter implements Converter<App,net.media.openrtb24.request.App> {

  Converter<Publisher, net.media.openrtb24.request.Publisher> publisherPublisherConverter;
  Converter<Content, net.media.openrtb24.request.Content> contentContentConverter;

  @Override
  public net.media.openrtb24.request.App map(App source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.App app1 = new net.media.openrtb24.request.App();

    inhance( source, app1, config );

    return app1;
  }

  @Override
  public void inhance(App source, net.media.openrtb24.request.App target, Config config) {
    if(source == null)
      return;
    target.setSectioncat( source.getSectcat() );
    target.setPrivacypolicy( source.getPrivpolicy() );
    target.setPublisher( publisherPublisherConverter.map( source.getPub(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setBundle( source.getBundle() );
    target.setDomain( source.getDomain() );
    target.setStoreurl( source.getStoreurl() );
    target.setCat( source.getCat() );
    target.setPagecat( source.getPagecat() );
    target.setVer( source.getVer() );
    target.setPaid( source.getPaid() );
    target.setContent( contentContentConverter.map( source.getContent(), config ) );
    target.setKeywords( source.getKeywords() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getStoreid() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getStoreid());
    }
  }
}
