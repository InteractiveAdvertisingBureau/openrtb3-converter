package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Data;
import net.media.openrtb3.Segment;
import net.media.utils.ListToListConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DataToDataConverter implements Converter<Data, net.media.openrtb3.Data> {

  private Converter<net.media.openrtb24.request.Segment, Segment> segmentSegmentConverter;

  @Override
  public net.media.openrtb3.Data map(Data source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Data data1 = new net.media.openrtb3.Data();

    inhance( source, data1, config );

    return data1;
  }

  @Override
  public void inhance(Data source, net.media.openrtb3.Data target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( ListToListConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
