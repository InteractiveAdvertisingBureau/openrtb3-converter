package net.media.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.IterableNonInterableUtil;

import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T01:23:48+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class VideoConverter {

  private final IterableNonInterableUtil iterableNonInterableUtil = new IterableNonInterableUtil();
  private final CompanionConverter companionConverter = new CompanionConverter();

  public VideoPlacement videoToVideoPlacement(Video video, Imp imp, BidRequest bidRequest) {
    if ( video == null) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();

    if ( video != null ) {
      List<Integer> list = video.getCompaniontype();
      if ( list != null ) {
        videoPlacement.setComptype( new ArrayList<Integer>( list ) );
      }
      videoPlacement.setComp( bannerListToCompanionList( video.getCompanionad(), imp, bidRequest ) );
      videoPlacement.setLinear( video.getLinearity() );
      Set<String> set = video.getMimes();
      if ( set != null ) {
        videoPlacement.setMime( new HashSet<String>( set ) );
      }
      videoPlacement.setMinbitr( video.getMinbitrate() );
      videoPlacement.setPtype( video.getPlacement() );
      videoPlacement.setMaxdur( video.getMaxduration() );
      videoPlacement.setMaxext( video.getMaxextended() );
      videoPlacement.setDelay( video.getStartdelay() );
      videoPlacement.setPlayend( video.getPlaybackend() );
      videoPlacement.setMindur( video.getMinduration() );
      Set<Integer> set1 = video.getProtocols();
      if ( set1 != null ) {
        videoPlacement.setCtype( new HashSet<Integer>( set1 ) );
      }
      videoPlacement.setBoxing( video.getBoxingallowed() );
      videoPlacement.setPlaymethod( iterableNonInterableUtil.first( video.getPlaybackmethod() ) );
      videoPlacement.setMaxbitr( video.getMaxbitrate() );
      videoPlacement.setPos( video.getPos() );
      videoPlacement.setSkip( video.getSkip() );
      videoPlacement.setSkipmin( video.getSkipmin() );
      videoPlacement.setSkipafter( video.getSkipafter() );
      videoPlacement.setApi(video.getApi());
      videoPlacement.setW( video.getW() );
      videoPlacement.setH( video.getH() );
      List<Integer> list2 = video.getDelivery();
      if ( list2 != null ) {
        videoPlacement.setDelivery( new ArrayList<Integer>( list2 ) );
      }
    }
    if ( imp != null ) {
      videoPlacement.setClktype( imp.getClickbrowser() );
    }

    videoToVideoPlacementAfterMapping( video, imp, bidRequest, videoPlacement );

    return videoPlacement;
  }

  public Video videoPlacementToVideo(VideoPlacement videoPlacement, Item item, Request request) {
    if ( videoPlacement == null ) {
      return null;
    }

    Video video = new Video();

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
    video.setCompanionad( companionListToBannerList( videoPlacement.getComp() ) );
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
    if (nonNull(request)) {
      if (nonNull(request.getContext())) {
        if (nonNull(request.getContext().getRestrictions())) {
          if (nonNull(request.getContext().getRestrictions())) {
            if (nonNull(request.getContext().getRestrictions().getBattr())) {
              video.setBattr(new HashSet<>(request.getContext().getRestrictions().getBattr()));
            }
          }
        }
      }
    }
    video.setSequence(item.getSeq());
    Map<String, Object> map = videoPlacement.getExt();
    if ( map != null ) {
      video.setExt( new HashMap<String, Object>( map ) );
    }

    videoPlacementToVideoAfterMapping( videoPlacement, item, request, video );

    return video;
  }

  protected List<Companion> bannerListToCompanionList(List<Banner> list, Imp imp, BidRequest bidRequest) {
    if ( list == null ) {
      return null;
    }

    List<Companion> list1 = new ArrayList<Companion>( list.size() );
    for ( Banner banner : list ) {
      list1.add( companionConverter.map( banner, imp, bidRequest ) );
    }

    return list1;
  }

  protected List<Banner> companionListToBannerList(List<Companion> list) {
    if ( list == null ) {
      return null;
    }

    List<Banner> list1 = new ArrayList<Banner>( list.size() );
    for ( Companion companion : list ) {
      list1.add( companionConverter.map( companion ) );
    }

    return list1;
  }

  protected void videoToVideoPlacementAfterMapping(Video video, Imp imp, BidRequest bidRequest,
                                                   VideoPlacement videoPlacement) {
    if (nonNull(video) && nonNull(video.getExt()) && nonNull(videoPlacement)) {
      if (video.getExt().containsKey("unit")) {
        videoPlacement.setUnit((Integer) video.getExt().get("unit"));
        videoPlacement.getExt().remove("unit");
      }
      if (video.getExt().containsKey("maxseq")) {
        videoPlacement.setMaxseq((Integer) video.getExt().get("maxseq"));
        videoPlacement.getExt().remove("maxseq");
      }
    }
  }

  protected void videoPlacementToVideoAfterMapping(VideoPlacement videoPlacement, Item item,
                                                   Request request, Video video) {
    if (nonNull(videoPlacement) && nonNull(video)) {
      if (nonNull(videoPlacement.getPlaymethod())) {
        video.setPlaybackmethod(Collections.singleton(videoPlacement.getPlaymethod()));
      }
    }
  }
}
