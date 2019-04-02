package net.media.converters.request30toRequest25;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Producer;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ContentToContentConverter implements Converter<Content, net.media.openrtb25.request.Content> {

  private Converter<Producer, net.media.openrtb25.request.Producer> producerProducerConverter;
  private Converter<Data, net.media.openrtb25.request.Data> dataDataConverter;

  @java.beans.ConstructorProperties({"producerProducerConverter", "dataDataConverter"})
  public ContentToContentConverter(Converter<Producer, net.media.openrtb25.request.Producer> producerProducerConverter, Converter<Data, net.media.openrtb25.request.Data> dataDataConverter) {
    this.producerProducerConverter = producerProducerConverter;
    this.dataDataConverter = dataDataConverter;
  }

  @Override
  public net.media.openrtb25.request.Content map(Content source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Content content1 = new net.media.openrtb25.request.Content();

    enhance( source, content1, config );

    return content1;
  }

  @Override
  public void enhance(Content source, net.media.openrtb25.request.Content target, Config config)
    throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setContentrating( source.getRating() );
    target.setSourcerelationship( source.getSrcrel() );
    target.setUserrating( source.getUrating() );
    target.setLanguage( source.getLang() );
    target.setQagmediarating( source.getMrating() );
    target.setEmbeddable( source.getEmbed() );
    target.setLivestream( source.getLive() );
    target.setId( source.getId() );
    target.setEpisode( source.getEpisode() );
    target.setTitle( source.getTitle() );
    target.setSeries( source.getSeries() );
    target.setSeason( source.getSeason() );
    target.setArtist( source.getArtist() );
    target.setGenre( source.getGenre() );
    target.setAlbum( source.getAlbum() );
    target.setIsrc( source.getIsrc() );
    target.setProducer( producerProducerConverter.map( source.getProducer(), config ) );
    target.setUrl( source.getUrl() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    target.setProdq( source.getProdq() );
    target.setContext( source.getContext() );
    target.setKeywords( source.getKeywords() );
    target.setLen( source.getLen() );
    target.setData( CollectionToCollectionConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getExt() != null) {
      if (source.getExt().containsKey("videoquality")) {
        try {
          target.setVideoquality((Integer) source.getExt().get("videoquality"));
          target.getExt().remove("videoquality");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for Content", e);
        }
      }
    }
  }
}
