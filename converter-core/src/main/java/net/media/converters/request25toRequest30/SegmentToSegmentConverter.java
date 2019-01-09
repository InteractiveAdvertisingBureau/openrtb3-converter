package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Segment;

import java.util.HashMap;
import java.util.Map;

public class SegmentToSegmentConverter implements Converter<Segment, net.media.openrtb3.Segment> {
  @Override
  public net.media.openrtb3.Segment map(Segment source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Segment segment1 = new net.media.openrtb3.Segment();

    enhance( source, segment1, config );

    return segment1;
  }

  @Override
  public void enhance(Segment source, net.media.openrtb3.Segment target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setValue( source.getValue() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
