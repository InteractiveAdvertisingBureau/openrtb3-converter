package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
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
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class ImpToItemConverter implements Converter<Imp, Item> {

  @Override
  public Item map(Imp imp, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(imp)) {
      return null;
    }
    Item item = new Item();
    enhance(imp, item, config, converterProvider);
    return item;
  }

  @Override
  public void enhance(Imp imp, Item item, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(imp == null || item == null) {
      return;
    }
    Converter<Deal, net.media.openrtb3.Deal> dealDealConverter = converterProvider.fetch(new Conversion<>
            (Deal.class, net.media.openrtb3.Deal.class));
    Converter<Metric, net.media.openrtb3.Metric> metricMetricConverter = converterProvider.fetch(new Conversion<>
            (Metric.class, net.media.openrtb3.Metric.class));

      if ( item.getSpec() == null ) {
        item.setSpec( new Spec() );
      }
      impToSpec1( imp, item.getSpec(), config, converterProvider );
      Map<String, Object> map = imp.getExt();
      item.setExt(Utils.copyMap(map, config));
      if(imp.getPmp() != null && imp.getPmp().getExt() != null) {
        Pmp pmp = new Pmp();
        pmp.setExt(imp.getPmp().getExt());
        if(item.getExt() == null) {
          item.setExt(new HashMap<>());
        }
        item.getExt().put("pmp", pmp);
      }
      item.setFlrcur( imp.getBidfloorcur() );
      Collection<Deal> deals = impPmpDeals( imp );
      item.setDeal( CollectionToCollectionConverter.convert( deals, dealDealConverter, config,
        converterProvider) );
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

  }

  private void impToSpec1(Imp imp, Spec mappingTarget, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( imp == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    impToPlacement1( imp, mappingTarget.getPlacement(), config, converterProvider );
  }

  private void impToPlacement1(Imp imp, Placement mappingTarget, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( imp == null ) {
      return;
    }
    Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter = converterProvider.fetch(new Conversion<>
            (Banner.class, DisplayPlacement.class));
    Converter<Audio, AudioPlacement> audioAudioPlacementConverter = converterProvider.fetch(new Conversion<>
            (Audio.class, AudioPlacement.class));
    Converter<Video, VideoPlacement> videoVideoPlacementConverter = converterProvider.fetch(new Conversion<>
            (Video.class, VideoPlacement.class));
    Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter = converterProvider.fetch(new Conversion<>
            (Native.class, DisplayPlacement.class));
    mappingTarget.setAudio( audioAudioPlacementConverter.map( imp.getAudio(), config, converterProvider) );
    mappingTarget.setVideo( videoVideoPlacementConverter.map( imp.getVideo(), config, converterProvider) );
    if (nonNull(mappingTarget.getVideo())) {
      mappingTarget.getVideo().setClktype(imp.getClickbrowser());
    }
    mappingTarget.setTagid( imp.getTagId() );
    mappingTarget.setSdk( imp.getDisplaymanager() );
    mappingTarget.setSdkver( imp.getDisplaymanagerver() );
    mappingTarget.setSecure( imp.getSecure() );
    DisplayPlacement displayPlacement = bannerDisplayPlacementConverter.map(imp.getBanner(),
      config, converterProvider);
    try {
      if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(displayPlacement)) {
        if (imp.getExt().containsKey("ampren")) {
          displayPlacement.setAmpren((Integer) imp.getExt().get("ampren"));
        }
        if (imp.getExt().containsKey("event")) {
          displayPlacement.setEvent((EventSpec) imp.getExt().get("event"));
        }
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    if (isNull(displayPlacement) && nonNull(imp.getNat())) {
      displayPlacement = new DisplayPlacement();
    }
    nativeDisplayPlacementConverter.enhance(imp.getNat(), displayPlacement, config, converterProvider);
    mappingTarget.setDisplay( displayPlacement );
    if (nonNull(mappingTarget.getDisplay())) {
      mappingTarget.getDisplay().setClktype(imp.getClickbrowser());
      mappingTarget.getDisplay().setIfrbust(Utils.copyCollection(imp.getIframebuster(), config));
      mappingTarget.getDisplay().setInstl(imp.getInstl());
    }
  }

  private Collection<Deal> impPmpDeals(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if ( pmp == null ) {
      return null;
    }
    Collection<Deal> deals = pmp.getDeals();
    if ( deals == null ) {
      return null;
    }
    return deals;
  }

  private Integer getQuantity(Imp imp) throws OpenRtbConverterException {
    if ( imp == null ) {
      return null;
    }
    Video video = imp.getVideo();
    try {
      if (nonNull(video) && nonNull(video.getExt()) && video.getExt().containsKey("qty")) {
        return (Integer) video.getExt().get("qty");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    if (nonNull(imp.getNat()) && nonNull(imp.getNat().getNativeRequestBody())) {
      return imp.getNat().getNativeRequestBody().getPlcmtcnt();
    }
    Banner banner = imp.getBanner();
    try {
      if (nonNull(banner) && nonNull(banner.getExt()) && banner.getExt().containsKey("qty")) {
        return (Integer) banner.getExt().get("qty");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
    }
    Audio audio = imp.getAudio();
    try {
      if (nonNull(audio) && nonNull(audio.getExt()) && audio.getExt().containsKey("qty")) {
        return (Integer) audio.getExt().get("qty");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Imp", e);
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

  private Integer impSequence(Imp imp) throws OpenRtbConverterException {
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
    try {
      if (nonNull(banner) && nonNull(banner.getExt()) && banner.getExt().containsKey("seq")) {
        return (Integer) banner.getExt().get("seq");
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
    if (nonNull(imp) && nonNull(imp.getExt()) && !imp.getExt().isEmpty()) {
      try {
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
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for Imp", e);      }
    }
    if (nonNull(item.getExt())) {
      item.getExt().remove("ampren");
      item.getExt().remove("ctype");
    }
  }
}
