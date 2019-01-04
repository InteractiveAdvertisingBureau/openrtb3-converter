package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Content;
import net.media.openrtb24.request.Publisher;
import net.media.openrtb24.request.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SiteToSiteConverter implements Converter<Site, net.media.openrtb3.Site> {

  private Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb3.Content> contentContentConverter;

  @Override
  public net.media.openrtb3.Site map(Site source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Site site1 = new net.media.openrtb3.Site();

    site1.setPrivpolicy( source.getPrivacypolicy() );
    site1.setSectcat( source.getSectioncat() );
    site1.setPub( publisherPublisherConverter.map( source.getPublisher(), config ) );
    site1.setId( source.getId() );
    site1.setName( source.getName() );
    site1.setContent( contentContentConverter.map( source.getContent(), config ) );
    site1.setDomain( source.getDomain() );
    List<String> list = source.getCat();
    if ( list != null ) {
      site1.setCat( new ArrayList<String>( list ) );
    }
    site1.setPagecat( source.getPagecat() );
    site1.setKeywords( source.getKeywords() );
    site1.setPage( source.getPage() );
    site1.setRef( source.getRef() );
    site1.setSearch( source.getSearch() );
    site1.setMobile( source.getMobile() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      site1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, site1, config );

    return site1;
  }

  @Override
  public void inhance(Site source, net.media.openrtb3.Site target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setAmp((Integer) source.getExt().get("amp"));
    target.getExt().remove("cattax");
    target.getExt().remove("amp");
  }
}
