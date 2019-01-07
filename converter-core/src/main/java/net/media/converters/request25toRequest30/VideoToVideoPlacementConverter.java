package net.media.converters.request25toRequest30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

@AllArgsConstructor
public class VideoToVideoPlacementConverter implements Converter<Video, VideoPlacement> {

  private Converter<Banner, Companion> bannerCompanionConverter;

  @Override
  public VideoPlacement map(Video video, Config config) throws OpenRtbConverterException {
    if ( video == null) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();
    inhance(video, videoPlacement, config);

    return videoPlacement;
  }

  @Override
  public void inhance(Video video, VideoPlacement videoPlacement, Config config) throws OpenRtbConverterException {
    if (isNull(video) || isNull(videoPlacement)) {
      return;
    }
    List<Integer> list = video.getCompaniontype();
    if ( list != null ) {
      videoPlacement.setComptype( new ArrayList<Integer>( list ) );
    }
    videoPlacement.setComp( bannerListToCompanionList( video.getCompanionad(), config ) );
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
    videoPlacement.setPlaymethod( CollectionUtils.firstElementFromCollection( video
      .getPlaybackmethod() ) );
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
    videoToVideoPlacementAfterMapping(video, videoPlacement);
  }

  private List<Companion> bannerListToCompanionList(List<Banner> list, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<Companion> list1 = new ArrayList<Companion>( list.size() );
    for ( Banner banner : list ) {
      list1.add( bannerCompanionConverter.map( banner, config ) );
    }

    return list1;
  }

  private void videoToVideoPlacementAfterMapping(Video video, VideoPlacement videoPlacement) {
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
}
