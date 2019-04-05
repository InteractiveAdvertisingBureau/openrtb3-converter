package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Media;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

public class MediatoBidConverter implements Converter<Media,Bid> {

  public Bid map(Media source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source))
      return null;
    Bid bid =  new Bid();
    enhance(source,bid,config, converterProvider);
    return bid;
  }

  public  void enhance(Media source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    Converter<Ad, Bid> adBidConverter = converterProvider.fetch(new Conversion<>(Ad.class, Bid.class));
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    adBidConverter.enhance(source.getAd(),target,config, converterProvider);

  }
}
