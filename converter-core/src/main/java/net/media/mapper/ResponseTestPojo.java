package net.media.mapper;

import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;

import lombok.Data;

/**
 * Created by shiva.b on 24/12/18.
 */
@Data
public class ResponseTestPojo {

  private String description;
  private BidResponse response25;
  private OpenRTB response30;
  private String type;
}
