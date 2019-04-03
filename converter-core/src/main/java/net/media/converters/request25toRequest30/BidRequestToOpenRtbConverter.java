package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class BidRequestToOpenRtbConverter implements Converter<BidRequest, OpenRTB> {
  @Override
  public OpenRTB map(BidRequest source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }

    OpenRTB openRTB = new OpenRTB();
    enhance(source, openRTB, config, converterProvider);

    return openRTB;
  }

  @Override
  public void enhance(BidRequest source, OpenRTB target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if (isNull(source) || isNull(target)) {
      return;
    }
    Converter<BidRequest, Request> bidRequestRequestConverter = converterProvider.fetch(new Conversion
            (BidRequest.class, Request.class));
    target.setRequest(bidRequestRequestConverter.map(source, config, converterProvider));
  }
}
