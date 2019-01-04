package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.App;
import net.media.openrtb24.request.Content;
import net.media.openrtb24.request.Publisher;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AppToAppConverter implements Converter<App, net.media.openrtb3.App> {

  private Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb3.Content> contentContentConverter;

  @Override
  public net.media.openrtb3.App map(App source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.App app1 = new net.media.openrtb3.App();

    app1.setPrivpolicy( source.getPrivacypolicy() );
    app1.setSectcat( source.getSectioncat() );
    app1.setPub( publisherPublisherConverter.map( source.getPublisher(), config ) );
    app1.setId( source.getId() );
    app1.setName( source.getName() );
    app1.setContent( contentContentConverter.map( source.getContent(), config ) );
    app1.setDomain( source.getDomain() );
    app1.setCat( source.getCat() );
    app1.setPagecat( source.getPagecat() );
    app1.setKeywords( source.getKeywords() );
    app1.setBundle( source.getBundle() );
    app1.setStoreurl( source.getStoreurl() );
    app1.setVer( source.getVer() );
    app1.setPaid( source.getPaid() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      app1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, app1, config );

    return app1;
  }

  @Override
  public void inhance(App source, net.media.openrtb3.App target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setStoreid((String) source.getExt().get("storeid"));
    target.getExt().remove("cattax");
    target.getExt().remove("storeid");
  }
}
