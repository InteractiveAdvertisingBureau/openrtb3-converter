package net.media.converters.response25toresponse30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;

/**
 * @author shiva.b
 */
public class BidResponseToOpenRtbConverter implements Converter<BidResponse, OpenRTB> {

  private Converter<BidResponse, Response> bidResponseResponseConverter;

  public BidResponseToOpenRtbConverter(Converter<BidResponse, Response> bidResponseToResponseConverter) {
    this.bidResponseResponseConverter = bidResponseToResponseConverter;
  }

  /**
   *
   * @param source
   * @param config
   * @return
   */
  @Override
  public OpenRTB map(BidResponse source, Config config) throws OpenRtbConverterException {
    OpenRTB openRTB = new OpenRTB();
    enhance(source, openRTB, config);
    return openRTB;
  }

  /**
   *
   * @param source
   * @param target
   */
  @Override
  public void enhance(BidResponse source, OpenRTB target, Config config) throws OpenRtbConverterException{
    target.setDomainSpec("3.0");
    target.setResponse(bidResponseResponseConverter.map(source, config));
  }
}
