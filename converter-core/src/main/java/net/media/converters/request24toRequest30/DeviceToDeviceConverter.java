package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
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
  public net.media.openrtb3.Device map(Device source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Device device1 = new net.media.openrtb3.Device();

    inhance( source, device1, config );

    return device1;
  }

  @Override
  public void inhance(Device source, net.media.openrtb3.Device target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setContype( source.getConnectiontype() );
    target.setType( source.getDevicetype() );
    target.setLang( source.getLanguage() );
    target.setUa( source.getUa() );
    target.setIfa( source.getIfa() );
    if ( source.getDnt() != null ) {
      target.setDnt( String.valueOf( source.getDnt() ) );
    }
    target.setLmt( source.getLmt() );
    target.setMake( source.getMake() );
    target.setModel( source.getModel() );
    if ( source.getOs() != null ) {
      target.setOs(OsMap.osMap.getOrDefault(source.getOs().trim().toLowerCase(), 0));
    }
    target.setOsv( source.getOsv() );
    target.setHwv( source.getHwv() );
    target.setH( source.getH() );
    target.setW( source.getW() );
    target.setPpi( source.getPpi() );
    target.setPxratio( source.getPxratio() );
    target.setJs( source.getJs() );
    target.setIp( source.getIp() );
    target.setIpv6( source.getIpv6() );
    target.setCarrier( source.getCarrier() );
    target.setGeofetch( source.getGeofetch() );
    target.setGeo( geoToGeoConverter.map( source.getGeo(), config ) );
    target.setMccmnc( source.getMccmnc() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    if(source.getFlashver() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("flashver", source.getFlashver());
    }
    if(source.getExt() == null)
      return;
    target.setXff((String) source.getExt().get("xff"));
    target.getExt().remove("xff");
    target.setIptr((Integer) source.getExt().get("iptr"));
    target.getExt().remove("iptr");
    target.setIptr((Integer) source.getExt().get("mccmncsim"));
    target.getExt().remove("mccmncsim");
  }
}
