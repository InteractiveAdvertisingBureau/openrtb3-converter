package net.media.converters.response30toresponse24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Display;

public class AuditToBidConverter implements Converter<Audit,Bid> {
  public AuditToBidConverter(){

  }
  public Bid map(Audit source, Config config){
    return null;
  }

  public  void inhance(Audit source, Bid target, Config config){
    return ;
  }
}
