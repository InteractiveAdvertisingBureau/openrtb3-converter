package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audit;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class AuditToBidConverter implements Converter<Audit,Bid> {


  public Bid map(Audit source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    enhance(source,bid,config, converterProvider);
    return bid;
  }

  public  void enhance(Audit source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    if(isNull(target.getExt())){
      target.setExt(new HashMap<>());
    }
    target.getExt().put("status",source.getStatus());
    target.getExt().put("feedback",source.getFeedback());
    target.getExt().put("init",source.getInit());
    target.getExt().put("lastmod",source.getLastmod());
    target.getExt().put("corr",source.getCorr());
    target.getExt().putAll(source.getExt());
  }
}
