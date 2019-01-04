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

    geo1.setIpserv( source.getIpservice() );
    geo1.setAccur( source.getAccuracy() );
    geo1.setType( source.getType() );
    geo1.setLat( source.getLat() );
    geo1.setLon( source.getLon() );
    geo1.setLastfix( source.getLastfix() );
    geo1.setCountry( source.getCountry() );
    geo1.setRegion( source.getRegion() );
    geo1.setMetro( source.getMetro() );
    geo1.setCity( source.getCity() );
    geo1.setUtcoffset( source.getUtcoffset() );
    geo1.setZip( source.getZip() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      geo1.setExt( new HashMap<String, Object>( map ) );
    }

    if(source.getRegionfips104() != null) {
      if(geo1.getExt() == null) {
        geo1.setExt(new HashMap<>());
      }
      geo1.getExt().put("regionfips104", source.getRegionfips104());
    }

    return geo1;
  }

  @Override
  public void inhance(Geo source, net.media.openrtb3.Geo target, Config config) {

  }
}
