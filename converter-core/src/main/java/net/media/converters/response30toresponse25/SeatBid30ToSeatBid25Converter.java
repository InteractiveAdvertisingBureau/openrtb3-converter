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
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Seatbid;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SeatBid30ToSeatBid25Converter implements Converter<Seatbid, SeatBid> {

  @Override
  public SeatBid map(Seatbid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(config)) {
      return null;
    }
    SeatBid seatBid = new SeatBid();
    enhance(source, seatBid, config, converterProvider);
    return seatBid;
  }

  @Override
  public void enhance(Seatbid source, SeatBid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {

    Converter<net.media.openrtb3.Bid, Bid> bidBidConverter =
        converterProvider.fetch(new Conversion<>(net.media.openrtb3.Bid.class, Bid.class));
    if (isNull(source) || isNull(target) || isNull(config)) {
      return;
    }

    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(source.getExt()));
    }
    target.setGroup(source.get_package());
    List<Bid> bidList = new ArrayList<>();
    if (nonNull(source.getBid())) {
      for (net.media.openrtb3.Bid bid : source.getBid()) {
        bidList.add(bidBidConverter.map(bid, config, converterProvider));
      }
    }
    target.setBid(bidList);
    target.setSeat(source.getSeat());
  }
}
