package net.media.converters.response25toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.Provider;

import static java.util.Objects.isNull;

/**
 * Created by shiva.b on 10/04/19.
 */
public class BidResponseToOpenRtbWrapperConverter implements Converter<BidResponse2_X, OpenRTBWrapper3_X> {

  @Override
  public OpenRTBWrapper3_X map(BidResponse2_X source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    OpenRTBWrapper3_X openRTBWrapper3_x = new OpenRTBWrapper3_X();
    Converter<BidResponse2_X, OpenRTB3_X> bidResponseToOpenRtbConverter = converterProvider.fetch
      (new Conversion<>(BidResponse2_X.class, OpenRTB3_X.class));
    openRTBWrapper3_x.setOpenrtb( bidResponseToOpenRtbConverter.map(source, config,
      converterProvider));
    return openRTBWrapper3_x;
  }

  @Override
  public void enhance(BidResponse2_X source, OpenRTBWrapper3_X target, Config config, Provider converterProvider) throws OpenRtbConverterException {

  }
}
