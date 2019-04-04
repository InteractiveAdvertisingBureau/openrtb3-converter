package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Data;
import net.media.openrtb3.Segment;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class DataToDataConverter implements Converter<Data, net.media.openrtb25.request.Data> {

  @Override
  public net.media.openrtb25.request.Data map(Data source, Config config, Provider converterProvider) throws OpenRtbConverterException {

    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Data data1 = new net.media.openrtb25.request.Data();

    enhance( source, data1, config, converterProvider );

    return data1;
  }

  @Override
  public void enhance(Data source, net.media.openrtb25.request.Data target, Config config, Provider converterProvider) throws
    OpenRtbConverterException {
    if(target == null || source == null)
      return;
    Converter<Segment, net.media.openrtb25.request.Segment> segmentSegmentConverter =
      converterProvider.fetch(new Conversion(Segment.class, net.media.openrtb25.request.Segment.class));
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setSegment( CollectionToCollectionConverter.convert( source.getSegment(),
      segmentSegmentConverter, config, converterProvider ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }
  }
}
