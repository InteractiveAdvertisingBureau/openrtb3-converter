package net.media.converters.response30toresponse25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Media;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class Bid30ToBid24Converter  implements Converter<net.media.openrtb3.Bid, Bid> {

  Converter<Media,Bid> mediaBidConverter;

  public Bid30ToBid24Converter(Converter<Media,Bid> mediaBidConverter){
    this.mediaBidConverter = mediaBidConverter;
  }

  @Override
  public Bid map(net.media.openrtb3.Bid source, Config config) throws OpenRtbConverterException {
    Bid bid = new Bid();
    inhance(source,bid,config);
    return bid;
  }

  @Override
  public void inhance(net.media.openrtb3.Bid source,Bid target, Config config) throws OpenRtbConverterException {
    if ( source == null && target == null && config == null ) {
      return ;
    }
    if ( source != null ) {
      Map<String, Object> map = source.getExt();
      if ( map != null ) {
        target.setExt(new HashMap<>(map) );
      }
      else {
        target.setExt( null );
      }
      target.setId( source.getId() );
      if ( source.getPrice() != null ) {
        target.setPrice( source.getPrice() );
      }
      target.setImpid(source.getItem());
      target.setDealid(source.getDeal());
      target.setNurl(source.getPurl());
      target.setCid( source.getCid() );
      target.setExp( source.getExp() );
      target.setBurl( source.getBurl() );
      target.setLurl( source.getLurl() );
      target.setTactic( source.getTactic() );
      if(isNull(target.getExt())){
          target.setExt(new HashMap<>());
      }
      target.setQagmediarating(Integer.parseInt(source.getMid()));
      target.getExt().put("macro",source.getMacro());
      mediaBidConverter.inhance(source.getMedia(),target,config);
    }
  }
}
