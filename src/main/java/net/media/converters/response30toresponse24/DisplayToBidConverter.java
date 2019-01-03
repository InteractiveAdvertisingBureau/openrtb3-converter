package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;
import net.media.openrtb3.Native;

public class DisplayToBidConverter implements Converter<Display,Bid> {
  Converter<Banner,Bid> bannerBidConverter;
  Converter<Native,Bid> nativeBidConverter;

  DisplayToBidConverter(Converter<Banner,Bid> bannerBidConverter,Converter<Native,Bid> nativeBidConverter){
    this.bannerBidConverter = bannerBidConverter;
    this.nativeBidConverter = nativeBidConverter;
  }

  public Bid map(Display source, Config config){
    return null;
  }

  public  void inhance(Display source, Bid target, Config config){
    return ;
  }
}
