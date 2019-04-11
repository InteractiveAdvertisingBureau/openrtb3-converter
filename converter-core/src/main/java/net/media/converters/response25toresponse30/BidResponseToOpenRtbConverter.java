package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.utils.Provider;

/**
 * @author shiva.b
 */
public class BidResponseToOpenRtbConverter implements Converter<BidResponse, OpenRTB> {

  /**
   *
   * @param source
   * @param config
   * @return
   */
  @Override
  public OpenRTB map(BidResponse source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    OpenRTB openRTB = new OpenRTB();
    enhance(source, openRTB, config, converterProvider);
    return openRTB;
  }

  /**
   *
   * @param source
   * @param target
   */
  @Override
  public void enhance(BidResponse source, OpenRTB target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    Converter<BidResponse, Response> converter = converterProvider.fetch(new Conversion<>(BidResponse.class, Response.class));
    target.setResponse(converter.map(source, config, converterProvider));
  }
}
