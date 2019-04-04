package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Publisher;
import net.media.openrtb25.request.Site;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

public class SiteToSiteConverter implements Converter<Site, net.media.openrtb3.Site> {

  @Override
  public net.media.openrtb3.Site map(Site source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Site site1 = new net.media.openrtb3.Site();

    enhance( source, site1, config, converterProvider );

    return site1;
  }

  @Override
  public void enhance(Site source, net.media.openrtb3.Site target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter = converterProvider.fetch(new Conversion
            (Publisher.class, net.media.openrtb3.Publisher.class));
    Converter<Content, net.media.openrtb3.Content> contentContentConverter = converterProvider.fetch(new Conversion
            (Content.class, net.media.openrtb3.Content.class));
    target.setPrivpolicy( source.getPrivacypolicy() );
    target.setSectcat( Utils.copyCollection(source.getSectioncat(), config) );
    target.setPub( publisherPublisherConverter.map( source.getPublisher(), config, converterProvider ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setContent( contentContentConverter.map( source.getContent(), config, converterProvider ) );
    target.setDomain( source.getDomain() );
    if ( source.getCat() != null ) {
      target.setCat( Utils.copyCollection( source.getCat(), config ) );
    }
    target.setPagecat( Utils.copyCollection(source.getPagecat(), config) );
    target.setKeywords( source.getKeywords() );
    target.setPage( source.getPage() );
    target.setRef( source.getRef() );
    target.setSearch( source.getSearch() );
    target.setMobile( source.getMobile() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap( map, config ) );
    }
    if(source.getExt() == null)
      return;
    try {
      if (source.getExt().containsKey("cattax")) {
        target.setCattax((Integer) source.getExt().get("cattax"));
      } else {
        target.setCattax(DEFAULT_CATTAX_TWODOTX);
      }
      target.setAmp((Integer) source.getExt().get("amp"));
      target.getExt().remove("cattax");
      target.getExt().remove("amp");
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Site", e);
    }
  }
}
