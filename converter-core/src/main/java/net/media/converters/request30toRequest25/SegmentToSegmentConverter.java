package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb3.Segment;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SegmentToSegmentConverter implements Converter<Segment, net.media.openrtb25.request.Segment> {
  @Override
  public net.media.openrtb25.request.Segment map(Segment source, Config config, Provider converterProvider) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Segment segment1 = new net.media.openrtb25.request.Segment();

    enhance( source, segment1, config, converterProvider );

    return segment1;
  }

  @Override
  public void enhance(Segment source, net.media.openrtb25.request.Segment target, Config config, Provider converterProvider) {
    if(source == null || target == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setValue( source.getValue() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
  }
}
