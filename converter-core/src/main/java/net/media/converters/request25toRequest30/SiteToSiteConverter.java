package net.media.converters.request25toRequest30;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Publisher;
import net.media.openrtb25.request.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SiteToSiteConverter implements Converter<Site, net.media.openrtb3.Site> {

  private Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb3.Content> contentContentConverter;

  @Override
  public net.media.openrtb3.Site map(Site source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Site site1 = new net.media.openrtb3.Site();

    inhance( source, site1, config );

    return site1;
  }

  @Override
  public void inhance(Site source, net.media.openrtb3.Site target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setPrivpolicy( source.getPrivacypolicy() );
    target.setSectcat( source.getSectioncat() );
    target.setPub( publisherPublisherConverter.map( source.getPublisher(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setContent( contentContentConverter.map( source.getContent(), config ) );
    target.setDomain( source.getDomain() );
    List<String> list = source.getCat();
    if ( list != null ) {
      target.setCat( new ArrayList<String>( list ) );
    }
    target.setPagecat( source.getPagecat() );
    target.setKeywords( source.getKeywords() );
    target.setPage( source.getPage() );
    target.setRef( source.getRef() );
    target.setSearch( source.getSearch() );
    target.setMobile( source.getMobile() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setAmp((Integer) source.getExt().get("amp"));
    target.getExt().remove("cattax");
    target.getExt().remove("amp");
  }
}
