package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.Video;

public class VideoToBidConverter implements Converter<Video,Bid> {
  public VideoToBidConverter(){

  }
  public Bid map(Video source, Config config){
    return null;
  }

  public  void inhance(Video source, Bid target, Config config){
    return ;
  }
}