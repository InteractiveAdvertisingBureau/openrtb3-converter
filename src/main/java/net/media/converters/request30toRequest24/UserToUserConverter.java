package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Data;
import net.media.openrtb3.Geo;
import net.media.openrtb3.User;
import net.media.utils.ListToListConverter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class UserToUserConverter implements Converter<User, net.media.openrtb24.request.User> {

  private Converter<Geo, net.media.openrtb24.request.Geo> geoGeoConverter;
  private Converter<Data, net.media.openrtb24.request.Data> dataDataConverter;

  @Override
  public net.media.openrtb24.request.User map(User source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb24.request.User user1 = new net.media.openrtb24.request.User();

    user1.setId( source.getId() );
    user1.setBuyeruid( source.getBuyeruid() );
    user1.setYob( source.getYob() );
    user1.setGender( source.getGender() );
    user1.setGeo( geoGeoConverter.map( source.getGeo(), config ) );
    user1.setKeywords( source.getKeywords() );
    user1.setData( ListToListConverter.convert( source.getData(), dataDataConverter, config ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      user1.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, user1, config );

    return user1;
  }

  @Override
  public void inhance(User source, net.media.openrtb24.request.User target, Config config) {
    if(source == null)
      return;
    if(source.getConsent() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("consent", source.getConsent());
    }
  }
}
