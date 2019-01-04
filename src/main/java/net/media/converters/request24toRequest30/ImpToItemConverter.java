package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
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

import java.util.ArrayList;
import java.util.HashMap;
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

  public ImpToItemConverter(Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter,
                            Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter,
                            Converter<Video, VideoPlacement> videoVideoPlacementConverter,
                            Converter<Audio, AudioPlacement> audioAudioPlacementConverter,
                            Converter<Deal, net.media.openrtb3.Deal> dealDealConverter,
                            Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
    this.nativeDisplayPlacementConverter = nativeDisplayPlacementConverter;
    this.videoVideoPlacementConverter = videoVideoPlacementConverter;
    this.audioAudioPlacementConverter = audioAudioPlacementConverter;
    this.dealDealConverter = dealDealConverter;
    this.metricMetricConverter = metricMetricConverter;
  }

  @Override
  public Item map(Imp imp, Config config) {
    if (isNull(imp)) {
      return null;
    }
    Item item = new Item();
    inhance(imp, item, config);
    return item;
  }

  @Override
  public void inhance(Imp imp, Item item, Config config) {
    if (nonNull(imp)) {
      if ( item.getSpec() == null ) {
        item.setSpec( new Spec() );
      }
      impToSpec1( imp, item.getSpec(), config );
      Map<String, Object> map = imp.getExt();
      if ( map != null ) {
        item.setExt( new HashMap<>( map ) );
      }
      item.setFlrcur( imp.getBidfloorcur() );
      List<Deal> deals = impPmpDeals( imp );
      item.setDeal( dealListToDealList( deals, config ) );
      item.setFlr( imp.getBidfloor() );
      item.setId( imp.getId() );
      Integer private_auction = impPmpPrivate_auction( imp );
      if ( private_auction != null ) {
        item.setPriv( private_auction );
      }
      Integer sequence = impSequence( imp );
      if ( sequence != null ) {
        item.setSeq( sequence );
        item.getExt().remove("sequence");
      }
      item.setExp( imp.getExp() );
      List<Metric> metrics = imp.getMetric();
      if (metrics != null) {
        List<net.media.openrtb3.Metric> metrics1 = new ArrayList<>(metrics.size());
        for (Metric metric : metrics) {
          metrics1.add(metricMetricConverter.map(metric, config));
        }
        item.setMetric(metrics1);
      }
      item.setQty(getPlcmtcntFromNative(imp));
    }
  }

  private void impToSpec1(Imp imp, Spec mappingTarget, Config config) {
    if ( imp == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    impToPlacement1( imp, mappingTarget.getPlacement(), config );
  }

  private void impToPlacement1(Imp imp, Placement mappingTarget, Config config) {
    if ( imp == null ) {
      return;
    }

    mappingTarget.setAudio( audioAudioPlacementConverter.map( imp.getAudio(), config) );
    mappingTarget.setVideo( videoVideoPlacementConverter.map( imp.getVideo(), config) );
    mappingTarget.setTagid( imp.getTagId() );
    mappingTarget.setSdk( imp.getDisplaymanager() );
    mappingTarget.setSdkver( imp.getDisplaymanagerver() );
    mappingTarget.setSecure( imp.getSecure() );
    DisplayPlacement displayPlacement = bannerDisplayPlacementConverter.map(imp.getBanner(),
      config);
    nativeDisplayPlacementConverter.inhance(imp.getNat(), displayPlacement, config);
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

  private List<net.media.openrtb3.Deal> dealListToDealList(List<Deal> list, Config config) {
    if ( list == null ) {
      return null;
    }

    List<net.media.openrtb3.Deal> list1 = new ArrayList<net.media.openrtb3.Deal>( list.size() );
    for ( Deal deal : list ) {
      list1.add( dealDealConverter.map(deal, config) );
    }

    return list1;
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
    return null;
  }
}
