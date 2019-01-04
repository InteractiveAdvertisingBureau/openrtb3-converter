package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Content;
import net.media.openrtb24.request.Producer;
import net.media.openrtb3.Data;
import net.media.utils.ListToListConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ContentToContentConverter implements Converter<Content, net.media.openrtb3.Content> {

  private Converter<Producer, net.media.openrtb3.Producer> producerProducerConverter;
  private Converter<net.media.openrtb24.request.Data, Data> dataDataConverter;

  @Override
  public net.media.openrtb3.Content map(Content source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Content content1 = new net.media.openrtb3.Content();

    content1.setMrating( source.getQagmediarating() );
    content1.setSrcrel( source.getSourcerelationship() );
    content1.setRating( source.getContentrating() );
    content1.setEmbed( source.getEmbeddable() );
    content1.setUrating( source.getUserrating() );
    content1.setLang( source.getLanguage() );
    content1.setLive( source.getLivestream() );
    content1.setId( source.getId() );
    content1.setEpisode( source.getEpisode() );
    content1.setTitle( source.getTitle() );
    content1.setSeries( source.getSeries() );
    content1.setSeason( source.getSeason() );
    content1.setArtist( source.getArtist() );
    content1.setGenre( source.getGenre() );
    content1.setAlbum( source.getAlbum() );
    content1.setIsrc( source.getIsrc() );
    content1.setUrl( source.getUrl() );
    content1.setCat( source.getCat() );
    content1.setProdq( source.getProdq() );
    content1.setContext( source.getContext() );
    content1.setKeywords( source.getKeywords() );
    content1.setLen( source.getLen() );
    content1.setProducer( producerProducerConverter.map( source.getProducer(), config ) );
    content1.setData( ListToListConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      content1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, content1, config );

    return content1;
  }

  @Override
  public void inhance(Content source, net.media.openrtb3.Content target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
    if(source.getVideoquality() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("videoquality", source.getVideoquality());
    }
  }
}
