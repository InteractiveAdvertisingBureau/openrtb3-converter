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
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ItemToImpConverter implements Converter<Item, Imp> {

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
    Converter<DisplayPlacement, Native> displayPlacementNativeConverter =
        converterProvider.fetch(new Conversion<>(DisplayPlacement.class, Native.class));
    Converter<VideoPlacement, Video> videoPlacementVideoConverter =
        converterProvider.fetch(new Conversion<>(VideoPlacement.class, Video.class));
    Converter<AudioPlacement, Audio> audioPlacementAudioConverter =
        converterProvider.fetch(new Conversion<>(AudioPlacement.class, Audio.class));
    Converter<net.media.openrtb3.Metric, Metric> metricMetricConverter =
        converterProvider.fetch(new Conversion<>(net.media.openrtb3.Metric.class, Metric.class));
    imp.setPmp(itemToPmp(item, config, converterProvider));
    imp.setExt(MapUtils.copyMap(item.getExt(), config));
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
      if (nonNull(item.getQty())) {
        if (isNull(imp.getVideo().getExt())) {
          imp.getVideo().setExt(new HashMap<>());
        }
        imp.getVideo().getExt().put(CommonConstants.QTY, item.getQty());
      }
    }
    String sdkver = itemSpecPlacementSdkver(item);
    if (sdkver != null) {
      imp.setDisplaymanagerver(sdkver);
    }
    DisplayPlacement display = itemSpecPlacementDisplay(item);
    if (display != null) {
      imp.setBanner(displayPlacementBannerConverter.map(display, config, converterProvider));
      if (nonNull(imp.getBanner())) {
        if (nonNull(item.getSeq())) {
          if (isNull(imp.getBanner().getExt())) {
            imp.getBanner().setExt(new HashMap<>());
          }
          imp.getBanner().getExt().put(CommonConstants.SEQ, item.getSeq());
        }
        if (nonNull(item.getQty())) {
          if (isNull(imp.getBanner().getExt())) {
            imp.getBanner().setExt(new HashMap<>());
          }
          imp.getBanner().getExt().put(CommonConstants.QTY, item.getQty());
        }
      }
      imp.setNat(displayPlacementNativeConverter.map(display, config, converterProvider));
      if (nonNull(imp.getNat()) && nonNull(imp.getNat().getNativeRequestBody())) {
        imp.getNat().getNativeRequestBody().setPlcmtcnt(item.getQty());
        imp.getNat().getNativeRequestBody().setSeq(item.getSeq());
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
      if (nonNull(item.getQty())) {
        if (isNull(imp.getAudio().getExt())) {
          imp.getAudio().setExt(new HashMap<>());
        }
        imp.getAudio().getExt().put(CommonConstants.QTY, item.getQty());
      }
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
    if (item.getExt() != null) {
      if (item.getExt().containsKey(CommonConstants.PMP)) {
        try {
          Map<String, Object> pmp1 = (Map<String, Object>) item.getExt().get(CommonConstants.PMP);
          if (pmp1.containsKey("ext")) {
            pmp.setExt((Map<String, Object>) pmp1.get("ext"));
          }
          item.getExt().remove(CommonConstants.PMP);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("Error in converting pmp ext ", e);
        }
      }
    }

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
        if (isNull(imp.getExt())) {
          imp.setExt(new HashMap<>());
        }
        imp.getExt().put(CommonConstants.DT, item.getDt());
        imp.getExt().put(CommonConstants.DLVY, item.getDlvy());
      }
    }
    if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
      if (nonNull(imp)) {
        if (isNull(imp.getExt())) {
          imp.setExt(new HashMap<>());
        }
        if(item.getSpec().getPlacement().getSsai() != null)
          imp.getExt().put(CommonConstants.SSAI, item.getSpec().getPlacement().getSsai());
        if(item.getSpec().getPlacement().getReward() != null)
          imp.getExt().put(CommonConstants.REWARD, item.getSpec().getPlacement().getReward());
        if(item.getSpec().getPlacement().getAdmx() != null)
          imp.getExt().put(CommonConstants.ADMX, item.getSpec().getPlacement().getAdmx());
        if(item.getSpec().getPlacement().getCurlx() != null)
          imp.getExt().put(CommonConstants.CURLX, item.getSpec().getPlacement().getCurlx());
      }
      if (nonNull(item.getSpec().getPlacement().getDisplay())) {
        if(item.getSpec().getPlacement().getDisplay().getAmpren() != null)
          imp.getExt().put(CommonConstants.AMPREN, item.getSpec().getPlacement().getDisplay().getAmpren());
        if(item.getSpec().getPlacement().getDisplay().getEvent() != null)
          imp.getExt().put(CommonConstants.EVENT, item.getSpec().getPlacement().getDisplay().getEvent());
      }
    }
  }
}
