/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/01/19. */
public class VideoToVideoPlacementConverter implements Converter<Video, VideoPlacement> {

  @Override
  public VideoPlacement map(Video video, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (video == null) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();
    enhance(video, videoPlacement, config, converterProvider);

    return videoPlacement;
  }

  @Override
  public void enhance(
      Video video, VideoPlacement videoPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(video) || isNull(videoPlacement)) {
      return;
    }
    Converter<Banner, Companion> bannerCompanionConverter =
        converterProvider.fetch(new Conversion<>(Banner.class, Companion.class));
    videoPlacement.setComptype(Utils.copyCollection(video.getCompaniontype(), config));
    videoPlacement.setComp(
        CollectionToCollectionConverter.convert(
            video.getCompanionad(), bannerCompanionConverter, config, converterProvider));
    videoPlacement.setLinear(video.getLinearity());
    videoPlacement.setMime(Utils.copyCollection(video.getMimes(), config));
    videoPlacement.setMinbitr(video.getMinbitrate());
    videoPlacement.setPtype(video.getPlacement());
    videoPlacement.setMaxdur(video.getMaxduration());
    videoPlacement.setMaxext(video.getMaxextended());
    videoPlacement.setDelay(video.getStartdelay());
    videoPlacement.setPlayend(video.getPlaybackend());
    videoPlacement.setMindur(video.getMinduration());
    videoPlacement.setCtype(Utils.copyCollection(video.getProtocols(), config));
    videoPlacement.setBoxing(video.getBoxingallowed());
    videoPlacement.setPlaymethod(
        CollectionUtils.firstElementFromCollection(video.getPlaybackmethod()));
    videoPlacement.setMaxbitr(video.getMaxbitrate());
    videoPlacement.setPos(video.getPos());
    videoPlacement.setSkip(video.getSkip());
    videoPlacement.setSkipmin(video.getSkipmin());
    videoPlacement.setSkipafter(video.getSkipafter());
    videoPlacement.setApi(Utils.copyCollection(video.getApi(), config));
    videoPlacement.setW(video.getW());
    videoPlacement.setH(video.getH());
    videoPlacement.setDelivery(Utils.copyCollection(video.getDelivery(), config));
    videoPlacement.setExt(Utils.copyMap(video.getExt(), config));
    videoToVideoPlacementAfterMapping(video, config, videoPlacement);
  }

  private void videoToVideoPlacementAfterMapping(
      Video video, Config config, VideoPlacement videoPlacement) throws OpenRtbConverterException {
    try {
      if (nonNull(video) && nonNull(video.getExt()) && nonNull(videoPlacement)) {
        videoPlacement.setExt((Utils.copyMap(video.getExt(), config)));
        if (video.getExt().containsKey("unit")) {
          videoPlacement.setUnit((Integer) video.getExt().get("unit"));
          videoPlacement.getExt().remove("unit");
        }
        if (video.getExt().containsKey("maxseq")) {
          videoPlacement.setMaxseq((Integer) video.getExt().get("maxseq"));
          videoPlacement.getExt().remove("maxseq");
        }
        videoPlacement.getExt().remove("qty");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Video", e);
    }
  }
}
