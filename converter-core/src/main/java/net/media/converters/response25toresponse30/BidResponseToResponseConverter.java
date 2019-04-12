/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.converters.response25toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.CollectionUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author shiva.b */
public class BidResponseToResponseConverter implements Converter<BidResponse2_X, Response> {

  /**
   * @param bidResponse
   * @param config
   * @return
   */
  @Override
  public Response map(BidResponse2_X bidResponse, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (bidResponse == null) {
      return null;
    }
    Response response = new Response();
    enhance(bidResponse, response, config, converterProvider);
    return response;
  }

  /**
   * @param bidResponse
   * @param response
   */
  @Override
  public void enhance(
      BidResponse2_X bidResponse, Response response, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    response.setId(bidResponse.getId());
    response.setBidid(bidResponse.getBidid());
    response.setNbr(bidResponse.getNbr());
    response.setCur(bidResponse.getCur());
    Converter<SeatBid, Seatbid> converter =
        converterProvider.fetch(new Conversion<>(SeatBid.class, Seatbid.class));
    if (!CollectionUtils.isEmpty(bidResponse.getSeatbid())) {
      List<Seatbid> seatbids = new ArrayList<>();
      for (SeatBid seatBid : bidResponse.getSeatbid()) {
        seatbids.add(converter.map(seatBid, config, converterProvider));
      }
      response.setSeatbid(seatbids);
    }
    Map<String, Object> map = bidResponse.getExt();
    if (map != null) {
      response.setExt(new HashMap<>(map));
    } else {
      response.setExt(null);
    }
    response.setCdata(bidResponse.getCustomdata());
  }
}
