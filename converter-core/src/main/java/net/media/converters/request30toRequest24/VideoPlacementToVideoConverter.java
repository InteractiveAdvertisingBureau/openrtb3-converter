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

package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class VideoPlacementToVideoConverter
    extends net.media.converters.request30toRequest25.VideoPlacementToVideoConverter {

  public void enhance(
      VideoPlacement videoPlacement, Video video, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (videoPlacement == null || video == null) {
      return;
    }
    super.enhance(videoPlacement, video, config, converterProvider);
    if (nonNull(video.getPlacement())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put(CommonConstants.PLACEMENT, video.getPlacement());
      video.setPlacement(null);
    }
    if (nonNull(video.getPlaybackend())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put(CommonConstants.PLAYBACKEND, video.getPlaybackend());
      video.setPlaybackend(null);
    }
  }
}
