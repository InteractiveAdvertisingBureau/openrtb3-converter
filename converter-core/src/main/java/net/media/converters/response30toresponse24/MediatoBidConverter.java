package net.media.converters.response30toresponse24;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Display;
import net.media.openrtb3.Media;

import static java.util.Objects.isNull;

public class MediatoBidConverter implements Converter<Media,Bid> {
  Converter<Ad,Bid> adBidConverter;

  public MediatoBidConverter(Converter<Ad,Bid> adBidConverter) {
    this.adBidConverter  = adBidConverter;
  }
  public Bid map(Media source, Config config) throws OpenRtbConverterException {
    if(isNull(source))
      return null;
    Bid bid =  new Bid();
    inhance(source,bid,config);
    return bid;
  }

  public  void inhance(Media source, Bid target, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    adBidConverter.inhance(source.getAd(),target,config);

  }
}
