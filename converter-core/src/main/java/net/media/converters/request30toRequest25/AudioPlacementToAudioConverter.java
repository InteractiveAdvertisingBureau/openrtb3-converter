package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AudioPlacementToAudioConverter implements Converter<AudioPlacement, Audio> {

  @Override
  public Audio map(AudioPlacement audioPlacement, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( audioPlacement == null ) {
      return null;
    }

    Audio audio = new Audio();
    enhance(audioPlacement, audio, config, converterProvider);

    return audio;
  }

  @Override
  public void enhance(AudioPlacement audioPlacement, Audio audio, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(audioPlacement) || isNull(audio)) {
      return;
    }
    audio.setDelivery(Utils.copyCollection(audioPlacement.getDelivery(), config));
    audio.setApi(Utils.copyCollection(audioPlacement.getApi(), config));
    audio.setMaxseq( audioPlacement.getMaxseq() );
    audio.setFeed( audioPlacement.getFeed() );
    audio.setNvol( audioPlacement.getNvol() );
    Map<String, Object> map = audioPlacement.getExt();
    if ( map != null ) {
      audio.setExt(Utils.copyMap(map, config));
      try {
        audio.setStitched((Integer) map.get("stitched"));
        audio.getExt().remove("stitched");
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for Audio", e);
      }
    }
    audio.setCompanionad(companionListToBannerList(audioPlacement.getComp(), config, converterProvider));
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

  protected Collection<Banner> companionListToBannerList(Collection<Companion> list, Config
    config, Provider converterProvider) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    Collection<Banner> list1 = new ArrayList<>( list.size() );
    Converter<Companion, Banner> companionBannerConverter = converterProvider.fetch(new Conversion<>(Companion.class, Banner.class));
    for ( Companion companion : list ) {
      list1.add( companionBannerConverter.map( companion, config, converterProvider ) );
    }

    return list1;
  }
}
