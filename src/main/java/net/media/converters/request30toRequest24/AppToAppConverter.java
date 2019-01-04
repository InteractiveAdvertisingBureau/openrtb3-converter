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

    app1.setSectioncat( source.getSectcat() );
    app1.setPrivacypolicy( source.getPrivpolicy() );
    app1.setPublisher( publisherPublisherConverter.map( source.getPub(), config ) );
    app1.setId( source.getId() );
    app1.setName( source.getName() );
    app1.setBundle( source.getBundle() );
    app1.setDomain( source.getDomain() );
    app1.setStoreurl( source.getStoreurl() );
    app1.setCat( source.getCat() );
    app1.setPagecat( source.getPagecat() );
    app1.setVer( source.getVer() );
    app1.setPaid( source.getPaid() );
    app1.setContent( contentContentConverter.map( source.getContent(), config ) );
    app1.setKeywords( source.getKeywords() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      app1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, app1, config );

    return app1;
  }

  @Override
  public void inhance(App source, net.media.openrtb24.request.App target, Config config) {
    if(source == null)
      return;
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
