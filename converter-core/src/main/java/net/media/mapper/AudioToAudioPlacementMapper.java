package net.media.mapper;

import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.AudioPlacement;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 25/12/18.
 */

@Mapper(uses = CompanionConverter.class)
public interface AudioToAudioPlacementMapper {

//  @Mappings({
//    @Mapping(source = "audio.mimes", target = "mime"),
//    @Mapping(source = "audio.startdelay", target = "delay"),
//    @Mapping(source = "audio.minduration", target = "mindur"),
//    @Mapping(source = "audio.maxduration", target = "maxdur"),
//    @Mapping(source = "audio.companiontype", target = "comptype"),
//    @Mapping(source = "audio.minduration", target = "minbitr"),
//    @Mapping(source = "audio.maxduration", target = "maxbitr"),
//    @Mapping(source = "audio.protocols", target = "ctype"),
//    @Mapping(source = "audio.maxextended", target = "maxext"),
//    @Mapping(source = "audio.companionad", target = "comp"),
//    @Mapping(source = "audio.ext", target = "ext"),
//  })
//  AudioPlacement map(Audio audio);

  @AfterMapping
  default void audioPlacementToAudioAfterMapping(Audio audio, @MappingTarget AudioPlacement
    audioPlacement) {
    if (nonNull(audio) && nonNull(audio.getExt()) && nonNull(audioPlacement)) {
      audioPlacement.setSkip((Integer) audio.getExt().get("skip"));
      audioPlacement.getExt().remove("skip");
      audioPlacement.setSkipmin((Integer) audio.getExt().get("skipmin"));
      audioPlacement.getExt().remove("skipmin");
      audioPlacement.setSkipafter((Integer) audio.getExt().get("skipafter"));
      audioPlacement.getExt().remove("skipafter");
      audioPlacement.setPlaymethod((Integer) audio.getExt().get("playmethod"));
      audioPlacement.getExt().remove("playmethod");
      audioPlacement.setPlayend((Integer) audio.getExt().get("playend"));
      audioPlacement.getExt().remove("playend");
    }
  }

  @Mappings({
    @Mapping(source = "audio.mimes", target = "mime"),
    @Mapping(source = "audio.startdelay", target = "delay"),
    @Mapping(source = "audio.minduration", target = "mindur"),
    @Mapping(source = "audio.maxduration", target = "maxdur"),
    @Mapping(source = "audio.companiontype", target = "comptype"),
    @Mapping(source = "audio.minduration", target = "minbitr"),
    @Mapping(source = "audio.maxduration", target = "maxbitr"),
    @Mapping(source = "audio.protocols", target = "ctype"),
    @Mapping(source = "audio.maxextended", target = "maxext"),
    @Mapping(source = "audio.companionad", target = "comp"),
    @Mapping(source = "audio.ext", target = "ext"),
  })
  AudioPlacement map(Audio audio, Imp imp, BidRequest bidRequest);

  @AfterMapping
  default void audioPlacementToAudioAfterMapping(Audio audio, Imp imp, BidRequest bidRequest,
                                                 @MappingTarget AudioPlacement audioPlacement) {
    if (nonNull(audio) && nonNull(audio.getExt()) && nonNull(audioPlacement)) {
      audioPlacement.setSkip((Integer) audio.getExt().get("skip"));
      audioPlacement.getExt().remove("skip");
      audioPlacement.setSkipmin((Integer) audio.getExt().get("skipmin"));
      audioPlacement.getExt().remove("skipmin");
      audioPlacement.setSkipafter((Integer) audio.getExt().get("skipafter"));
      audioPlacement.getExt().remove("skipafter");
      audioPlacement.setPlaymethod((Integer) audio.getExt().get("playmethod"));
      audioPlacement.getExt().remove("playmethod");
      audioPlacement.setPlayend((Integer) audio.getExt().get("playend"));
      audioPlacement.getExt().remove("playend");
    }
  }

  @InheritInverseConfiguration
  Audio map(AudioPlacement audioPlacement);

  @AfterMapping
  default void audioToAudioPlacementAfterMapping(AudioPlacement audioPlacement, @MappingTarget
                                                 Audio audio) {
    if (nonNull(audioPlacement) && nonNull(audioPlacement.getExt()) && nonNull(audio)) {
      if (isNull(audio.getExt())) {
        audio.setExt(new HashMap<>());
      }
      audio.getExt().put("skip", audioPlacement.getSkip());
      audio.getExt().put("skipmin", audioPlacement.getSkipmin());
      audio.getExt().put("skipafter", audioPlacement.getSkipafter());
      audio.getExt().put("playmethod", audioPlacement.getPlaymethod());
      audio.getExt().put("playend", audioPlacement.getPlayend());
    }
  }
}
