package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Data;
import net.media.openrtb3.Segment;
import net.media.utils.Provider;
import net.media.utils.Utils;
import net.media.utils.CollectionToCollectionConverter;

import java.util.HashMap;
import java.util.Map;

public class DataToDataConverter implements Converter<Data, net.media.openrtb3.Data> {

  @Override
  public net.media.openrtb3.Data map(Data source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Data data1 = new net.media.openrtb3.Data();

    enhance( source, data1, config, converterProvider );

    return data1;
  }

  @Override
  public void enhance(Data source, net.media.openrtb3.Data target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<net.media.openrtb25.request.Segment, Segment> segmentSegmentConverter = converterProvider.fetch(new Conversion
            (net.media.openrtb25.request.Segment.class, Segment.class));
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( CollectionToCollectionConverter.convert( source.getSegment(), segmentSegmentConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }
  }
}
