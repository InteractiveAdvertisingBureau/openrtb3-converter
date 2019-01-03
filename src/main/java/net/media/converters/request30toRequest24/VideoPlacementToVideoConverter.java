package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.VideoPlacement;

public class VideoPlacementToVideoConverter implements Converter<VideoPlacement, Video> {
  @Override
  public Video map(VideoPlacement source, Config config) {
    return null;
  }

  @Override
  public void inhance(VideoPlacement source, Video target, Config config) {

  }
}
