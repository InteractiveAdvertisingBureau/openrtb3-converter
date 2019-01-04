package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Producer;
import net.media.utils.ListToListConverter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ContentToContentConverter implements Converter<Content, net.media.openrtb24.request.Content> {

  private Converter<Producer, net.media.openrtb24.request.Producer> producerProducerConverter;
  private Converter<Data, net.media.openrtb24.request.Data> dataDataConverter;

  @Override
  public net.media.openrtb24.request.Content map(Content source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Content content1 = new net.media.openrtb24.request.Content();

    content1.setContentrating( source.getRating() );
    content1.setSourcerelationship( source.getSrcrel() );
    content1.setUserrating( source.getUrating() );
    content1.setLanguage( source.getLang() );
    content1.setQagmediarating( source.getMrating() );
    content1.setEmbeddable( source.getEmbed() );
    content1.setLivestream( source.getLive() );
    content1.setId( source.getId() );
    content1.setEpisode( source.getEpisode() );
    content1.setTitle( source.getTitle() );
    content1.setSeries( source.getSeries() );
    content1.setSeason( source.getSeason() );
    content1.setArtist( source.getArtist() );
    content1.setGenre( source.getGenre() );
    content1.setAlbum( source.getAlbum() );
    content1.setIsrc( source.getIsrc() );
    content1.setProducer( producerProducerConverter.map( source.getProducer(), config ) );
    content1.setUrl( source.getUrl() );
    content1.setCat( source.getCat() );
    content1.setProdq( source.getProdq() );
    content1.setContext( source.getContext() );
    content1.setKeywords( source.getKeywords() );
    content1.setLen( source.getLen() );
    content1.setData( ListToListConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      content1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, content1, config );

    return content1;
  }

  @Override
  public void inhance(Content source, net.media.openrtb24.request.Content target, Config config) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getExt() != null) {
      if (source.getExt().containsKey("videoquality")) {
        target.setVideoquality((Integer) source.getExt().get("videoquality"));
        target.getExt().remove("videoquality");
      }
    }
  }
}
