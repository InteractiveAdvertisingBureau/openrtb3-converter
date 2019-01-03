package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;

/**
 * Created by rajat.go on 03/01/19.
 */
public class VideoToVideoPlacementConverter implements Converter<Video, VideoPlacement> {

  private Converter<Banner, Companion> bannerCompanionConverter;

  public VideoToVideoPlacementConverter(Converter<Banner, Companion> bannerCompanionConverter) {
    this.bannerCompanionConverter = bannerCompanionConverter;
  }

  @Override
  public VideoPlacement map(Video source, Config config) {
    return null;
  }

  @Override
  public void inhance(Video source, VideoPlacement target, Config config) {

  }
}
