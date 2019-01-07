package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
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
  public net.media.openrtb24.request.Device map(Device source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.Device device1 = new net.media.openrtb24.request.Device();

    inhance( source, device1, config );

    return device1;
  }

  @Override
  public void inhance(Device source, net.media.openrtb24.request.Device target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setLanguage( source.getLang() );
    target.setConnectiontype( source.getContype() );
    target.setDevicetype( source.getType() );
    target.setUa( source.getUa() );
    target.setGeo( geoGeoConverter.map( source.getGeo(), config ) );
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
        target.setFlashver((String) source.getExt().get("flashver"));
        map.remove("flashver");
      }
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
