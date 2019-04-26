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

package net.media.converters.request30toRequest25;

import static java.util.Objects.isNull;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Deal;
import net.media.utils.CollectionUtils;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

public class DealToDealConverter implements Converter<Deal, net.media.openrtb25.request.Deal> {

  @Override
  public net.media.openrtb25.request.Deal map(
      Deal deal, Config config, Provider converterProvider) {
    if (deal == null) {
      return null;
    }
    net.media.openrtb25.request.Deal deal1 = new net.media.openrtb25.request.Deal();
    enhance(deal, deal1, config, converterProvider);
    return deal1;
  }

  @Override
  public void enhance(
      Deal deal,
      net.media.openrtb25.request.Deal deal1,
      Config config,
      Provider converterProvider) {
    if (isNull(deal) || isNull(deal1)) {
      return;
    }
    deal1.setBidFloorCur(deal.getFlrcur());
    if (deal.getFlr() != null) {
      deal1.setBidFloor(deal.getFlr());
    }
    deal1.setId(deal.getId());
    deal1.setAt(deal.getAt());
    deal1.setWseat(CollectionUtils.copyCollection(deal.getWseat(), config));
    deal1.setWadomain(CollectionUtils.copyCollection(deal.getWadomain(), config));
    deal1.setExt(MapUtils.copyMap(deal.getExt(), config));
  }
}
