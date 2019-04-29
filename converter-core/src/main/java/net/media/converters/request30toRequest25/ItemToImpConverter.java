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

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.*;

public class ItemToImpConverter implements Converter<Item, Imp> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.PMP);
  }

  @Override
  public Imp map(Item item, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (item == null) {
      return null;
    }

    Imp imp = new Imp();
    enhance(item, imp, config, converterProvider);

    return imp;
  }

  @Override
  public void enhance(Item item, Imp imp, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(imp) || isNull(item)) {
      return;
    }
    Converter<DisplayPlacement, Banner> displayPlacementBannerConverter =
        converterProvider.fetch(new Conversion<>(DisplayPlacement.class, Banner.class));
    Converter<DisplayPlacement, NativeRequest> displayPlacementNativeConverter =
        converterProvider.fetch(new Conversion<>(DisplayPlacement.class, NativeRequest.class));
    Converter<VideoPlacement, Video> videoPlacementVideoConverter =
        converterProvider.fetch(new Conversion<>(VideoPlacement.class, Video.class));
    Converter<AudioPlacement, Audio> audioPlacementAudioConverter =
        converterProvider.fetch(new Conversion<>(AudioPlacement.class, Audio.class));
    Converter<net.media.openrtb3.Metric, Metric> metricMetricConverter =
        converterProvider.fetch(new Conversion<>(net.media.openrtb3.Metric.class, Metric.class));
    imp.setPmp(itemToPmp(item, config, converterProvider));
    if (nonNull(item.getExt())) {
      imp.setExt(new HashMap<>(item.getExt()));
    }
    fillExtMap(item, imp, config);
    imp.setBidfloor(item.getFlr());
    VideoPlacement video = itemSpecPlacementVideo(item);
    if (video != null) {
      imp.setVideo(videoPlacementVideoConverter.map(video, config, converterProvider));
    }
    if (nonNull(video) && nonNull(video.getClktype())) {
      imp.setClickbrowser(video.getClktype());
    }
    if (nonNull(imp.getVideo())) {
      imp.getVideo().setSequence(item.getSeq());
      putToExt(item::getQty, imp.getVideo().getExt(), CommonConstants.QTY, imp.getVideo()::setExt);
    }
    String sdkver = itemSpecPlacementSdkver(item);
    if (sdkver != null) {
      imp.setDisplaymanagerver(sdkver);
    }
    DisplayPlacement display = itemSpecPlacementDisplay(item);
    if (display != null) {
      imp.setBanner(displayPlacementBannerConverter.map(display, config, converterProvider));
      if (nonNull(imp.getBanner())) {
        putToExt(
          item::getSeq, imp.getBanner().getExt(), CommonConstants.SEQ, imp.getBanner()::setExt);
        putToExt(
          item::getQty, imp.getBanner().getExt(), CommonConstants.QTY, imp.getBanner()::setExt);
      }
      if (nonNull(display.getNativefmt())) {
        NativeRequest nativeRequest =
          displayPlacementNativeConverter.map(display, config, converterProvider);
        Native nat = new Native();
        nat.setApi(CollectionUtils.copyCollection(display.getApi(), config));
        if (nonNull(display.getExt())) {
          if (isNull(nat.getExt())) {
            nat.setExt(new HashMap<>());
          }
          nat.getExt().putAll(display.getExt());
          fetchFromExt(
            nat::setVer,
            display.getNativefmt().getExt(),
            CommonConstants.VER,
            "error while mapping ver from nativefmt.ext");
        }
        putToExt(display::getPriv, nat.getExt(), CommonConstants.PRIV, nat::setExt);
        putToExt(display::getCtype, nat.getExt(), CommonConstants.CTYPE, nat::setExt);
        if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
          nativeRequest.getNativeRequestBody().setPlcmtcnt(item.getQty());
          nativeRequest.getNativeRequestBody().setSeq(item.getSeq());
        }
        if (config.getNativeRequestAsString()) {
          try {
            nat.setRequest(JacksonObjectMapperUtils.getMapper().writeValueAsString(nativeRequest));
          } catch (JsonProcessingException e) {
            throw new OpenRtbConverterException(e);
          }
        } else {
          nat.setRequest(nativeRequest);
        }

        imp.setNat(nat);
      }
      imp.setInstl(display.getInstl());
      imp.setIframebuster(CollectionUtils.copyCollection(display.getIfrbust(), config));
      imp.setClickbrowser(display.getClktype());
    }
    imp.setBidfloorcur(item.getFlrcur());
    String tagid = itemSpecPlacementTagid(item);
    if (tagid != null) {
      imp.setTagId(tagid);
    }
    String sdk = itemSpecPlacementSdk(item);
    if (sdk != null) {
      imp.setDisplaymanager(sdk);
    }
    AudioPlacement audio = itemSpecPlacementAudio(item);
    if (audio != null) {
      imp.setAudio(audioPlacementAudioConverter.map(audio, config, converterProvider));
    }
    if (nonNull(imp.getAudio())) {
      imp.getAudio().setSequence(item.getSeq());
      putToExt(item::getQty, imp.getAudio().getExt(), CommonConstants.QTY, imp.getAudio()::setExt);
    }
    imp.setId(item.getId());
    imp.setExp(item.getExp());
    Integer secure = itemSpecPlacementSecure(item);
    if (secure != null) {
      imp.setSecure(secure);
    }
    Collection<net.media.openrtb3.Metric> metrics = item.getMetric();
    if (metrics != null) {
      Collection<Metric> metrics1 = new ArrayList<>(metrics.size());
      for (net.media.openrtb3.Metric metric : metrics) {
        metrics1.add(metricMetricConverter.map(metric, config, converterProvider));
      }
      imp.setMetric(metrics1);
    }
    removeFromExt(imp.getExt(), extraFieldsInExt);
  }

  private Pmp itemToPmp(Item item, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (item == null) {
      return null;
    }

    Converter<net.media.openrtb3.Deal, Deal> dealDealConverter =
        converterProvider.fetch(new Conversion<>(net.media.openrtb3.Deal.class, Deal.class));

    Pmp pmp = new Pmp();

    pmp.setDeals(
        CollectionToCollectionConverter.convert(
            item.getDeal(), dealDealConverter, config, converterProvider));
    pmp.setPrivate_auction(item.getPriv());
    pmp.setExt(
      fetchExtFromFieldInExt(item.getExt(), CommonConstants.PMP, "Error in mapping pmp ext"));

    return pmp;
  }

  private VideoPlacement itemSpecPlacementVideo(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    VideoPlacement video = placement.getVideo();
    if (video == null) {
      return null;
    }
    return video;
  }

  private String itemSpecPlacementSdkver(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    String sdkver = placement.getSdkver();
    if (sdkver == null) {
      return null;
    }
    return sdkver;
  }

  private DisplayPlacement itemSpecPlacementDisplay(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    DisplayPlacement display = placement.getDisplay();
    if (display == null) {
      return null;
    }
    return display;
  }

  private String itemSpecPlacementTagid(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    String tagid = placement.getTagid();
    if (tagid == null) {
      return null;
    }
    return tagid;
  }

  private String itemSpecPlacementSdk(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    String sdk = placement.getSdk();
    if (sdk == null) {
      return null;
    }
    return sdk;
  }

  private AudioPlacement itemSpecPlacementAudio(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    AudioPlacement audio = placement.getAudio();
    if (audio == null) {
      return null;
    }
    return audio;
  }

  private Integer itemSpecPlacementSecure(Item item) {
    if (item == null) {
      return null;
    }
    Spec spec = item.getSpec();
    if (spec == null) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if (placement == null) {
      return null;
    }
    Integer secure = placement.getSecure();
    if (secure == null) {
      return null;
    }
    return secure;
  }

  private void fillExtMap(Item item, Imp imp, Config config) {
    if (nonNull(item)) {
      if (nonNull(imp)) {
        putToExt(item::getDt, imp.getExt(), CommonConstants.DT, imp::setExt);
        putToExt(item::getDlvy, imp.getExt(), CommonConstants.DLVY, imp::setExt);
      }
    }
    if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
      if (nonNull(imp)) {
        putToExt(
          item.getSpec().getPlacement()::getSsai,
          imp.getExt(),
          CommonConstants.SSAI,
          imp::setExt);
        putToExt(
          item.getSpec().getPlacement()::getReward,
          imp.getExt(),
          CommonConstants.REWARD,
          imp::setExt);
        putToExt(
          item.getSpec().getPlacement()::getAdmx,
          imp.getExt(),
          CommonConstants.ADMX,
          imp::setExt);
        putToExt(
          item.getSpec().getPlacement()::getCurlx,
          imp.getExt(),
          CommonConstants.CURLX,
          imp::setExt);
      }
      if (nonNull(item.getSpec().getPlacement().getDisplay())) {
        putToExt(
          item.getSpec().getPlacement().getDisplay()::getAmpren,
          imp.getExt(),
          CommonConstants.AMPREN,
          imp::setExt);
        putToExt(
          item.getSpec().getPlacement().getDisplay()::getEvent,
          imp.getExt(),
          CommonConstants.EVENT,
          imp::setExt);
      }
    }
  }
}
