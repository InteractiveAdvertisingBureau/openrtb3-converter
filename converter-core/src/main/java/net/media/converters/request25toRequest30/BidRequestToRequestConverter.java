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
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Source;
import net.media.openrtb25.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/01/19. */
public class BidRequestToRequestConverter implements Converter<BidRequest2_X, Request> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CATTAX);
    extraFieldsInExt.add(CommonConstants.RESTRICTIONS);
    extraFieldsInExt.add(CommonConstants.DOOH);
  }

  private String bidRequestUserCustomdata(BidRequest2_X bidRequest) {
    if (bidRequest == null) {
      return null;
    }
    User user = bidRequest.getUser();
    if (user == null) {
      return null;
    }
    String customdata = user.getCustomdata();
    if (customdata == null) {
      return null;
    }
    return customdata;
  }

  @Override
  public Request map(BidRequest2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    Request request = new Request();

    enhance(source, request, config, converterProvider);

    return request;
  }

  @Override
  public void enhance(
      BidRequest2_X source, Request target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<BidRequest2_X, Context> bidRequestContextConverter =
        converterProvider.fetch(new Conversion<>(BidRequest2_X.class, Context.class));
    Converter<Imp, Item> impItemConverter =
        converterProvider.fetch(new Conversion<>(Imp.class, Item.class));
    target.setContext(bidRequestContextConverter.map(source, config, converterProvider));
    target.setItem(
        CollectionUtils.convert(
            source.getImp(), impItemConverter, config, converterProvider));
    target.setPack(source.getAllimps());
    String customdata = bidRequestUserCustomdata(source);
    if (customdata != null) {
      target.setCdata(customdata);
    }
    target.setId(source.getId());
    target.setTest(source.getTest());
    target.setTmax(source.getTmax());
    target.setAt(source.getAt());
    target.setCur(CollectionUtils.copyCollection(source.getCur(), config));
    Converter<Source, net.media.openrtb3.Source> source25Source3Converter =
        converterProvider.fetch(new Conversion<>(Source.class, net.media.openrtb3.Source.class));
    target.setSource(source25Source3Converter.map(source.source, config, converterProvider));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }

    if (!CollectionUtils.isEmpty(target.getItem())) {
      for (Item item : target.getItem()) {
        if (item.getSpec() == null) {
          item.setSpec(new Spec());
        }
        bidRequestToSpec(source, item.getSpec(), config);
      }
    }
    if (source.getWseat() != null && source.getWseat().size() > 0) {
      target.setWseat(1);
      target.setSeat(CollectionUtils.copyCollection(source.getWseat(), config));
    } else {
      target.setWseat(0);
      target.setSeat(CollectionUtils.copyCollection(source.getBseat(), config));
    }
    if (target.getContext() == null) {
      target.setContext(new Context());
    }
    fetchFromExt(
      target.getContext()::setDooh,
      source.getExt(),
      CommonConstants.DOOH,
      "error while typecasting ext for BidRequest2_X",
      Dooh.class);

    removeFromExt(target.getExt(), extraFieldsInExt);
  }

  private void bidRequestToSpec(BidRequest2_X bidRequest, Spec mappingTarget, Config config) {
    if (bidRequest == null) {
      return;
    }

    if (mappingTarget.getPlacement() == null) {
      mappingTarget.setPlacement(new Placement());
    }
    bidRequestToPlacement(bidRequest, mappingTarget.getPlacement(), config);
  }

  private void bidRequestToPlacement(
      BidRequest2_X bidRequest, Placement mappingTarget, Config config) {
    if (bidRequest == null) {
      return;
    }

    mappingTarget.setWlang(CollectionUtils.copyCollection(bidRequest.getWlang(), config));
  }
}
