package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Geo;

import java.util.HashMap;
import java.util.Map;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb25.request.Geo> {
  @Override
  public net.media.openrtb25.request.Geo map(Geo source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Geo geo1 = new net.media.openrtb25.request.Geo();

    inhance(source, geo1, config );

    return geo1;
  }

  @Override
  public void inhance(Geo source, net.media.openrtb25.request.Geo target, Config config) {
    if(source == null)
      return;
    target.setIpservice( source.getIpserv() );
    target.setAccuracy( source.getAccur() );
    target.setType( source.getType() );
    target.setRegion( source.getRegion() );
    target.setMetro( source.getMetro() );
    target.setCity( source.getCity() );
    target.setZip( source.getZip() );
    target.setCountry( source.getCountry() );
    target.setLat( source.getLat() );
    target.setLon( source.getLon() );
    target.setUtcoffset( source.getUtcoffset() );
    target.setLastfix( source.getLastfix() );
    Map<String, Object> map = source.getExt();
    if(map == null)
      return;
    if(source.getExt().containsKey("regionfips104")) {
      target.setRegionfips104((String) source.getExt().get("regionfips104"));
      source.getExt().remove("regionfips104");
    }
    target.setExt( new HashMap<String, Object>( map ) );
  }
}
