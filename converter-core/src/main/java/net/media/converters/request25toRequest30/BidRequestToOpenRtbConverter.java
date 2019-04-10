package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class BidRequestToOpenRtbConverter implements Converter<BidRequest2_X, OpenRTB3_X> {
  @Override
  public OpenRTB3_X map(BidRequest2_X source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }

    OpenRTB3_X openRTB = new OpenRTB3_X();
    enhance(source, openRTB, config, converterProvider);

    return openRTB;
  }

  @Override
  public void enhance(BidRequest2_X source, OpenRTB3_X target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source) || isNull(target)) {
      return;
    }
    Converter<BidRequest2_X, Request> bidRequestRequestConverter = converterProvider.fetch(new Conversion<>
            (BidRequest2_X.class, Request.class));
    target.setRequest(bidRequestRequestConverter.map(source, config, converterProvider));
  }
}
