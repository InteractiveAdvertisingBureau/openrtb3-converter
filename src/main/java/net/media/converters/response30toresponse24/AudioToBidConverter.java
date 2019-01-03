package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;

public class AudioToBidConverter implements Converter<Audio,Bid> {

  public AudioToBidConverter(){

  }

  public Bid map(Audio source, Config config){
    return null;
  }

  public  void inhance(Audio source, Bid target, Config config){
    return ;
  }
}
