package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Audio;
import net.media.openrtb3.AudioPlacement;

public class AudioPlacementToAudioConverter implements Converter<AudioPlacement, Audio> {
  @Override
  public Audio map(AudioPlacement source, Config config) {
    return null;
  }

  @Override
  public void inhance(AudioPlacement source, Audio target, Config config) {

  }
}
