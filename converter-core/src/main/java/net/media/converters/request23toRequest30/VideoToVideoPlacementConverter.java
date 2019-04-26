/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.converters.request23toRequest30;

import static java.util.Objects.nonNull;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 03/04/19.
 */
public class VideoToVideoPlacementConverter
    extends net.media.converters.request25toRequest30.VideoToVideoPlacementConverter {

  public void enhance(
      Video video, VideoPlacement videoPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (video == null || videoPlacement == null) {
      return;
    }
    if (nonNull(video.getExt())) {
      if (video.getExt().containsKey(CommonConstants.PLACEMENT)) {
        try {
          video.setPlacement((Integer) video.getExt().get(CommonConstants.PLACEMENT));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting placement from video.ext.placement", e);
        }
        video.getExt().remove(CommonConstants.PLACEMENT);
      }
      if (video.getExt().containsKey(CommonConstants.PLAYBACKEND)) {
        try {
          video.setPlaybackend((Integer) video.getExt().get(CommonConstants.PLAYBACKEND));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting playbackend from imp.ext" + ".playbackend", e);
        }
        video.getExt().remove(CommonConstants.PLAYBACKEND);
      }
      if (video.getExt().containsKey(CommonConstants.SKIP)) {
        try {
          video.setSkip((Integer) video.getExt().get(CommonConstants.SKIP));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting skip from video.ext.skip", e);
        }
        video.getExt().remove(CommonConstants.SKIP);
      }
      if (video.getExt().containsKey(CommonConstants.SKIPMIN)) {
        try {
          video.setSkipmin((Integer) video.getExt().get(CommonConstants.SKIPMIN));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting skipmin from video.ext.skipmin", e);
        }
        video.getExt().remove(CommonConstants.SKIPMIN);
      }
      if (video.getExt().containsKey(CommonConstants.SKIPAFTER)) {
        try {
          video.setSkipafter((Integer) video.getExt().get(CommonConstants.SKIPAFTER));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting skipafter from video.ext.skipafter", e);
        }
        video.getExt().remove(CommonConstants.SKIPAFTER);
      }
    }
    super.enhance(video, videoPlacement, config, converterProvider);
  }
}
