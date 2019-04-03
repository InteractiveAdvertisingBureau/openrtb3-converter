package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.App;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Publisher;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class AppToAppConverter implements Converter<App, net.media.openrtb3.App> {

  @Override
  public net.media.openrtb3.App map(App source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.App app1 = new net.media.openrtb3.App();

    enhance( source, app1, config, converterProvider);

    return app1;
  }

  @Override
  public void enhance(App source, net.media.openrtb3.App target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    target.setPrivpolicy( source.getPrivacypolicy() );
    target.setSectcat( Utils.copyCollection(source.getSectioncat(), config) );
    Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = converterProvider.fetch(new Conversion
            (Publisher.class, net.media.openrtb3.Publisher.class));
    Converter<Content, net.media.openrtb3.Content> contentContentConverter = converterProvider.fetch(new Conversion
            (Content.class, net.media.openrtb3.Content.class));
    target.setPub( publisherPublisherConverter.map( source.getPublisher(), config, converterProvider ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setContent( contentContentConverter.map( source.getContent(), config, converterProvider) );
    target.setDomain( source.getDomain() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    target.setPagecat( Utils.copyCollection(source.getPagecat(), config) );
    target.setKeywords( source.getKeywords() );
    target.setBundle( source.getBundle() );
    target.setStoreurl( source.getStoreurl() );
    target.setVer( source.getVer() );
    target.setPaid( source.getPaid() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }

    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setStoreid((String) source.getExt().get("storeid"));
    target.getExt().remove("cattax");
    target.getExt().remove("storeid");

  }
}
