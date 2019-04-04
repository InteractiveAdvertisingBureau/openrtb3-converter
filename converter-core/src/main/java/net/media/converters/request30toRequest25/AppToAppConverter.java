package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.App;
import net.media.openrtb3.Content;
import net.media.openrtb3.Publisher;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class AppToAppConverter implements Converter<App,net.media.openrtb25.request.App> {

  @Override
  public net.media.openrtb25.request.App map(App source, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.App app1 = new net.media.openrtb25.request.App();

    enhance( source, app1, config, converterProvider );

    return app1;
  }

  @Override
  public void enhance(App source, net.media.openrtb25.request.App target, Config config, Provider
    converterProvider) throws
    OpenRtbConverterException {
    if(source == null || target == null)
      return;
    target.setSectioncat( Utils.copyCollection(source.getSectcat(), config) );
    target.setPrivacypolicy( source.getPrivpolicy() );
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter =
      converterProvider.fetch(new Conversion(Publisher.class, net.media.openrtb25.request.Publisher.class));
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter =
      converterProvider.fetch(new Conversion(Content.class, net.media.openrtb25.request.Content.class));
    target.setPublisher( publisherPublisherConverter.map( source.getPub(), config, converterProvider ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setBundle( source.getBundle() );
    target.setDomain( source.getDomain() );
    target.setStoreurl( source.getStoreurl() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    target.setPagecat( Utils.copyCollection(source.getPagecat(), config) );
    target.setVer( source.getVer() );
    target.setPaid( source.getPaid() );
    target.setContent( contentContentConverter.map( source.getContent(), config, converterProvider ) );
    target.setKeywords( source.getKeywords() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
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
