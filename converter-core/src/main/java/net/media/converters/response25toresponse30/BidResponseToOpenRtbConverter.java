package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.Response;
import net.media.utils.Provider;

/**
 * @author shiva.b
 */
public class BidResponseToOpenRtbConverter implements Converter<BidResponse2_X, OpenRTB3_X> {

  /**
   *
   * @param source
   * @param config
   * @return
   */
  @Override
  public OpenRTB3_X map(BidResponse2_X source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    OpenRTB3_X openRTB = new OpenRTB3_X();
    enhance(source, openRTB, config, converterProvider);
    return openRTB;
  }

  /**
   *
   * @param source
   * @param target
   */
  @Override
  public void enhance(BidResponse2_X source, OpenRTB3_X target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    target.setDomainSpec("3.0");
    Converter<BidResponse2_X, Response> converter = converterProvider.fetch(new Conversion<>(BidResponse2_X.class, Response.class));
    target.setResponse(converter.map(source, config, converterProvider));
  }
}
