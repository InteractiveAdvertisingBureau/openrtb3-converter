package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Publisher;
import net.media.openrtb25.request.Site;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SiteToSiteConverter implements Converter<Site, net.media.openrtb3.Site> {

  private Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb3.Content> contentContentConverter;

  @java.beans.ConstructorProperties({"publisherPublisherConverter", "contentContentConverter"})
  public SiteToSiteConverter(Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter, Converter<Content, net.media.openrtb3.Content> contentContentConverter) {
    this.publisherPublisherConverter = publisherPublisherConverter;
    this.contentContentConverter = contentContentConverter;
  }

  @Override
  public net.media.openrtb3.Site map(Site source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Site site1 = new net.media.openrtb3.Site();

    enhance( source, site1, config );

    return site1;
  }

  @Override
  public void enhance(Site source, net.media.openrtb3.Site target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setPrivpolicy( source.getPrivacypolicy() );
    target.setSectcat( Utils.copyList(source.getSectioncat(), config) );
    target.setPub( publisherPublisherConverter.map( source.getPublisher(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setContent( contentContentConverter.map( source.getContent(), config ) );
    target.setDomain( source.getDomain() );
    if ( source.getCat() != null ) {
      target.setCat( Utils.copyList( source.getCat(), config ) );
    }
    target.setPagecat( Utils.copySet(source.getPagecat(), config) );
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
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setAmp((Integer) source.getExt().get("amp"));
    target.getExt().remove("cattax");
    target.getExt().remove("amp");
  }
}
