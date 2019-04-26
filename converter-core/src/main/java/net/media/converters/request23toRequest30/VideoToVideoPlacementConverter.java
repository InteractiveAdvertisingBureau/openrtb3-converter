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

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/04/19. */
public class VideoToVideoPlacementConverter
    extends net.media.converters.request25toRequest30.VideoToVideoPlacementConverter {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.PLACEMENT);
    extraFieldsInExt.add(CommonConstants.PLAYBACKEND);
    extraFieldsInExt.add(CommonConstants.SKIP);
    extraFieldsInExt.add(CommonConstants.SKIPMIN);
    extraFieldsInExt.add(CommonConstants.SKIPAFTER);
  }

  public void enhance(
      Video video, VideoPlacement videoPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (video == null || videoPlacement == null) {
      return;
    }
    fetchFromExt(
      video::setPlacement,
      video.getExt(),
      CommonConstants.PLACEMENT,
      "Error in setting placement from video.ext.placement");
    fetchFromExt(
      video::setPlaybackend,
      video.getExt(),
      CommonConstants.PLAYBACKEND,
      "Error in setting playbackend from video.ext.playbackend");
    fetchFromExt(
      video::setSkip,
      video.getExt(),
      CommonConstants.SKIP,
      "Error in setting skip from video.ext.skip");
    fetchFromExt(
      video::setSkipmin,
      video.getExt(),
      CommonConstants.SKIPMIN,
      "Error in setting skipmin from video.ext.skipmin");
    fetchFromExt(
      video::setSkipafter,
      video.getExt(),
      CommonConstants.SKIPAFTER,
      "Error in setting skipafter from video.ext.skipafter");
    super.enhance(video, videoPlacement, config, converterProvider);
    removeFromExt(videoPlacement.getExt(), extraFieldsInExt);
  }
}
