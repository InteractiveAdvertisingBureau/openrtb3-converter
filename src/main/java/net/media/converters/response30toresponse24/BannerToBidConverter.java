package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Display;

public class BannerToBidConverter implements Converter<Banner,Bid> {
  BannerToBidConverter(){

  }
  public Bid map(Banner source, Config config){
    return null;
  }

  public  void inhance(Banner source, Bid target, Config config){
    return ;
  }
}
