package net.media.converters.request30toRequest24;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemToImpConverter implements Converter<Item, Imp> {

  private Converter<DisplayPlacement, Banner> displayPlacementBannerConverter;

  private Converter<DisplayPlacement, Native> displayPlacementNativeConverter;

  private Converter<VideoPlacement, Video> videoPlacementVideoConverter;

  private Converter<AudioPlacement, Audio> audioPlacementAudioConverter;

  private Converter<net.media.openrtb3.Metric, Metric> metricMetricConverter;

  private Converter<net.media.openrtb3.Deal, Deal> dealDealConverter;

  @Override
  public Imp map(Item item, Config config) {
    if ( item == null ) {
      return null;
    }

    Imp imp = new Imp();
    inhance(item, imp, config);

    return imp;
  }

  @Override
  public void inhance(Item item, Imp imp, Config config) {
    imp.setPmp( itemToPmp( item, config ) );
    Map<String, Object> map = item.getExt();
    if ( map != null ) {
      imp.setExt( new HashMap<String, Object>( map ) );
    }
    imp.setBidfloor( item.getFlr() );
    VideoPlacement video = itemSpecPlacementVideo( item );
    if ( video != null ) {
      imp.setVideo( videoPlacementVideoConverter.map(video, config) );
    }
    String sdkver = itemSpecPlacementSdkver( item );
    if ( sdkver != null ) {
      imp.setDisplaymanagerver( sdkver );
    }
    DisplayPlacement display = itemSpecPlacementDisplay( item );
    if ( display != null ) {
      imp.setBanner( displayPlacementBannerConverter.map( display, config ) );
      imp.setNat(displayPlacementNativeConverter.map(display, config));
      imp.setInstl(display.getInstl());
      imp.setIframebuster(new ArrayList<>(display.getIfrbust()));
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

  private Pmp itemToPmp(Item item, Config config) {
    if ( item == null ) {
      return null;
    }

    Pmp pmp = new Pmp();

    pmp.setDeals( dealListToDealList1( item.getDeal(), config ) );
    pmp.setPrivate_auction( item.getPriv() );

    return pmp;
  }

  private List<Deal> dealListToDealList1(List<net.media.openrtb3.Deal> list, Config config) {
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
}
