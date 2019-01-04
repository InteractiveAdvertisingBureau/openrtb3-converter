package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Geo;

import java.util.HashMap;
import java.util.Map;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb24.request.Geo> {
  @Override
  public net.media.openrtb24.request.Geo map(Geo source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Geo geo1 = new net.media.openrtb24.request.Geo();

    geo1.setIpservice( source.getIpserv() );
    geo1.setAccuracy( source.getAccur() );
    geo1.setType( source.getType() );
    geo1.setRegion( source.getRegion() );
    geo1.setMetro( source.getMetro() );
    geo1.setCity( source.getCity() );
    geo1.setZip( source.getZip() );
    geo1.setCountry( source.getCountry() );
    geo1.setLat( source.getLat() );
    geo1.setLon( source.getLon() );
    geo1.setUtcoffset( source.getUtcoffset() );
    geo1.setLastfix( source.getLastfix() );
    Map<String, Object> map = source.getExt();
    inhance(source, geo1, config );
    if ( map != null ) {
      geo1.setExt( new HashMap<String, Object>( map ) );
    }

    return geo1;
  }

  @Override
  public void inhance(Geo source, net.media.openrtb24.request.Geo target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("regionfips104")) {
      target.setRegionfips104((String) source.getExt().get("regionfips104"));
      source.getExt().remove("regionfips104");
    }
  }
}
