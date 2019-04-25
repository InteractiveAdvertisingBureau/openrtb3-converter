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

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Deal;
import net.media.openrtb25.request.*;
import net.media.openrtb25.request.Metric;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.*;
import net.media.utils.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.putToExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/01/19. */
public class ImpToItemConverter implements Converter<Imp, Item> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.SEQ);
    extraFieldsInExt.add(CommonConstants.QTY);
    extraFieldsInExt.add(CommonConstants.DT);
    extraFieldsInExt.add(CommonConstants.DLVY);
    extraFieldsInExt.add(CommonConstants.SSAI);
    extraFieldsInExt.add(CommonConstants.REWARD);
    extraFieldsInExt.add(CommonConstants.ADMX);
    extraFieldsInExt.add(CommonConstants.CURLX);
    extraFieldsInExt.add(CommonConstants.AMPREN);
    extraFieldsInExt.add(CommonConstants.CTYPE);
    extraFieldsInExt.add(CommonConstants.EVENT);
  }

  private static final JavaType javaTypeForEventSpecCollection = JacksonObjectMapperUtils.getMapper().getTypeFactory()
    .constructCollectionType(Collection.class, EventSpec.class);

  @Override
  public Item map(Imp imp, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(imp)) {
      return null;
    }
    Item item = new Item();
    enhance(imp, item, config, converterProvider);
    return item;
  }

  @Override
  public void enhance(Imp imp, Item item, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (imp == null || item == null) {
      return;
    }
    Converter<Deal, net.media.openrtb3.Deal> dealDealConverter =
        converterProvider.fetch(new Conversion<>(Deal.class, net.media.openrtb3.Deal.class));
    Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter =
        converterProvider.fetch(new Conversion<>(Metric.class, net.media.openrtb3.Metric.class));

    if (item.getSpec() == null) {
      item.setSpec(new Spec());
    }
    impToSpec1(imp, item.getSpec(), config, converterProvider);
    Map<String, Object> map = imp.getExt();
    if(nonNull(map)) {
      item.setExt(new HashMap<>(map));
    }
    if (imp.getPmp() != null && imp.getPmp().getExt() != null) {
      Pmp pmp = new Pmp();
      pmp.setExt(imp.getPmp().getExt());
      item.setExt(putToExt(pmp, item.getExt(), CommonConstants.PMP));
    }
    item.setFlrcur(imp.getBidfloorcur());
    Collection<Deal> deals = impPmpDeals(imp);
    item.setDeal(
        CollectionToCollectionConverter.convert(
            deals, dealDealConverter, config, converterProvider));
    item.setFlr(imp.getBidfloor());
    item.setId(imp.getId());
    Integer private_auction = impPmpPrivate_auction(imp);
    if (private_auction != null) {
      item.setPriv(private_auction);
    }
    Integer sequence = impSequence(imp);
    if (sequence != null) {
      item.setSeq(sequence);
    }
    item.setExp(imp.getExp());
    Collection<Metric> metrics = imp.getMetric();
    if (metrics != null) {
      Collection<net.media.openrtb3.Metric> metrics1 = new ArrayList<>(metrics.size());
      for (Metric metric : metrics) {
        metrics1.add(metricMetricConverter.map(metric, config, converterProvider));
      }
      item.setMetric(metrics1);
    }
    item.setQty(getQuantity(imp));
    impToItemAfterMapping(imp, item);
    removeFromExt(item.getExt(), extraFieldsInExt);
  }

  private void impToSpec1(Imp imp, Spec mappingTarget, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (imp == null) {
      return;
    }

    if (mappingTarget.getPlacement() == null) {
      mappingTarget.setPlacement(new Placement());
    }
    impToPlacement1(imp, mappingTarget.getPlacement(), config, converterProvider);
  }

  private void impToPlacement1(
      Imp imp, Placement mappingTarget, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (imp == null) {
      return;
    }
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter =
        converterProvider.fetch(new Conversion<>(Banner.class, DisplayPlacement.class));
    Converter<Audio, AudioPlacement> audioAudioPlacementConverter =
        converterProvider.fetch(new Conversion<>(Audio.class, AudioPlacement.class));
    Converter<Video, VideoPlacement> videoVideoPlacementConverter =
        converterProvider.fetch(new Conversion<>(Video.class, VideoPlacement.class));
    Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter =
        converterProvider.fetch(new Conversion<>(Native.class, DisplayPlacement.class));
    mappingTarget.setAudio(
        audioAudioPlacementConverter.map(imp.getAudio(), config, converterProvider));
    mappingTarget.setVideo(
        videoVideoPlacementConverter.map(imp.getVideo(), config, converterProvider));
    if (nonNull(mappingTarget.getVideo())) {
      mappingTarget.getVideo().setClktype(imp.getClickbrowser());
    }
    mappingTarget.setTagid(imp.getTagId());
    mappingTarget.setSdk(imp.getDisplaymanager());
    mappingTarget.setSdkver(imp.getDisplaymanagerver());
    mappingTarget.setSecure(imp.getSecure());
    DisplayPlacement displayPlacement =
        bannerDisplayPlacementConverter.map(imp.getBanner(), config, converterProvider);
    if(nonNull(displayPlacement)) {
      fetchFromExt(displayPlacement::setAmpren, imp.getExt(), CommonConstants.AMPREN, "error while mapping ampren from Imp");
      fetchFromExt(displayPlacement::setEvent, imp.getExt(), CommonConstants.EVENT, "error while mapping event from Imp", javaTypeForEventSpecCollection);
    }
    if (isNull(displayPlacement) && nonNull(imp.getNat())) {
      displayPlacement = new DisplayPlacement();
    }
    nativeDisplayPlacementConverter.enhance(
        imp.getNat(), displayPlacement, config, converterProvider);
    mappingTarget.setDisplay(displayPlacement);
    if (nonNull(mappingTarget.getDisplay())) {
      mappingTarget.getDisplay().setClktype(imp.getClickbrowser());
      mappingTarget.getDisplay().setIfrbust(CollectionUtils.copyCollection(imp.getIframebuster(), config));
      mappingTarget.getDisplay().setInstl(imp.getInstl());
    }
  }

  private Collection<Deal> impPmpDeals(Imp imp) {
    if (imp == null) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if (pmp == null) {
      return null;
    }
    Collection<Deal> deals = pmp.getDeals();
    if (deals == null) {
      return null;
    }
    return deals;
  }

  private Integer getQuantity(Imp imp) throws OpenRtbConverterException {
    if (imp == null) {
      return null;
    }
    Video video = imp.getVideo();
    try {
      if (nonNull(video) && nonNull(video.getExt()) && video.getExt().containsKey(CommonConstants.QTY)) {
        return (Integer) video.getExt().get(CommonConstants.QTY);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    if (nonNull(imp.getNat()) && nonNull(imp.getNat().getNativeRequestBody())) {
      return imp.getNat().getNativeRequestBody().getPlcmtcnt();
    }
    Banner banner = imp.getBanner();
    try {
      if (nonNull(banner) && nonNull(banner.getExt()) && banner.getExt().containsKey(CommonConstants.QTY)) {
        return (Integer) banner.getExt().get(CommonConstants.QTY);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    Audio audio = imp.getAudio();
    try {
      if (nonNull(audio) && nonNull(audio.getExt()) && audio.getExt().containsKey(CommonConstants.QTY)) {
        return (Integer) audio.getExt().get(CommonConstants.QTY);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    return null;
  }

  private Integer impPmpPrivate_auction(Imp imp) {
    if (imp == null) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if (pmp == null) {
      return null;
    }
    Integer private_auction = pmp.getPrivate_auction();
    if (private_auction == null) {
      return null;
    }
    return private_auction;
  }

  private Integer impSequence(Imp imp) throws OpenRtbConverterException {
    if (imp == null) {
      return null;
    }
    Video video = imp.getVideo();
    if (nonNull(video) && nonNull(video.getSequence())) {
      return video.getSequence();
    }
    Native nat = imp.getNat();
    if (nonNull(nat)
        && nonNull(nat.getNativeRequestBody())
        && nonNull(nat.getNativeRequestBody().getSeq())) {
      return nat.getNativeRequestBody().getSeq();
    }
    Banner banner = imp.getBanner();
    try {
      if (nonNull(banner) && nonNull(banner.getExt()) && banner.getExt().containsKey(CommonConstants.SEQ)) {
        return (Integer) banner.getExt().get(CommonConstants.SEQ);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    Audio audio = imp.getAudio();
    if (nonNull(audio) && nonNull(audio.getSequence())) {
      return audio.getSequence();
    }
    return null;
  }

  private void impToItemAfterMapping(Imp imp, Item item) throws OpenRtbConverterException {
    if(nonNull(imp)) {
      if(nonNull(item)) {
        fetchFromExt(item::setDt, imp.getExt(), CommonConstants.DT, "error while mapping dt from Imp");
        fetchFromExt(item::setDlvy, imp.getExt(), CommonConstants.DLVY, "error while mapping dlvy from Imp");
        fetchFromExt(item.getSpec().getPlacement()::setSsai, imp.getExt(), CommonConstants.SSAI, "error while mapping ssai from Imp");
        fetchFromExt(item.getSpec().getPlacement()::setReward, imp.getExt(), CommonConstants.REWARD, "error while mapping reward from Imp");
        fetchFromExt(item.getSpec().getPlacement()::setAdmx, imp.getExt(), CommonConstants.ADMX, "error while mapping admx from Imp");
        fetchFromExt(item.getSpec().getPlacement()::setCurlx, imp.getExt(), CommonConstants.CURLX, "error while mapping curlx from Imp");
      }
    }
  }
}
