package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Source;
import net.media.openrtb3.Request;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 02/04/19.
 */
public class BidRequestToRequestConverter extends net.media.converters
  .request25toRequest30.BidRequestToRequestConverter {

  public void enhance(BidRequest source, Request target, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("bseat")) {
        try {
          source.setBseat((Collection<String>) source.getExt().get("bseat"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting bseat from bidRequest.ext.bseat",
            e);
        }
        source.getExt().remove("bseat");
      }
      if (source.getExt().containsKey("wlang")) {
        try {
          source.setWlang((Collection<String>) source.getExt().get("wlang"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting wlang from bidRequest.ext.wlang",
            e);
        }
        source.getExt().remove("wlang");
      }
      if (source.getExt().containsKey("source")) {
        try {
          Source source1 = Utils.getMapper().convertValue(source.getExt().get("source"), Source
            .class);
          source.setSource(source1);
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting source from bidRequest.ext.source",
            e);
        }
        source.getExt().remove("source");
      }
    }
    super.enhance(source, target, config, converterProvider);

  }
}
