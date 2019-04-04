package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class VideoToVideoPlacementConverter extends net.media.converters
  .request25toRequest30.VideoToVideoPlacementConverter {

  public void enhance(Video video, VideoPlacement videoPlacement, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (video == null) {
      return;
    }
    if (nonNull(video.getExt())) {
      if (video.getExt().containsKey("placement")) {
        video.setPlacement((Integer) video.getExt().get("placement"));
        video.getExt().remove("placement");
      }
      if (video.getExt().containsKey("playbackend")) {
        video.setPlaybackend((Integer) video.getExt().get("playbackend"));
        video.getExt().remove("playbackend");
      }
    }
    super.enhance(video, videoPlacement, config, converterProvider);
  }
}