package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * Created by shiva.b on 10/04/19.
 */
public class BidRequestToOpenRtbWrapperConverter implements Converter<BidRequest2_X, OpenRTBWrapper3_X> {
  @Override
  public OpenRTBWrapper3_X map(BidRequest2_X source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    OpenRTBWrapper3_X openRTBWrapper3_x = new OpenRTBWrapper3_X();
    Converter<BidRequest2_X, OpenRTB3_X> bidRequestToOpenRtbConverter = converterProvider.fetch(new
      Conversion<>(BidRequest2_X.class, OpenRTB3_X.class));
    openRTBWrapper3_x.setOpenrtb( bidRequestToOpenRtbConverter.map(source, config,
      converterProvider));
    return openRTBWrapper3_x;
  }

  @Override
  public void enhance(BidRequest2_X source, OpenRTBWrapper3_X target, Config config, Provider converterProvider) throws OpenRtbConverterException {

  }
}
