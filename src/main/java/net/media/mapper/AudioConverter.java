package net.media.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;

import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T00:52:26+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class AudioConverter {

  private final CompanionConverter companionConverter = new CompanionConverter();

  public AudioPlacement map(Audio audio, Imp imp, BidRequest bidRequest) {
    if ( audio == null) {
      return null;
    }

    AudioPlacement audioPlacement = new AudioPlacement();

    if ( audio != null ) {
      List<Integer> list = audio.getCompaniontype();
      if ( list != null ) {
        audioPlacement.setComptype( new ArrayList<Integer>( list ) );
      }
      Map<String, Object> map = audio.getExt();
      if ( map != null ) {
        audioPlacement.setExt( new HashMap<String, Object>( map ) );
      }
      audioPlacement.setComp( bannerListToCompanionList( audio.getCompanionad(), imp, bidRequest
      ) );
      audioPlacement.setMaxdur( audio.getMaxduration() );
      audioPlacement.setMaxext( audio.getMaxextended() );
      audioPlacement.setDelay( audio.getStartdelay() );
      audioPlacement.setMindur( audio.getMinduration() );
      List<Integer> list2 = audio.getProtocols();
      if ( list2 != null ) {
        audioPlacement.setCtype( new ArrayList<Integer>( list2 ) );
      }
      List<String> list3 = audio.getMimes();
      if ( list3 != null ) {
        audioPlacement.setMime( new ArrayList<String>( list3 ) );
      }
      audioPlacement.setMinbitr( audio.getMinduration() );
      audioPlacement.setMaxbitr( audio.getMaxduration() );
      audioPlacement.setFeed( audio.getFeed() );
      audioPlacement.setNvol( audio.getNvol() );
      List<Integer> list4 = audio.getApi();
      if ( list4 != null ) {
        audioPlacement.setApi( new ArrayList<Integer>( list4 ) );
      }
      List<Integer> list5 = audio.getDelivery();
      if ( list5 != null ) {
        audioPlacement.setDelivery( new ArrayList<Integer>( list5 ) );
      }
      audioPlacement.setMaxseq( audio.getMaxseq() );
    }

    audioPlacementToAudioAfterMapping( audio, imp, bidRequest, audioPlacement );

    return audioPlacement;
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

  protected void audioPlacementToAudioAfterMapping(Audio audio, Imp imp, BidRequest bidRequest,
                                                   AudioPlacement audioPlacement) {
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

  protected void audioToAudioPlacementAfterMapping(AudioPlacement audioPlacement, Audio audio) {
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
