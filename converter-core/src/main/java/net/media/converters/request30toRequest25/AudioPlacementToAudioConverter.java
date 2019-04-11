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

package net.media.converters.request30toRequest25;

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
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AudioPlacementToAudioConverter implements Converter<AudioPlacement, Audio> {

  @Override
  public Audio map(AudioPlacement audioPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (audioPlacement == null) {
      return null;
    }

    Audio audio = new Audio();
    enhance(audioPlacement, audio, config, converterProvider);

    return audio;
  }

  @Override
  public void enhance(
      AudioPlacement audioPlacement, Audio audio, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(audioPlacement) || isNull(audio)) {
      return;
    }
    audio.setDelivery(Utils.copyCollection(audioPlacement.getDelivery(), config));
    audio.setApi(Utils.copyCollection(audioPlacement.getApi(), config));
    audio.setMaxseq(audioPlacement.getMaxseq());
    audio.setFeed(audioPlacement.getFeed());
    audio.setNvol(audioPlacement.getNvol());
    audio.setCompaniontype(audioPlacement.getComptype());
    audio.setMaxbitrate(audioPlacement.getMaxbitr());
    audio.setMaxduration(audioPlacement.getMaxdur());
    audio.setMaxextended(audioPlacement.getMaxext());
    audio.setMimes(audioPlacement.getMime());
    audio.setMinbitrate(audioPlacement.getMinbitr());
    audio.setMinduration(audioPlacement.getMindur());
    audio.setStartdelay(audioPlacement.getDelay());
    audio.setProtocols(audioPlacement.getCtype());
    Map<String, Object> map = audioPlacement.getExt();
    if (map != null) {
      audio.setExt(Utils.copyMap(map, config));
      try {
        audio.setStitched((Integer) map.get("stitched"));
        audio.getExt().remove("stitched");
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for Audio", e);
      }
    }
    audio.setCompanionad(
        companionListToBannerList(audioPlacement.getComp(), config, converterProvider));
    audioPlacementToAudioAfterMapping(audioPlacement, audio);
  }

  private void audioPlacementToAudioAfterMapping(AudioPlacement audioPlacement, Audio audio) {
    if (nonNull(audioPlacement) && nonNull(audioPlacement.getExt()) && nonNull(audio)) {
      if (isNull(audio.getExt())) {
        audio.setExt(new HashMap<>());
      }
      audio.getExt().put("skip", audioPlacement.getSkip());
      audio.getExt().put("skipmin", audioPlacement.getSkipmin());
      audio.getExt().put("skipafter", audioPlacement.getSkipafter());
      audio.getExt().put("playmethod", audioPlacement.getPlaymethod());
      audio.getExt().put("playend", audioPlacement.getPlayend());
    }
  }

  protected Collection<Banner> companionListToBannerList(
      Collection<Companion> list, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (list == null) {
      return null;
    }

    Collection<Banner> list1 = new ArrayList<>(list.size());
    Converter<Companion, Banner> companionBannerConverter =
        converterProvider.fetch(new Conversion<>(Companion.class, Banner.class));
    for (Companion companion : list) {
      list1.add(companionBannerConverter.map(companion, config, converterProvider));
    }

    return list1;
  }
}
