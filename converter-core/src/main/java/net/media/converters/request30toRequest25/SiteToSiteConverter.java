package net.media.converters.request30toRequest25;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Content;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SiteToSiteConverter implements Converter<Site, net.media.openrtb24.request.Site> {

  private Converter<Publisher, net.media.openrtb24.request.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb24.request.Content> contentContentConverter;

  @Override
  public net.media.openrtb24.request.Site map(Site source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Site site1 = new net.media.openrtb24.request.Site();

    inhance( source, site1, config );

    return site1;
  }

  @Override
  public void inhance(Site source, net.media.openrtb24.request.Site target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setSectioncat( source.getSectcat() );
    target.setPrivacypolicy( source.getPrivpolicy() );
    target.setPublisher( publisherPublisherConverter.map( source.getPub(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setDomain( source.getDomain() );
    List<String> list1 = source.getCat();
    if ( list1 != null ) {
      target.setCat( new ArrayList<String>( list1 ) );
    }
    target.setPagecat( source.getPagecat() );
    target.setPage( source.getPage() );
    target.setRef( source.getRef() );
    target.setSearch( source.getSearch() );
    target.setMobile( source.getMobile() );
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
    if(source.getAmp() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getAmp());
    }
  }
}
