package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Media;
import net.media.template.MacroMapper;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Bid30ToBid25Converter implements Converter<net.media.openrtb3.Bid, Bid> {


  @Override
  public Bid map(net.media.openrtb3.Bid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    Bid bid = new Bid();
    enhance(source,bid,config, converterProvider);
    return bid;
  }

  @Override
  public void enhance(net.media.openrtb3.Bid source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    Converter<Media, Bid> mediaBidConverter = converterProvider.fetch(new Conversion<>(Media.class,
      Bid.class));

    if ( source == null && target == null && config == null ) {
      return ;
    }
    if ( source != null ) {
      Map<String, Object> map = source.getExt();
      if ( map != null ) {
        target.setExt(Utils.copyMap(map, config));
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
//      target.setQagmediarating(Integer.parseInt(source.getMid()));
      target.setAdid(source.getMid());
//      target.getExt().put("qagmediarating", source.getMid());
      target.getExt().put("macro",source.getMacro());
      mediaBidConverter.enhance(source.getMedia(),target,config, converterProvider);
      MacroMapper.macroReplaceTwoX(target);
      if (nonNull(source.getExt())) {
        if (source.getExt().containsKey("protocol")) {
          target.setProtocol((Integer) source.getExt().get("protocol"));
          target.getExt().remove("protocol");

        }
      }
    }
  }
}
