package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
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
  public net.media.openrtb24.request.Data map(Data source, Config config) throws OpenRtbConverterException {

    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Data data1 = new net.media.openrtb24.request.Data();

    inhance( source, data1, config );

    return data1;
  }

  @Override
  public void inhance(Data source, net.media.openrtb24.request.Data target, Config config) throws OpenRtbConverterException {
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( ListToListConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
