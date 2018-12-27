package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.VideoPlacement;
import net.media.util.FirstElement;
import net.media.util.IterableNonInterableUtil;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 26/12/18.
 */

@Mapper(uses = {IterableNonInterableUtil.class, CompanionConverter.class})
public interface VideoToVideoPlacementMapper {

//  @Mappings({
//    @Mapping(source = "video.placement", target = "ptype"),
//    @Mapping(source = "video.startdelay", target = "delay"),
//    @Mapping(source = "video.playbackmethod", target = "playmethod", qualifiedBy = FirstElement
//      .class),
//    @Mapping(source = "video.playbackend", target = "playend"),
//    @Mapping(source = "video.mimes", target = "mime"),
//    @Mapping(source = "video.minduration", target = "mindur"),
//    @Mapping(source = "video.maxduration", target = "maxdur"),
//    @Mapping(source = "video.maxextended", target = "maxext"),
//    @Mapping(source = "video.minbitrate", target = "minbitr"),
//    @Mapping(source = "video.maxbitrate", target = "maxbitr"),
//    @Mapping(source = "video.linearity", target = "linear"),
//    @Mapping(source = "video.boxingallowed", target = "boxing"),
//    @Mapping(source = "video.companiontype", target = "comptype"),
//    @Mapping(source = "video.protocols", target = "ctype"),
//    @Mapping(source = "video.companionad", target = "comp")
//  })
//  VideoPlacement videoToVideoPlacement(Video video);

  @AfterMapping
  default void videoToVideoPlacementAfterMapping(Video video, @MappingTarget VideoPlacement
    videoPlacement) {
    if (nonNull(video) && nonNull(video.getExt()) && nonNull(videoPlacement)) {
      videoPlacement.setUnit((Integer) video.getExt().get("unit"));
      videoPlacement.setMaxseq((Integer) video.getExt().get("maxseq"));
    }
  }

  @Mappings({
    @Mapping(target = "ext", ignore = true),
    @Mapping(source = "video.placement", target = "ptype"),
    @Mapping(source = "video.startdelay", target = "delay"),
    @Mapping(source = "video.playbackmethod", target = "playmethod", qualifiedBy = FirstElement
      .class),
    @Mapping(source = "video.playbackend", target = "playend"),
    @Mapping(source = "imp.clickbrowser", target = "clktype"),
    @Mapping(source = "video.mimes", target = "mime"),
    @Mapping(source = "video.minduration", target = "mindur"),
    @Mapping(source = "video.maxduration", target = "maxdur"),
    @Mapping(source = "video.maxextended", target = "maxext"),
    @Mapping(source = "video.minbitrate", target = "minbitr"),
    @Mapping(source = "video.maxbitrate", target = "maxbitr"),
    @Mapping(source = "video.linearity", target = "linear"),
    @Mapping(source = "video.boxingallowed", target = "boxing"),
    @Mapping(source = "video.companiontype", target = "comptype"),
    @Mapping(source = "video.protocols", target = "ctype"),
    @Mapping(source = "video.companionad", target = "comp")
  })
  VideoPlacement videoToVideoPlacement(Video video, Imp imp, BidRequest bidRequest);

  @AfterMapping
  default void videoToVideoPlacementAfterMapping(Video video, Imp imp, BidRequest bidRequest,
                                               @MappingTarget VideoPlacement videoPlacement) {
    if (nonNull(video) && nonNull(video.getExt()) && nonNull(videoPlacement)) {
        videoPlacement.setUnit((Integer) video.getExt().get("unit"));
        videoPlacement.setMaxseq((Integer) video.getExt().get("maxseq"));
    }
  }

  @InheritInverseConfiguration
  @Mappings({
    @Mapping(target = "playbackmethod", ignore = true)
  })
  Video videoPlacementToVideo(VideoPlacement videoPlacement);

  @AfterMapping
  default void videoPlacementToVideoAfterMapping(VideoPlacement videoPlacement, @MappingTarget
    Video video) {
    if (nonNull(videoPlacement) && nonNull(video)) {
      if (nonNull(videoPlacement.getPlaymethod())) {
        video.setPlaybackmethod(Collections.singleton(videoPlacement.getPlaymethod()));
      }
      if (nonNull(videoPlacement.getExt()) && !videoPlacement.getExt().isEmpty()) {
        video.setSequence((Integer) videoPlacement.getExt().get("sequence"));
      }
    }
  }
}
