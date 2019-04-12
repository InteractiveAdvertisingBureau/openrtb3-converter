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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/01/19. */
public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {

  @Override
  public AudioPlacement map(Audio audio, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (audio == null) {
      return null;
    }

    AudioPlacement audioPlacement = new AudioPlacement();
    enhance(audio, audioPlacement, config, converterProvider);

    return audioPlacement;
  }

  @Override
  public void enhance(
      Audio audio, AudioPlacement audioPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (audio == null || audioPlacement == null) {
      return;
    }
    audioPlacement.setComptype(Utils.copyCollection(audio.getCompaniontype(), config));
    audioPlacement.setExt(Utils.copyMap(audio.getExt(), config));
    if (nonNull(audio.getStitched())) {
      if (isNull(audioPlacement.getExt())) {
        audioPlacement.setExt(new HashMap<>());
      }
      audioPlacement.getExt().put("stitched", audio.getStitched());
    }
    audioPlacement.setComp(
        bannerListToCompanionList(audio.getCompanionad(), config, converterProvider));
    audioPlacement.setMaxdur(audio.getMaxduration());
    audioPlacement.setMaxext(audio.getMaxextended());
    audioPlacement.setDelay(audio.getStartdelay());
    audioPlacement.setMindur(audio.getMinduration());
    audioPlacement.setCtype(Utils.copyCollection(audio.getProtocols(), config));
    audioPlacement.setMime(Utils.copyCollection(audio.getMimes(), config));
    audioPlacement.setMinbitr(audio.getMinduration());
    audioPlacement.setMaxbitr(audio.getMaxduration());
    audioPlacement.setFeed(audio.getFeed());
    audioPlacement.setNvol(audio.getNvol());
    audioPlacement.setApi(Utils.copyCollection(audio.getApi(), config));
    audioPlacement.setDelivery(Utils.copyCollection(audio.getDelivery(), config));
    audioPlacement.setMaxseq(audio.getMaxseq());

    audioToAudioPlacementAfterMapping(audio, audioPlacement);
  }

  private Collection<Companion> bannerListToCompanionList(
      Collection<Banner> list, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (list == null) {
      return null;
    }
    Converter<Banner, net.media.openrtb3.Companion> bannerCompanionConverter =
        converterProvider.fetch(new Conversion<>(Banner.class, Companion.class));
    Collection<Companion> list1 = new ArrayList<Companion>(list.size());
    for (Banner banner : list) {
      list1.add(bannerCompanionConverter.map(banner, config, converterProvider));
    }

    return list1;
  }

  private void audioToAudioPlacementAfterMapping(Audio audio, AudioPlacement audioPlacement)
      throws OpenRtbConverterException {
    try {
      if (nonNull(audio) && nonNull(audio.getExt()) && nonNull(audioPlacement)) {
        if (audio.getExt().containsKey("skip")) {
          audioPlacement.setSkip((Integer) audio.getExt().get("skip"));
          audioPlacement.getExt().remove("skip");
        }
        if (audio.getExt().containsKey("skipmin")) {
          audioPlacement.setSkipmin((Integer) audio.getExt().get("skipmin"));
          audioPlacement.getExt().remove("skipmin");
        }
        if (audio.getExt().containsKey("skipafter")) {
          audioPlacement.setSkipafter((Integer) audio.getExt().get("skipafter"));
          audioPlacement.getExt().remove("skipafter");
        }
        if (audio.getExt().containsKey("playmethod")) {
          audioPlacement.setPlaymethod((Integer) audio.getExt().get("playmethod"));
          audioPlacement.getExt().remove("playmethod");
        }
        if (audio.getExt().containsKey("playend")) {
          audioPlacement.setPlayend((Integer) audio.getExt().get("playend"));
          audioPlacement.getExt().remove("playend");
        }
        if (audio.getExt().containsKey("delay")) {
          audioPlacement.setSkip((Integer) audio.getExt().get("delay"));
          audioPlacement.getExt().remove("delay");
        }
        audioPlacement.getExt().remove("qty");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Audio", e);
    }
  }
}
