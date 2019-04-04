package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.utils.OsMap;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb25.request.Device> {

  @Override
  public net.media.openrtb25.request.Device map(Device source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Device device1 = new net.media.openrtb25.request.Device();

    enhance( source, device1, config, converterProvider );

    return device1;
  }

  @Override
  public void enhance(Device source, net.media.openrtb25.request.Device target, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter =
      converterProvider.fetch(new Conversion<>(Geo.class, net.media.openrtb25.request.Geo.class));
    target.setLanguage( source.getLang() );
    target.setConnectiontype( source.getContype() );
    target.setDevicetype( source.getType() );
    target.setUa( source.getUa() );
    target.setGeo( geoGeoConverter.map( source.getGeo(), config, converterProvider ) );
    if ( source.getDnt() != null ) {
      target.setDnt( Integer.parseInt( source.getDnt() ) );
    }
    target.setLmt( source.getLmt() );
    target.setIp( source.getIp() );
    target.setIpv6( source.getIpv6() );
    if ( source.getOs() != null ) {
      if( OsMap.osMap.inverse().containsKey( source.getOs() ) )
        target.setOs( OsMap.osMap.inverse().get( source.getOs() ) );
    }
    target.setMake( source.getMake() );
    target.setModel( source.getModel() );
    target.setOsv( source.getOsv() );
    target.setHwv( source.getHwv() );
    target.setH( source.getH() );
    target.setW( source.getW() );
    target.setPpi( source.getPpi() );
    target.setPxratio( source.getPxratio() );
    target.setJs( source.getJs() );
    target.setGeofetch( source.getGeofetch() );
    target.setCarrier( source.getCarrier() );
    target.setIfa( source.getIfa() );
    target.setMccmnc( source.getMccmnc() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      if(map.containsKey("flashver")) {
        try {
          target.setFlashver((String) source.getExt().get("flashver"));
          map.remove("flashver");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for Device", e);
        }
      }
      target.setExt( Utils.copyMap(map, config) );
    }
  }
}
