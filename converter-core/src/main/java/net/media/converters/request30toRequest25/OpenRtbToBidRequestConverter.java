package net.media.converters.request30toRequest25;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class OpenRtbToBidRequestConverter implements Converter<OpenRTB, BidRequest> {

  private Converter<Request, BidRequest> requestBidRequestConverter;

  @Override
  public BidRequest map(OpenRTB source, Config config) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    if (isNull(source.getRequest())) {
      return null;
    }
    return requestBidRequestConverter.map(source.getRequest(), config);
  }

  @Override
  public void inhance(OpenRTB source, BidRequest target, Config config) {
  }
}
