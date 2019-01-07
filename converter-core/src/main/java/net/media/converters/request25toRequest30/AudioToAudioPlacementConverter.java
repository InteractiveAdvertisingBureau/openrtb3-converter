package net.media.converters.request25toRequest30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */
public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {

  private Converter<Banner, Companion> bannerCompanionConverter;

  public AudioToAudioPlacementConverter(Converter<Banner, Companion>
                                          bannerCompanionConverter) {
    this.bannerCompanionConverter = bannerCompanionConverter;
  }

  @Override
  public AudioPlacement map(Audio audio, Config config) throws OpenRtbConverterException {
    if ( audio == null) {
      return null;
    }

    AudioPlacement audioPlacement = new AudioPlacement();
    inhance(audio, audioPlacement, config);

    return audioPlacement;
  }

  @Override
  public void inhance(Audio audio, AudioPlacement audioPlacement, Config config) throws OpenRtbConverterException {
    List<Integer> list = audio.getCompaniontype();
    if ( list != null ) {
      audioPlacement.setComptype( new ArrayList<Integer>( list ) );
    }
    Map<String, Object> map = audio.getExt();
    if ( map != null ) {
      audioPlacement.setExt( new HashMap<String, Object>( map ) );
    }
    if (nonNull(audio.getStitched())) {
      if (isNull(audioPlacement.getExt())) {
        audioPlacement.setExt(new HashMap<>());
      }
      audioPlacement.getExt().put("stitched", audio.getStitched());
    }
    audioPlacement.setComp( bannerListToCompanionList( audio.getCompanionad(), config ) );
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
    audioToAudioPlacementAfterMapping( audio, audioPlacement );
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

  private void audioToAudioPlacementAfterMapping(Audio audio, AudioPlacement audioPlacement) {
    if (nonNull(audio) && nonNull(audio.getExt()) && nonNull(audioPlacement)) {
      if (audio.getExt().containsKey("skip")) {
        audioPlacement.setSkip((Integer) audio.getExt().get("skip"));
        audioPlacement.getExt().remove("skip");
      }
      if (audio.getExt().containsKey("skipmin")) {
        audioPlacement.setSkipmin((Integer) audio.getExt().get("skipmin"));
        audioPlacement.getExt().remove("skipmin");
      }
      if (audio.getExt().containsKey("skipafter")) {
        audioPlacement.setSkipafter((Integer) audio.getExt().get("skipafter"));
        audioPlacement.getExt().remove("skipafter");
      }
      if (audio.getExt().containsKey("playmethod")) {
        audioPlacement.setPlaymethod((Integer) audio.getExt().get("playmethod"));
        audioPlacement.getExt().remove("playmethod");
      }
      if (audio.getExt().containsKey("playend")) {
        audioPlacement.setPlayend((Integer) audio.getExt().get("playend"));
        audioPlacement.getExt().remove("playend");
      }
    }
  }
}
