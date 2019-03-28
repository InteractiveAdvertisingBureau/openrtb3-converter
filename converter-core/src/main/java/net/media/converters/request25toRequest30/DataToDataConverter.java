package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Data;
import net.media.openrtb3.Segment;
import net.media.utils.ListToListConverter;

import java.util.HashMap;
import java.util.Map;

public class DataToDataConverter implements Converter<Data, net.media.openrtb3.Data> {

  private Converter<net.media.openrtb25.request.Segment, Segment> segmentSegmentConverter;

  @java.beans.ConstructorProperties({"segmentSegmentConverter"})
  public DataToDataConverter(Converter<net.media.openrtb25.request.Segment, Segment> segmentSegmentConverter) {
    this.segmentSegmentConverter = segmentSegmentConverter;
  }

  @Override
  public net.media.openrtb3.Data map(Data source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Data data1 = new net.media.openrtb3.Data();

    enhance( source, data1, config );

    return data1;
  }

  @Override
  public void enhance(Data source, net.media.openrtb3.Data target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( ListToListConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
  }
}
