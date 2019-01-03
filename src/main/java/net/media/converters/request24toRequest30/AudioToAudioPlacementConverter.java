package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb3.AudioPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {
  @Override
  public AudioPlacement map(Audio source, Config config) {
    return null;
  }

  @Override
  public void inhance(Audio source, AudioPlacement target, Config config) {

  }
}
