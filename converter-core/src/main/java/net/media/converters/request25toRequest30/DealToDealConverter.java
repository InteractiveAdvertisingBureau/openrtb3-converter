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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Deal;
import net.media.utils.Provider;
import net.media.utils.Utils;

/** Created by rajat.go on 03/01/19. */
public class DealToDealConverter implements Converter<Deal, net.media.openrtb3.Deal> {
  @Override
  public net.media.openrtb3.Deal map(Deal deal, Config config, Provider converterProvider) {
    if (deal == null) {
      return null;
    }

    net.media.openrtb3.Deal deal1 = new net.media.openrtb3.Deal();
    enhance(deal, deal1, config, converterProvider);
    return deal1;
  }

  @Override
  public void enhance(
      Deal deal, net.media.openrtb3.Deal deal1, Config config, Provider converterProvider) {
    if (deal == null || deal1 == null) {
      return;
    }
    deal1.setFlrcur(deal.getBidFloorCur());
    deal1.setFlr(deal.getBidFloor());
    deal1.setId(deal.getId());
    deal1.setAt(deal.getAt());
    deal1.setWseat(Utils.copyCollection(deal.getWseat(), config));
    deal1.setWadomain(Utils.copyCollection(deal.getWadomain(), config));
    deal1.setExt(Utils.copyMap(deal.getExt(), config));
  }
}
