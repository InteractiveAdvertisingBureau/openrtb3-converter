package net.media.converters.request25toRequest30;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {

  private Converter<Banner, Companion> bannerCompanionConverter;

  @java.beans.ConstructorProperties({"bannerCompanionConverter"})
  public AudioToAudioPlacementConverter(Converter<Banner, Companion> bannerCompanionConverter) {
    this.bannerCompanionConverter = bannerCompanionConverter;
  }

  @Override
  public AudioPlacement map(Audio audio, Config config) throws OpenRtbConverterException {
    if ( audio == null) {
      return null;
    }

    AudioPlacement audioPlacement = new AudioPlacement();
    enhance(audio, audioPlacement, config);

    return audioPlacement;
  }

  @Override
  public void enhance(Audio audio, AudioPlacement audioPlacement, Config config) throws OpenRtbConverterException {
    audioPlacement.setComptype(Utils.copyList(audio.getCompaniontype(), config));
    audioPlacement.setExt(Utils.copyMap(audio.getExt(), config));
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
    audioPlacement.setCtype(Utils.copyList(audio.getProtocols(), config));
    audioPlacement.setMime(Utils.copyList(audio.getMimes(), config));
    audioPlacement.setMinbitr( audio.getMinduration() );
    audioPlacement.setMaxbitr( audio.getMaxduration() );
    audioPlacement.setFeed( audio.getFeed() );
    audioPlacement.setNvol( audio.getNvol() );
    audioPlacement.setApi(Utils.copyList(audio.getApi(), config));
    audioPlacement.setDelivery(Utils.copyList(audio.getDelivery(), config));
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
