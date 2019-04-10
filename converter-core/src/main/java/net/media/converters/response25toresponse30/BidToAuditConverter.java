package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Corr;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author shiva.b
 */
public class BidToAuditConverter implements Converter<Bid, Audit> {
  @Override
  public Audit map(Bid source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Audit audit = new Audit();
    return audit;
  }

  @Override
  public void enhance(Bid source, Audit target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    if (isNull(source) || isNull(target)) {
      return;
    }
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      try {
        target.setExt(Utils.copyMap(map, config));
        if (map.containsKey("corr")) {
          target.setCorr((Corr) map.get("corr"));
        }
        if (map.containsKey("status")) {
          target.setStatus((Integer) map.get("status"));
        }
        if (map.containsKey("audit")) {
          target.setInit(((Audit) map.get("audit")).getInit());
        }
        if (map.containsKey("lastmod")) {
          target.setLastmod((Integer) map.get("lastmod"));
        }
        if (map.containsKey("feedback")) {
          target.setFeedback((List<String>) map.get("feedback"));
        }
      }
      catch (Exception e) {
        throw new OpenRtbConverterException("error while type casting in bid.ext", e);
      }
    }
    else {
      target.setExt( null );
    }
  }
}
