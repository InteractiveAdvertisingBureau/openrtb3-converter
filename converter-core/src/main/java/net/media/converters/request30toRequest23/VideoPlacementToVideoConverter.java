package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class VideoPlacementToVideoConverter extends net.media.converters.request30toRequest25.VideoPlacementToVideoConverter {

  public void enhance(VideoPlacement videoPlacement, Video video, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (videoPlacement == null || video == null) {
      return;
    }
    super.enhance(videoPlacement, video, config, converterProvider);
    if (nonNull(video.getPlacement())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("placement", video.getPlacement());
      video.setPlacement(null);
    }
    if (nonNull(video.getPlaybackend())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("playbackend", video.getPlaybackend());
      video.setPlaybackend(null);
    }
    if (nonNull(video.getSkip())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("skip", video.getSkip());
      video.setSkip(null);
    }
    if (nonNull(video.getSkipmin())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("skipmin", video.getSkipmin());
      video.setSkipmin(null);
    }
    if (nonNull(video.getSkipafter())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("skipafter", video.getSkipafter());
      video.setSkipafter(null);
    }
  }
}
