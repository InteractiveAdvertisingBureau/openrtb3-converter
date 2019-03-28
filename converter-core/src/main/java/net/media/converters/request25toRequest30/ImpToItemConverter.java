package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Deal;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Metric;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.Pmp;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.EventSpec;
import net.media.openrtb3.Item;
import net.media.openrtb3.Placement;
import net.media.openrtb3.Spec;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.ListToListConverter;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class ImpToItemConverter implements Converter<Imp, Item> {

  private Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter;

  private Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter;

  private Converter<Video, VideoPlacement> videoVideoPlacementConverter;

  private Converter<Audio, AudioPlacement> audioAudioPlacementConverter;

  private Converter<Deal, net.media.openrtb3.Deal> dealDealConverter;

  private Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter;

  @java.beans.ConstructorProperties({"bannerDisplayPlacementConverter", "nativeDisplayPlacementConverter", "videoVideoPlacementConverter", "audioAudioPlacementConverter", "dealDealConverter", "metricMetricConverter"})
  public ImpToItemConverter(Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter, Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter, Converter<Video, VideoPlacement> videoVideoPlacementConverter, Converter<Audio, AudioPlacement> audioAudioPlacementConverter, Converter<Deal, net.media.openrtb3.Deal> dealDealConverter, Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
    this.nativeDisplayPlacementConverter = nativeDisplayPlacementConverter;
    this.videoVideoPlacementConverter = videoVideoPlacementConverter;
    this.audioAudioPlacementConverter = audioAudioPlacementConverter;
    this.dealDealConverter = dealDealConverter;
    this.metricMetricConverter = metricMetricConverter;
  }

  @Override
  public Item map(Imp imp, Config config) throws OpenRtbConverterException {
    if (isNull(imp)) {
      return null;
    }
    Item item = new Item();
    enhance(imp, item, config);
    return item;
  }

  @Override
  public void enhance(Imp imp, Item item, Config config) throws OpenRtbConverterException {
    if (nonNull(imp)) {
      if ( item.getSpec() == null ) {
        item.setSpec( new Spec() );
      }
      impToSpec1( imp, item.getSpec(), config );
      Map<String, Object> map = imp.getExt();
      item.setExt(Utils.copyMap(map, config));
      item.setFlrcur( imp.getBidfloorcur() );
      List<Deal> deals = impPmpDeals( imp );
      item.setDeal( ListToListConverter.convert( deals, dealDealConverter, config ) );
      item.setFlr( imp.getBidfloor() );
      item.setId( imp.getId() );
      Integer private_auction = impPmpPrivate_auction( imp );
      if ( private_auction != null ) {
        item.setPriv( private_auction );
      }
      Integer sequence = impSequence( imp );
      if ( sequence != null ) {
        item.setSeq( sequence );
        item.getExt().remove("seq");
      }
      item.setExp( imp.getExp() );
      List<Metric> metrics = imp.getMetric();
      if (metrics != null) {
        item.setMetric(ListToListConverter.convert(metrics, metricMetricConverter, config));
      }
      item.setQty(getPlcmtcntFromNative(imp));
      impToItemAfterMapping(imp, item);
    }
  }

  private void impToSpec1(Imp imp, Spec mappingTarget, Config config) throws OpenRtbConverterException {
    if ( imp == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    impToPlacement1( imp, mappingTarget.getPlacement(), config );
  }

  private void impToPlacement1(Imp imp, Placement mappingTarget, Config config) throws OpenRtbConverterException {
    if ( imp == null ) {
      return;
    }

    mappingTarget.setAudio( audioAudioPlacementConverter.map( imp.getAudio(), config) );
    mappingTarget.setVideo( videoVideoPlacementConverter.map( imp.getVideo(), config) );
    if (nonNull(mappingTarget.getVideo())) {
      mappingTarget.getVideo().setClktype(imp.getClickbrowser());
    }
    mappingTarget.setTagid( imp.getTagId() );
    mappingTarget.setSdk( imp.getDisplaymanager() );
    mappingTarget.setSdkver( imp.getDisplaymanagerver() );
    mappingTarget.setSecure( imp.getSecure() );
    DisplayPlacement displayPlacement = bannerDisplayPlacementConverter.map(imp.getBanner(),
      config);
    if (nonNull(mappingTarget.getDisplay())) {
      mappingTarget.getDisplay().setClktype(imp.getClickbrowser());
      mappingTarget.getDisplay().setIfrbust(Utils.copyList(imp.getIframebuster(), config));
      mappingTarget.getDisplay().setInstl(imp.getInstl());
    }
    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(displayPlacement)) {
      if (imp.getExt().containsKey("ampren")) {
        displayPlacement.setAmpren((Integer) imp.getExt().get("ampren"));
      }
      if (imp.getExt().containsKey("priv")) {
        displayPlacement.setPriv((Integer) imp.getExt().get("priv"));
      }
      if (imp.getExt().containsKey("event")) {
        displayPlacement.setEvent((EventSpec) imp.getExt().get("event"));
      }
    }
    if (isNull(displayPlacement) && nonNull(imp.getNat())) {
      displayPlacement = new DisplayPlacement();
    }
    nativeDisplayPlacementConverter.enhance(imp.getNat(), displayPlacement, config);
    mappingTarget.setDisplay( displayPlacement );
  }

  private List<Deal> impPmpDeals(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if ( pmp == null ) {
      return null;
    }
    List<Deal> deals = pmp.getDeals();
    if ( deals == null ) {
      return null;
    }
    return deals;
  }

  private Integer getPlcmtcntFromNative(Imp imp) {
    if (nonNull(imp) && nonNull(imp.getNat()) && nonNull(imp.getNat().getNativeRequestBody())) {
      return imp.getNat().getNativeRequestBody().getPlcmtcnt();
    }
    return null;
  }

  private Integer impPmpPrivate_auction(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if ( pmp == null ) {
      return null;
    }
    Integer private_auction = pmp.getPrivate_auction();
    if ( private_auction == null ) {
      return null;
    }
    return private_auction;
  }

  private Integer impSequence(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Video video = imp.getVideo();
    if (nonNull(video) && nonNull(video.getSequence())) {
      return video.getSequence();
    }
    Native nat = imp.getNat();
    if (nonNull(nat) && nonNull(nat.getNativeRequestBody()) && nonNull(nat.getNativeRequestBody()
      .getSeq())) {
      return nat.getNativeRequestBody().getSeq();
    }
    Banner banner = imp.getBanner();
    if (nonNull(banner) && nonNull(banner.getExt()) && banner.getExt().containsKey("seq")) {
      return (Integer) banner.getExt().get("seq");
    }
    Audio audio = imp.getAudio();
    if (nonNull(audio) && nonNull(audio.getSequence())) {
      return audio.getSequence();
    }
    return null;
  }

  private void impToItemAfterMapping(Imp imp, Item item) {
    if (nonNull(imp) && nonNull(imp.getExt()) && !imp.getExt().isEmpty()) {
      if (nonNull(item) && nonNull(imp.getExt())) {
        if (imp.getExt().containsKey("dt")) {
          item.setDt((Integer) imp.getExt().get("dt"));
          item.getExt().remove("dt");
        }
        if (imp.getExt().containsKey("dlvy")) {
          item.setDlvy((Integer) imp.getExt().get("dlvy"));
          item.getExt().remove("dlvy");
        }
      }
      if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement()) &&
        nonNull(imp.getExt())) {
        if (imp.getExt().containsKey("ssai")) {
          item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
          item.getExt().remove("ssai");
        }
        if (imp.getExt().containsKey("reward")) {
          item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
          item.getExt().remove("reward");
        }
        if (imp.getExt().containsKey("admx")) {
          item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
          item.getExt().remove("admx");
        }
        if (imp.getExt().containsKey("curlx")) {
          item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
          item.getExt().remove("curlx");
        }
      }
    }
    if (nonNull(item.getExt())) {
      item.getExt().remove("ampren");
      item.getExt().remove("ctype");
    }
  }
}
