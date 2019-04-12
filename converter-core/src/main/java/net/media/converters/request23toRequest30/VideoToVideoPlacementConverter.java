/*
 * Copyright © 2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class VideoToVideoPlacementConverter
    extends net.media.converters.request25toRequest30.VideoToVideoPlacementConverter {

  public void enhance(
      Video video, VideoPlacement videoPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (video == null || videoPlacement == null) {
      return;
    }
    if (nonNull(video.getExt())) {
      if (video.getExt().containsKey("placement")) {
        try {
          video.setPlacement((Integer) video.getExt().get("placement"));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting placement from video.ext.placement", e);
        }
        video.getExt().remove("placement");
      }
      if (video.getExt().containsKey("playbackend")) {
        try {
          video.setPlaybackend((Integer) video.getExt().get("playbackend"));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting playbackend from imp.ext" + ".playbackend", e);
        }
        video.getExt().remove("playbackend");
      }
      if (video.getExt().containsKey("skip")) {
        try {
          video.setSkip((Integer) video.getExt().get("skip"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting skip from video.ext.skip", e);
        }
        video.getExt().remove("skip");
      }
      if (video.getExt().containsKey("skipmin")) {
        try {
          video.setSkipmin((Integer) video.getExt().get("skipmin"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting skipmin from video.ext.skipmin", e);
        }
        video.getExt().remove("skipmin");
      }
      if (video.getExt().containsKey("skipafter")) {
        try {
          video.setSkipafter((Integer) video.getExt().get("skipafter"));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting skipafter from video.ext" + ".skipafter", e);
        }
        video.getExt().remove("skipafter");
      }
    }
    super.enhance(video, videoPlacement, config, converterProvider);
  }
}
