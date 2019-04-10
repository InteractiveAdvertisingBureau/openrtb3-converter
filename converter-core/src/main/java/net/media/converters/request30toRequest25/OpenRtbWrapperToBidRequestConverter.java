package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * Created by shiva.b on 10/04/19.
 */
public class OpenRtbWrapperToBidRequestConverter implements Converter<OpenRTBWrapper3_X, BidRequest2_X> {
  @Override
  public BidRequest2_X map(OpenRTBWrapper3_X source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    if (isNull(source.getOpenrtb())) {
      return null;
    }
    Converter<OpenRTB3_X, BidRequest2_X> openRTB3_xBidRequest2_xConverter = converterProvider.fetch(new
      Conversion<>(OpenRTB3_X.class, BidRequest2_X.class));
    return openRTB3_xBidRequest2_xConverter.map(source.getOpenrtb(), config, converterProvider);
  }

  @Override
  public void enhance(OpenRTBWrapper3_X source, BidRequest2_X target, Config config, Provider converterProvider) throws OpenRtbConverterException {

  }
}
