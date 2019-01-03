package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.VideoPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class ImpToItemConverter implements Converter<Imp, Item> {

  private Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter;

  private Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter;

  private Converter<Video, VideoPlacement> videoVideoPlacementConverter;

  private Converter<Audio, AudioPlacement> audioAudioPlacementConverter;

  public ImpToItemConverter(Converter<Banner, DisplayPlacement> bannerDisplayPlacementConverter,
                            Converter<Native, DisplayPlacement> nativeDisplayPlacementConverter,
                            Converter<Video, VideoPlacement> videoVideoPlacementConverter,
                            Converter<Audio, AudioPlacement> audioAudioPlacementConverter) {
    this.bannerDisplayPlacementConverter = bannerDisplayPlacementConverter;
    this.nativeDisplayPlacementConverter = nativeDisplayPlacementConverter;
    this.videoVideoPlacementConverter = videoVideoPlacementConverter;
    this.audioAudioPlacementConverter = audioAudioPlacementConverter;
  }

  @Override
  public Item map(Imp source, Config config) {
    return null;
  }

  @Override
  public void inhance(Imp source, Item target, Config config) {

  }
}
