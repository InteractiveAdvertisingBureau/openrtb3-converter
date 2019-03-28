package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Data;
import net.media.openrtb25.request.Geo;
import net.media.openrtb25.request.User;
import net.media.utils.ListToListConverter;

import java.util.HashMap;
import java.util.Map;

public class UserToUserConverter implements Converter<User, net.media.openrtb3.User> {

  private Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter;
  private Converter<Data, net.media.openrtb3.Data> dataDataConverter;

  @java.beans.ConstructorProperties({"geoToGeoConverter", "dataDataConverter"})
  public UserToUserConverter(Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter, Converter<Data, net.media.openrtb3.Data> dataDataConverter) {
    this.geoToGeoConverter = geoToGeoConverter;
    this.dataDataConverter = dataDataConverter;
  }

  @Override
  public net.media.openrtb3.User map(User source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.User user1 = new net.media.openrtb3.User();

    enhance( source, user1, config );

    return user1;
  }

  @Override
  public void enhance(User source, net.media.openrtb3.User target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setBuyeruid( source.getBuyeruid() );
    target.setYob( source.getYob() );
    target.setGender( source.getGender() );
    target.setKeywords( source.getKeywords() );
    target.setGeo( geoToGeoConverter.map( source.getGeo(), config ) );
    target.setData( ListToListConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
    }
    if(source.getExt() == null)
      return;
    target.setConsent((String) source.getExt().get("consent"));
    target.getExt().remove("consent");
  }
}
