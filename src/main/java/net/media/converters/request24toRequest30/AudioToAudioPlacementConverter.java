package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.openrtb3.DisplayPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {

  private Converter<Banner, Companion> bannerDisplayPlacementConverter;

  public AudioToAudioPlacementConverter(Converter<Banner, Companion>
                                          bannerDisplayPlacementConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
  }

  @Override
  public AudioPlacement map(Audio source, Config config) {
    return null;
  }

  @Override
  public void inhance(Audio source, AudioPlacement target, Config config) {

  }
}
