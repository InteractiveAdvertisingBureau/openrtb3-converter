package net.media.converters.request30toRequest25;

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

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class AudioPlacementToAudioConverter implements Converter<AudioPlacement, Audio> {

  private Converter<Companion, Banner> companionBannerConverter;

  @Override
  public Audio map(AudioPlacement audioPlacement, Config config) throws OpenRtbConverterException {
    if ( audioPlacement == null ) {
      return null;
    }

    Audio audio = new Audio();
    inhance(audioPlacement, audio, config);

    return audio;
  }

  @Override
  public void inhance(AudioPlacement audioPlacement, Audio audio, Config config) throws OpenRtbConverterException {
    if (isNull(audioPlacement) || isNull(audio)) {
      return;
    }
    List<Integer> list = audioPlacement.getDelivery();
    if ( list != null ) {
      audio.setDelivery( new ArrayList<>( list ) );
    }
    List<Integer> list1 = audioPlacement.getApi();
    if ( list1 != null ) {
      audio.setApi( new ArrayList<>( list1 ) );
    }
    audio.setMaxseq( audioPlacement.getMaxseq() );
    audio.setFeed( audioPlacement.getFeed() );
    audio.setNvol( audioPlacement.getNvol() );
    Map<String, Object> map = audioPlacement.getExt();
    if ( map != null ) {
      audio.setExt( new HashMap<>( map ) );
      audio.setStitched((Integer) map.get("stitched"));
      audio.getExt().remove("stitched");
    }
    audio.setCompanionad(companionListToBannerList(audioPlacement.getComp(), config));
    audioPlacementToAudioAfterMapping( audioPlacement, audio );
  }

  private void audioPlacementToAudioAfterMapping(AudioPlacement audioPlacement, Audio
    audio) {
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

  protected List<Banner> companionListToBannerList(List<Companion> list, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<Banner> list1 = new ArrayList<>( list.size() );
    for ( Companion companion : list ) {
      list1.add( companionBannerConverter.map( companion, config ) );
    }

    return list1;
  }
}
