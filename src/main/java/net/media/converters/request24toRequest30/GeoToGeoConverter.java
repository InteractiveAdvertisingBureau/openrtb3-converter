package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Geo;

import java.util.HashMap;
import java.util.Map;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb3.Geo> {
  @Override
  public net.media.openrtb3.Geo map(Geo source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Geo geo1 = new net.media.openrtb3.Geo();

    inhance( source, geo1, config );

    return geo1;
  }

  @Override
  public void inhance(Geo source, net.media.openrtb3.Geo target, Config config) {
    if(source == null)
      return;
    target.setIpserv( source.getIpservice() );
    target.setAccur( source.getAccuracy() );
    target.setType( source.getType() );
    target.setLat( source.getLat() );
    target.setLon( source.getLon() );
    target.setLastfix( source.getLastfix() );
    target.setCountry( source.getCountry() );
    target.setRegion( source.getRegion() );
    target.setMetro( source.getMetro() );
    target.setCity( source.getCity() );
    target.setUtcoffset( source.getUtcoffset() );
    target.setZip( source.getZip() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }

    if(source.getRegionfips104() != null) {
      if(target.getExt() == null) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("regionfips104", source.getRegionfips104());
    }
  }
}
