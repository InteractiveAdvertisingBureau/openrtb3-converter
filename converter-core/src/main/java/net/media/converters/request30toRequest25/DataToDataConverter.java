package net.media.converters.request30toRequest25;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Data;
import net.media.openrtb3.Segment;
import net.media.utils.ListToListConverter;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DataToDataConverter implements Converter<Data, net.media.openrtb25.request.Data> {

  private Converter<Segment, net.media.openrtb25.request.Segment> segmentSegmentConverter;

  @Override
  public net.media.openrtb25.request.Data map(Data source, Config config) throws OpenRtbConverterException {

    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Data data1 = new net.media.openrtb25.request.Data();

    enhance( source, data1, config );

    return data1;
  }

  @Override
  public void enhance(Data source, net.media.openrtb25.request.Data target, Config config) throws
    OpenRtbConverterException {
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( ListToListConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }
  }
}
