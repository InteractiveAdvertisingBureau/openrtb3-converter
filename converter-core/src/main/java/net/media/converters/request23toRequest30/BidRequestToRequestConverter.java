package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Source;
import net.media.openrtb3.Context;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class BidRequestToRequestConverter extends net.media.converters
  .request25toRequest30.BidRequestToRequestConverter {

  public void enhance(BidRequest source, Request target, Config config, Provider<Conversion,
    Converter> converterProvider) throws OpenRtbConverterException {
    if (source == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("bseat")) {
        source.setBseat((Collection<String>) source.getExt().get("bseat"));
        source.getExt().remove("bseat");
      }
      if (source.getExt().containsKey("wlang")) {
        source.setWlang((Collection<String>) source.getExt().get("wlang"));
        source.getExt().remove("wlang");
      }
      if (source.getExt().containsKey("source")) {
        source.setSource((Source) source.getExt().get("source"));
        source.getExt().remove("source");
      }
      if (source.getExt().containsKey("bapp")) {
        source.setBapp((Collection<String>) source.getExt().get("bapp"));
        source.getExt().remove("bapp");
      }
    }
    super.enhance(source, target, config, converterProvider);

  }
}
