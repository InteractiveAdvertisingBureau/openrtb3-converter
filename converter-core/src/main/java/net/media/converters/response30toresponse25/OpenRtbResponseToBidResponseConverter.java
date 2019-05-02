/*
 * Copyright  2019 - present. IAB Tech Lab
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

package net.media.converters.response30toresponse25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.OpenRTB3_X;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OpenRtbResponseToBidResponseConverter
    implements Converter<OpenRTB3_X, BidResponse2_X> {

  @Override
  public BidResponse2_X map(OpenRTB3_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    BidResponse2_X bidResponse = new BidResponse2_X();
    enhance(source, bidResponse, config, converterProvider);
    return bidResponse;
  }

  @Override
  public void enhance(
      OpenRTB3_X source, BidResponse2_X target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {

    Converter<Seatbid, SeatBid> seatBid30ToSeatBid25Converter =
        converterProvider.fetch(new Conversion<>(Seatbid.class, SeatBid.class));
    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }
    Response response = source.getResponse();
    if (response == null) {
      return;
    }

    target.setId(response.getId());
    List<SeatBid> seatBidList = new ArrayList<>();
    if (nonNull(response.getSeatbid())) {
      for (Seatbid seatBid : response.getSeatbid()) {
        seatBidList.add(seatBid30ToSeatBid25Converter.map(seatBid, config, converterProvider));
      }
    }
    target.setSeatbid(seatBidList);
    target.setBidid(response.getBidid());
    target.setCur(response.getCur());
    target.setNbr(response.getNbr());
    if (nonNull(response.getExt())) {
      target.setExt(new HashMap<>(response.getExt()));
    }
    target.setCustomdata(response.getCdata());
  }
}
