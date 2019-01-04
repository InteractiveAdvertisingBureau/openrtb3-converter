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

    net.media.openrtb24.request.Device.DeviceBuilder device1 = net.media.openrtb24.request.Device.builder();

    device1.language( source.getLang() );
    device1.connectiontype( source.getContype() );
    device1.devicetype( source.getType() );
    device1.ua( source.getUa() );
    device1.geo( geoGeoConverter.map( source.getGeo(), config ) );
    if ( source.getDnt() != null ) {
      device1.dnt( Integer.parseInt( source.getDnt() ) );
    }
    device1.lmt( source.getLmt() );
    device1.ip( source.getIp() );
    device1.ipv6( source.getIpv6() );
    if ( source.getOs() != null ) {
      if( OsMap.osMap.inverse().containsKey( source.getOs() ) )
        device1.os( OsMap.osMap.inverse().get( source.getOs() ) );
    }
    device1.make( source.getMake() );
    device1.model( source.getModel() );
    device1.osv( source.getOsv() );
    device1.hwv( source.getHwv() );
    device1.h( source.getH() );
    device1.w( source.getW() );
    device1.ppi( source.getPpi() );
    device1.pxratio( source.getPxratio() );
    device1.js( source.getJs() );
    device1.geofetch( source.getGeofetch() );
    device1.carrier( source.getCarrier() );
    device1.ifa( source.getIfa() );
    device1.mccmnc( source.getMccmnc() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      if(map.containsKey("flashver")) {
        device1.flashver((String) source.getExt().get("flashver"));
        map.remove("flashver");
      }
      device1.ext( new HashMap<String, Object>( map ) );
    }

    return device1.build();
  }

  @Override
  public void inhance(Device source, net.media.openrtb24.request.Device target, Config config) {

  }
}
