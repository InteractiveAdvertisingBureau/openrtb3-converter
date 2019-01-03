package net.media.converters;

import net.media.config.Config;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.Response;

/**
 * @author shiva.b
 */
public class BidResponseToResponseConverter implements Converter<BidResponse, Response>{

  /**
   *
   * @param source
   * @param config
   * @return
   */
  @Override
  public Response map(BidResponse source, Config config) {
    return null;
  }

  /**
   *
   * @param source
   * @param target
   */
  @Override
  public void inhance(BidResponse source, Response target, Config config) {

  }
}
