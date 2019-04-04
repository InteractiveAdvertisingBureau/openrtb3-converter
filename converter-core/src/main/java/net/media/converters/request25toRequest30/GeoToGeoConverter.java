package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb3.Geo> {
  @Override
  public net.media.openrtb3.Geo map(Geo source, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Geo geo1 = new net.media.openrtb3.Geo();

    enhance( source, geo1, config, converterProvider );

    return geo1;
  }

  @Override
  public void enhance(Geo source, net.media.openrtb3.Geo target, Config config,
                      Provider converterProvider) throws
    OpenRtbConverterException {
    if(source == null || target == null)
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
      target.setExt( Utils.copyMap(map, config) );
    }

    if(source.getRegionfips104() != null) {
      if(target.getExt() == null) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("regionfips104", source.getRegionfips104());
    }
  }
}
