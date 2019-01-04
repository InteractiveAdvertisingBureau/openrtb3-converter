package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VideoPlacementToVideoConverter implements Converter<VideoPlacement, Video> {

  private Converter<Companion, Banner> companionBannerConverter;

  @Override
  public Video map(VideoPlacement source, Config config) {
    return null;
  }

  @Override
  public void inhance(VideoPlacement source, Video target, Config config) {

  }
}
