package net.media;

import net.media.config.Config;
import net.media.enums.AdType;
import net.media.mapper.OpenRtb24To3MapperImpl;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.Response;

/**
 * Created by shiva.b on 02/01/19.
 */
public class OpenRtbConverter {

  private Config config;

  private OpenRtb24To3MapperImpl openRtb24To3Mapper;

  private OpenRtbConverter(Config config) {
    this.config = config;
    this.openRtb24To3Mapper = new OpenRtb24To3MapperImpl(config);
  }

  /**
   *
   * @param bidResponse
   * @param adType
   * @return
   */
  public Response map(BidResponse bidResponse, AdType adType) {
    return openRtb24To3Mapper.map(bidResponse, adType);
  }

  /**
   *
   * @param response
   * @param adType
   * @return
   */
  public BidResponse map(Response response, AdType adType) {
    return openRtb24To3Mapper.map(response, adType);
  }
}
