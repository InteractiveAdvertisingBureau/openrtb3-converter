package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
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
  public net.media.openrtb24.request.Site map(Site source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Site site1 = new net.media.openrtb24.request.Site();

    site1.setSectioncat( source.getSectcat() );
    site1.setPrivacypolicy( source.getPrivpolicy() );
    site1.setPublisher( publisherPublisherConverter.map( source.getPub(), config ) );
    site1.setId( source.getId() );
    site1.setName( source.getName() );
    site1.setDomain( source.getDomain() );
    List<String> list1 = source.getCat();
    if ( list1 != null ) {
      site1.setCat( new ArrayList<String>( list1 ) );
    }
    site1.setPagecat( source.getPagecat() );
    site1.setPage( source.getPage() );
    site1.setRef( source.getRef() );
    site1.setSearch( source.getSearch() );
    site1.setMobile( source.getMobile() );
    site1.setContent( contentContentConverter.map( source.getContent(), config ) );
    site1.setKeywords( source.getKeywords() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      site1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, site1, config );

    return site1;
  }

  @Override
  public void inhance(Site source, net.media.openrtb24.request.Site target, Config config) {
    if(source == null)
      return;
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
