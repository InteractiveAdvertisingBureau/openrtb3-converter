package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Geo;
import net.media.utils.OsMap;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb3.Device> {

  private Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter;

  @java.beans.ConstructorProperties({"geoToGeoConverter"})
  public DeviceToDeviceConverter(Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter) {
    this.geoToGeoConverter = geoToGeoConverter;
  }

  @Override
  public net.media.openrtb3.Device map(Device source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Device device1 = new net.media.openrtb3.Device();

    enhance( source, device1, config );

    return device1;
  }

  @Override
  public void enhance(Device source, net.media.openrtb3.Device target, Config config) throws OpenRtbConverterException {
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
      target.setExt( Utils.copyMap(map, config) );
    }
    if(source.getFlashver() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("flashver", source.getFlashver());
    }
    if(source.getExt() == null)
      return;
    try {
      target.setXff((String) source.getExt().get("xff"));
      target.getExt().remove("xff");
      target.setIptr((Integer) source.getExt().get("iptr"));
      target.getExt().remove("iptr");
      target.setIptr((Integer) source.getExt().get("mccmncsim"));
      target.getExt().remove("mccmncsim");
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Device", e);
    }
  }
}
