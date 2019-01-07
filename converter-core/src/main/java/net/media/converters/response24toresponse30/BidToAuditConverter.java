package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Corr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author shiva.b
 */
public class BidToAuditConverter implements Converter<Bid, Audit> {
  @Override
  public Audit map(Bid source, Config config) {
    if (isNull(source)) {
      return null;
    }
    Audit audit = new Audit();
    return audit;
  }

  @Override
  public void inhance(Bid source, Audit target, Config config) {
    if (isNull(source) || isNull(target)) {
      return;
    }
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(new HashMap<>(map) );
      if (map.containsKey("corr")) {
        target.setCorr((Corr) map.get("corr"));
      }
      if (map.containsKey("status")) {
        target.setStatus((Integer) map.get("status"));
      }
      if (map.containsKey("init")) {
        target.setInit((Integer) map.get("init"));
      }
      if (map.containsKey("lastMod")) {
        target.setLastmod((Integer) map.get("lastMode"));
      }
      if (map.containsKey("feedback")) {
        target.setFeedback((List<String>) map.get("feedback"));
      }
    }
    else {
      target.setExt( null );
    }
  }
}
