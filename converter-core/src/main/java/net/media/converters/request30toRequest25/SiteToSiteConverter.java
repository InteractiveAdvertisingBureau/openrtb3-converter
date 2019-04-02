package net.media.converters.request30toRequest25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Content;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Site;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SiteToSiteConverter implements Converter<Site, net.media.openrtb25.request.Site> {

  private Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb25.request.Content> contentContentConverter;

  @java.beans.ConstructorProperties({"publisherPublisherConverter", "contentContentConverter"})
  public SiteToSiteConverter(Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter, Converter<Content, net.media.openrtb25.request.Content> contentContentConverter) {
    this.publisherPublisherConverter = publisherPublisherConverter;
    this.contentContentConverter = contentContentConverter;
  }

  @Override
  public net.media.openrtb25.request.Site map(Site source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Site site1 = new net.media.openrtb25.request.Site();

    enhance( source, site1, config );

    return site1;
  }

  @Override
  public void enhance(Site source, net.media.openrtb25.request.Site target, Config config) throws
    OpenRtbConverterException {
    if(source == null)
      return;
    target.setSectioncat( Utils.copyCollection(source.getSectcat(), config) );
    target.setPrivacypolicy( source.getPrivpolicy() );
    target.setPublisher( publisherPublisherConverter.map( source.getPub(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setDomain( source.getDomain() );
    if ( source.getCat() != null ) {
      target.setCat( Utils.copyCollection( source.getCat(), config ) );
    }
    target.setPagecat( Utils.copyCollection(source.getPagecat(), config) );
    target.setPage( source.getPage() );
    target.setRef( source.getRef() );
    target.setSearch( source.getSearch() );
    target.setMobile( source.getMobile() );
    target.setContent( contentContentConverter.map( source.getContent(), config ) );
    target.setKeywords( source.getKeywords() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap( map, config ) );
    }
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getAmp() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getAmp());
    }
  }
}
