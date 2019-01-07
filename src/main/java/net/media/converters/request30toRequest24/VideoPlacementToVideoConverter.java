package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class VideoPlacementToVideoConverter implements Converter<VideoPlacement, Video> {

  private Converter<Companion, Banner> companionBannerConverter;

  @Override
  public Video map(VideoPlacement videoPlacement, Config config) {
    if ( videoPlacement == null ) {
      return null;
    }

    Video video = new Video();
    inhance(videoPlacement, video, config);

    return video;
  }

  @Override
  public void inhance(VideoPlacement videoPlacement, Video video, Config config) {
    if (isNull(video) || isNull(videoPlacement)) {
      return;
    }
    video.setMinbitrate( videoPlacement.getMinbitr() );
    video.setMaxbitrate( videoPlacement.getMaxbitr() );
    Set<Integer> set = videoPlacement.getCtype();
    if ( set != null ) {
      video.setProtocols( new HashSet<Integer>( set ) );
    }
    video.setBoxingallowed( videoPlacement.getBoxing() );
    video.setPlacement( videoPlacement.getPtype() );
    video.setPlaybackend( videoPlacement.getPlayend() );
    video.setMinduration( videoPlacement.getMindur() );
    List<Integer> list = videoPlacement.getComptype();
    if ( list != null ) {
      video.setCompaniontype( new ArrayList<Integer>( list ) );
    }
    video.setCompanionad( companionListToBannerList( videoPlacement.getComp(), config ) );
    Set<String> set1 = videoPlacement.getMime();
    if ( set1 != null ) {
      video.setMimes( new HashSet<String>( set1 ) );
    }
    video.setMaxduration( videoPlacement.getMaxdur() );
    video.setMaxextended( videoPlacement.getMaxext() );
    video.setStartdelay( videoPlacement.getDelay() );
    video.setLinearity( videoPlacement.getLinear() );
    video.setW( videoPlacement.getW() );
    video.setH( videoPlacement.getH() );
    video.setSkip( videoPlacement.getSkip() );
    video.setSkipmin( videoPlacement.getSkipmin() );
    video.setSkipafter( videoPlacement.getSkipafter() );
    List<Integer> list2 = videoPlacement.getDelivery();
    if ( list2 != null ) {
      video.setDelivery( new ArrayList<Integer>( list2 ) );
    }
    video.setPos( videoPlacement.getPos() );
    Set<Integer> list3 = videoPlacement.getApi();
    if ( list3 != null ) {
      video.setApi( new HashSet<Integer>( list3 ) );
    }
    Map<String, Object> map = videoPlacement.getExt();
    if ( map != null ) {
      video.setExt( new HashMap<String, Object>( map ) );
    }

    videoPlacementToVideoAfterMapping( videoPlacement, video );
  }

  private List<Banner> companionListToBannerList(List<Companion> list, Config config) {
    if ( list == null ) {
      return null;
    }

    List<Banner> list1 = new ArrayList<Banner>( list.size() );
    for ( Companion companion : list ) {
      list1.add( companionBannerConverter.map( companion, config ) );
    }

    return list1;
  }

  private void videoPlacementToVideoAfterMapping(VideoPlacement videoPlacement, Video video) {
    if (nonNull(videoPlacement) && nonNull(video)) {
      if (nonNull(videoPlacement.getPlaymethod())) {
        video.setPlaybackmethod(Collections.singleton(videoPlacement.getPlaymethod()));
      }
    }
    if (nonNull(videoPlacement.getUnit())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("unit", videoPlacement.getUnit());
    }
    if (nonNull(videoPlacement.getMaxseq())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("maxseq", videoPlacement.getMaxseq());
    }
  }
}
