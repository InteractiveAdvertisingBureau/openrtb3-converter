package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.utils.OsMap;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb24.request.Device> {

  private Converter<Geo, net.media.openrtb24.request.Geo> geoGeoConverter;

  @Override
  public net.media.openrtb24.request.Device map(Device source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Device device1 = new net.media.openrtb24.request.Device();

    device1.setLanguage( source.getLang() );
    device1.setConnectiontype( source.getContype() );
    device1.setDevicetype( source.getType() );
    device1.setUa( source.getUa() );
    device1.setGeo( geoGeoConverter.map( source.getGeo(), config ) );
    if ( source.getDnt() != null ) {
      device1.setDnt( Integer.parseInt( source.getDnt() ) );
    }
    device1.setLmt( source.getLmt() );
    device1.setIp( source.getIp() );
    device1.setIpv6( source.getIpv6() );
    if ( source.getOs() != null ) {
      if( OsMap.osMap.inverse().containsKey( source.getOs() ) )
        device1.setOs( OsMap.osMap.inverse().get( source.getOs() ) );
    }
    device1.setMake( source.getMake() );
    device1.setModel( source.getModel() );
    device1.setOsv( source.getOsv() );
    device1.setHwv( source.getHwv() );
    device1.setH( source.getH() );
    device1.setW( source.getW() );
    device1.setPpi( source.getPpi() );
    device1.setPxratio( source.getPxratio() );
    device1.setJs( source.getJs() );
    device1.setGeofetch( source.getGeofetch() );
    device1.setCarrier( source.getCarrier() );
    device1.setIfa( source.getIfa() );
    device1.setMccmnc( source.getMccmnc() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      if(map.containsKey("flashver")) {
        device1.setFlashver((String) source.getExt().get("flashver"));
        map.remove("flashver");
      }
      device1.setExt( new HashMap<String, Object>( map ) );
    }

    return device1;
  }

  @Override
  public void inhance(Device source, net.media.openrtb24.request.Device target, Config config) {

  }
}
