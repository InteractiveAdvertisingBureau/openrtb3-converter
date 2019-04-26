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

import static java.util.Objects.nonNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb3.Seatbid;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

/**
 * @author shiva.b
 */
public class SeatBid25ToSeatBid30Converter implements Converter<SeatBid, Seatbid> {

  @Override
  public Seatbid map(SeatBid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Seatbid seatbid = new Seatbid();
    enhance(source, seatbid, config, converterProvider);
    return seatbid;
  }

  @Override
  public void enhance(SeatBid source, Seatbid seatbid, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || seatbid == null) {
      return;
    }

    Map<String, Object> map = source.getExt();
    if (map != null) {
      seatbid.setExt(MapUtils.copyMap(map, config));
    } else {
      seatbid.setExt(null);
    }
    seatbid.set_package(source.getGroup());
    seatbid.setSeat(source.getSeat());
    Converter<Bid, net.media.openrtb3.Bid> bid25ToBid30Converter =
        converterProvider.fetch(new Conversion<>(Bid.class, net.media.openrtb3.Bid.class));
    if (!isEmpty(source.getBid())) {
      List<net.media.openrtb3.Bid> bidList = new ArrayList<>();
      for (Bid bid : source.getBid()) {
        net.media.openrtb3.Bid bid30 = bid25ToBid30Converter.map(bid, config, converterProvider);
        if (nonNull(bid)) {
          bidList.add(bid30);
        }
      }
      seatbid.setBid(bidList);
    }
  }
}
