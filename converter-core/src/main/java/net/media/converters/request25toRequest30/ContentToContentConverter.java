package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Producer;
import net.media.openrtb3.Data;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ContentToContentConverter implements Converter<Content, net.media.openrtb3.Content> {

  @Override
  public net.media.openrtb3.Content map(Content source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Content content1 = new net.media.openrtb3.Content();

    enhance( source, content1, config, converterProvider );

    return content1;
  }

  @Override
  public void enhance(Content source, net.media.openrtb3.Content target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<Producer, net.media.openrtb3.Producer> producerProducerConverter = converterProvider.fetch(new Conversion
            (Producer.class, net.media.openrtb3.Producer.class));
    Converter<net.media.openrtb25.request.Data, Data> dataDataConverter = converterProvider.fetch(new Conversion
            (net.media.openrtb25.request.Data.class, Data.class));
    target.setMrating( source.getQagmediarating() );
    target.setSrcrel( source.getSourcerelationship() );
    target.setRating( source.getContentrating() );
    target.setEmbed( source.getEmbeddable() );
    target.setUrating( source.getUserrating() );
    target.setLang( source.getLanguage() );
    target.setLive( source.getLivestream() );
    target.setId( source.getId() );
    target.setEpisode( source.getEpisode() );
    target.setTitle( source.getTitle() );
    target.setSeries( source.getSeries() );
    target.setSeason( source.getSeason() );
    target.setArtist( source.getArtist() );
    target.setGenre( source.getGenre() );
    target.setAlbum( source.getAlbum() );
    target.setIsrc( source.getIsrc() );
    target.setUrl( source.getUrl() );
    target.setCat( Utils.copyCollection(source.getCat(), config) );
    target.setProdq( source.getProdq() );
    target.setContext( source.getContext() );
    target.setKeywords( source.getKeywords() );
    target.setLen( source.getLen() );
    target.setProducer( producerProducerConverter.map( source.getProducer(), config, converterProvider) );
    target.setData( CollectionToCollectionConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }

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
