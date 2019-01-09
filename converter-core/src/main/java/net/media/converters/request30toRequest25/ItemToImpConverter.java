package net.media.converters.request30toRequest25;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Deal;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Metric;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.Pmp;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Placement;
import net.media.openrtb3.Spec;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ItemToImpConverter implements Converter<Item, Imp> {

  private Converter<DisplayPlacement, Banner> displayPlacementBannerConverter;

  private Converter<DisplayPlacement, Native> displayPlacementNativeConverter;

  private Converter<VideoPlacement, Video> videoPlacementVideoConverter;

  private Converter<AudioPlacement, Audio> audioPlacementAudioConverter;

  private Converter<net.media.openrtb3.Metric, Metric> metricMetricConverter;

  private Converter<net.media.openrtb3.Deal, Deal> dealDealConverter;

  @Override
  public Imp map(Item item, Config config) throws OpenRtbConverterException {
    if ( item == null ) {
      return null;
    }

    Imp imp = new Imp();
    inhance(item, imp, config);

    return imp;
  }

  @Override
  public void inhance(Item item, Imp imp, Config config) throws OpenRtbConverterException {
    if (isNull(imp) || isNull(item)) {
      return;
    }
    imp.setPmp( itemToPmp( item, config ) );
    imp.setExt(Utils.copyMap(item.getExt(), config));
    fillExtMap(item, imp, config);
    imp.setBidfloor( item.getFlr() );
    VideoPlacement video = itemSpecPlacementVideo( item );
    if ( video != null ) {
      imp.setVideo( videoPlacementVideoConverter.map(video, config) );
    }
    if (nonNull(imp.getVideo())) {
      imp.getVideo().setSequence(item.getSeq());
    }
    String sdkver = itemSpecPlacementSdkver( item );
    if ( sdkver != null ) {
      imp.setDisplaymanagerver( sdkver );
    }
    DisplayPlacement display = itemSpecPlacementDisplay( item );
    if ( display != null ) {
      imp.setBanner( displayPlacementBannerConverter.map( display, config ) );
      if (nonNull(imp.getBanner())) {
        if (nonNull(item.getSeq())) {
          if (isNull(imp.getBanner().getExt())) {
            imp.getBanner().setExt(new HashMap<>());
          }
          imp.getBanner().getExt().put("seq", item.getSeq());
        }
      }
      imp.setNat(displayPlacementNativeConverter.map(display, config));
      if (nonNull(imp.getNat()) && nonNull(imp.getNat().getNativeRequestBody())) {
        imp.getNat().getNativeRequestBody().setPlcmtcnt(item.getQty());
        imp.getNat().getNativeRequestBody().setSeq(item.getSeq());
      }
      imp.setInstl(display.getInstl());
      imp.setIframebuster(Utils.copyList(display.getIfrbust(), config));
      imp.setClickbrowser(display.getClktype());
    }
    imp.setBidfloorcur( item.getFlrcur() );
    String tagid = itemSpecPlacementTagid( item );
    if ( tagid != null ) {
      imp.setTagId( tagid );
    }
    String sdk = itemSpecPlacementSdk( item );
    if ( sdk != null ) {
      imp.setDisplaymanager( sdk );
    }
    AudioPlacement audio = itemSpecPlacementAudio( item );
    if ( audio != null ) {
      imp.setAudio( audioPlacementAudioConverter.map(audio, config));
    }
    if (nonNull(imp.getAudio())) {
      imp.getAudio().setSequence(item.getSeq());
    }
    imp.setId( item.getId() );
    imp.setExp( item.getExp() );
    Integer secure = itemSpecPlacementSecure( item );
    if ( secure != null ) {
      imp.setSecure( secure );
    }
    List<net.media.openrtb3.Metric> metrics = item.getMetric();
    if (metrics != null) {
      List<Metric> metrics1 = new ArrayList<>(metrics.size());
      for (net.media.openrtb3.Metric metric : metrics) {
        metrics1.add(metricMetricConverter.map(metric, config));
      }
      imp.setMetric(metrics1);
    }
  }

  private Pmp itemToPmp(Item item, Config config) throws OpenRtbConverterException {
    if ( item == null ) {
      return null;
    }

    Pmp pmp = new Pmp();

    pmp.setDeals( dealListToDealList1( item.getDeal(), config ) );
    pmp.setPrivate_auction( item.getPriv() );

    return pmp;
  }

  private List<Deal> dealListToDealList1(List<net.media.openrtb3.Deal> list, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<Deal> list1 = new ArrayList<Deal>( list.size() );
    for ( net.media.openrtb3.Deal deal : list ) {
      list1.add( dealDealConverter.map( deal, config ) );
    }

    return list1;
  }

  private VideoPlacement itemSpecPlacementVideo(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    VideoPlacement video = placement.getVideo();
    if ( video == null ) {
      return null;
    }
    return video;
  }

  private String itemSpecPlacementSdkver(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String sdkver = placement.getSdkver();
    if ( sdkver == null ) {
      return null;
    }
    return sdkver;
  }

  private DisplayPlacement itemSpecPlacementDisplay(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    DisplayPlacement display = placement.getDisplay();
    if ( display == null ) {
      return null;
    }
    return display;
  }

  private String itemSpecPlacementTagid(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String tagid = placement.getTagid();
    if ( tagid == null ) {
      return null;
    }
    return tagid;
  }

  private String itemSpecPlacementSdk(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String sdk = placement.getSdk();
    if ( sdk == null ) {
      return null;
    }
    return sdk;
  }

  private AudioPlacement itemSpecPlacementAudio(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    AudioPlacement audio = placement.getAudio();
    if ( audio == null ) {
      return null;
    }
    return audio;
  }

  private Integer itemSpecPlacementSecure(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    Integer secure = placement.getSecure();
    if ( secure == null ) {
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
        imp.getExt().put("qty", item.getQty());
        imp.getExt().put("dt", item.getDt());
        imp.getExt().put("dlvy", item.getDlvy());
      }
    }
    if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
      if (nonNull(imp)) {
        if (isNull(imp.getExt())) {
          imp.setExt(new HashMap<>());
        }
        imp.getExt().put("ssai", item.getSpec().getPlacement().getSsai());
        imp.getExt().put("reward", item.getSpec().getPlacement().getReward());
        imp.getExt().put("admx", item.getSpec().getPlacement().getAdmx());
        imp.getExt().put("curlx", item.getSpec().getPlacement().getCurlx());
      }
      if (nonNull(item.getSpec().getPlacement().getDisplay())) {
        imp.getExt().put("ampren", item.getSpec().getPlacement().getDisplay().getAmpren());
        if (nonNull(item.getSpec().getPlacement().getDisplay().getCtype())) {
          imp.getExt().put("ctype", Utils.copyList(item.getSpec().getPlacement().getDisplay()
            .getCtype(), config));
        }
        imp.getExt().put("priv", item.getSpec().getPlacement().getDisplay().getPriv());
        imp.getExt().put("event", item.getSpec().getPlacement().getDisplay().getEvent());
      }
    }
  }
}
