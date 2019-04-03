package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

public class OpenRtbToBidRequestConverter implements Converter<OpenRTB, BidRequest> {

  @Override
  public BidRequest map(OpenRTB source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    if (isNull(source.getRequest())) {
      return null;
    }
    Converter<Request, BidRequest> requestBidRequestConverter = converterProvider.fetch(new Conversion(Request.class, BidRequest.class));
    return requestBidRequestConverter.map(source.getRequest(), config, converterProvider);
  }

  @Override
  public void enhance(OpenRTB source, BidRequest target, Config config, Provider<Conversion, Converter> converterProvider) {
  }
}
