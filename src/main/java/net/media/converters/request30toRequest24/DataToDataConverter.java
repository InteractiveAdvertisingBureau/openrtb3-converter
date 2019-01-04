package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Data;
import net.media.openrtb3.Segment;
import net.media.utils.ListToListConverter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DataToDataConverter implements Converter<Data, net.media.openrtb24.request.Data> {

  private Converter<Segment, net.media.openrtb24.request.Segment> segmentSegmentConverter;

  @Override
  public net.media.openrtb24.request.Data map(Data source, Config config) {

    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Data data1 = new net.media.openrtb24.request.Data();

    data1.setId( source.getId() );
    data1.setName( source.getName() );
    data1.setSegment( ListToListConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      data1.setExt( new HashMap<String, Object>( map ) );
    }

    return data1;
  }

  @Override
  public void inhance(Data source, net.media.openrtb24.request.Data target, Config config) {

  }
}
