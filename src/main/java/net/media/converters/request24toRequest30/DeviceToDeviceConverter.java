package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Device;
import net.media.openrtb24.request.Geo;
import net.media.utils.OsMap;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb3.Device> {

  private Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter;

  @Override
  public net.media.openrtb3.Device map(Device source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Device device1 = new net.media.openrtb3.Device();

    device1.setContype( source.getConnectiontype() );
    device1.setType( source.getDevicetype() );
    device1.setLang( source.getLanguage() );
    device1.setUa( source.getUa() );
    device1.setIfa( source.getIfa() );
    if ( source.getDnt() != null ) {
      device1.setDnt( String.valueOf( source.getDnt() ) );
    }
    device1.setLmt( source.getLmt() );
    device1.setMake( source.getMake() );
    device1.setModel( source.getModel() );
    if ( source.getOs() != null ) {
      device1.setOs(OsMap.osMap.getOrDefault(source.getOs().trim().toLowerCase(), 0));
    }
    device1.setOsv( source.getOsv() );
    device1.setHwv( source.getHwv() );
    device1.setH( source.getH() );
    device1.setW( source.getW() );
    device1.setPpi( source.getPpi() );
    device1.setPxratio( source.getPxratio() );
    device1.setJs( source.getJs() );
    device1.setIp( source.getIp() );
    device1.setIpv6( source.getIpv6() );
    device1.setCarrier( source.getCarrier() );
    device1.setGeofetch( source.getGeofetch() );
    device1.setGeo( geoToGeoConverter.map( source.getGeo(), config ) );
    device1.setMccmnc( source.getMccmnc() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      device1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, device1, config );

    return device1;
  }

  @Override
  public void inhance(Device source, net.media.openrtb3.Device target, Config config) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setXff((String) source.getExt().get("xff"));
    target.getExt().remove("xff");
    target.setIptr((Integer) source.getExt().get("iptr"));
    target.getExt().remove("iptr");
    target.setIptr((Integer) source.getExt().get("mccmncsim"));
    target.getExt().remove("mccmncsim");
    if(source.getFlashver() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("flashver", source.getFlashver());
    }
  }
}
