package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Data;
import net.media.openrtb25.request.Geo;
import net.media.openrtb25.request.User;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Map;

public class UserToUserConverter implements Converter<User, net.media.openrtb3.User> {

  @Override
  public net.media.openrtb3.User map(User source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.User user1 = new net.media.openrtb3.User();

    enhance( source, user1, config, converterProvider );

    return user1;
  }

  @Override
  public void enhance(User source, net.media.openrtb3.User target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter = converterProvider.fetch(new Conversion
            (Geo.class, net.media.openrtb3.Geo.class));
    Converter<Data, net.media.openrtb3.Data> dataDataConverter = converterProvider.fetch(new Conversion
            (Data.class, net.media.openrtb3.Data.class));
    target.setId( source.getId() );
    target.setBuyeruid( source.getBuyeruid() );
    target.setYob( source.getYob() );
    target.setGender( source.getGender() );
    target.setKeywords( source.getKeywords() );
    target.setGeo( geoToGeoConverter.map( source.getGeo(), config, converterProvider ) );
    target.setData( CollectionToCollectionConverter.convert( source.getData(), dataDataConverter,
      config, converterProvider ) );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
    if(source.getExt() == null)
      return;
    target.setConsent((String) source.getExt().get("consent"));
    target.getExt().remove("consent");
  }
}
