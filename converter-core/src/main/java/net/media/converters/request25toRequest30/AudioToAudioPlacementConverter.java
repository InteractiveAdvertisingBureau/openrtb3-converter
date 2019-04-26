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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Audio;
import net.media.openrtb25.request.Banner;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Companion;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.*;

/** Created by rajat.go on 03/01/19. */
public class AudioToAudioPlacementConverter implements Converter<Audio, AudioPlacement> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.SKIP);
    extraFieldsInExt.add(CommonConstants.SKIPMIN);
    extraFieldsInExt.add(CommonConstants.SKIPAFTER);
    extraFieldsInExt.add(CommonConstants.PLAYMETHOD);
    extraFieldsInExt.add(CommonConstants.PLAYEND);
    extraFieldsInExt.add(CommonConstants.QTY);
  }

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
    audioPlacement.setComptype(CollectionUtils.copyCollection(audio.getCompaniontype(), config));
    if(nonNull(audio.getExt())) {
      audioPlacement.setExt(new HashMap<>(audio.getExt()));
    }
    putToExt(audio::getStitched, audioPlacement.getExt(), CommonConstants.STITCHED, audioPlacement::setExt);
    audioPlacement.setComp(
        bannerListToCompanionList(audio.getCompanionad(), config, converterProvider));
    audioPlacement.setMaxdur(audio.getMaxduration());
    audioPlacement.setMaxext(audio.getMaxextended());
    audioPlacement.setDelay(audio.getStartdelay());
    audioPlacement.setMindur(audio.getMinduration());
    audioPlacement.setCtype(CollectionUtils.copyCollection(audio.getProtocols(), config));
    audioPlacement.setMime(CollectionUtils.copyCollection(audio.getMimes(), config));
    audioPlacement.setMinbitr(audio.getMinduration());
    audioPlacement.setMaxbitr(audio.getMaxduration());
    audioPlacement.setFeed(audio.getFeed());
    audioPlacement.setNvol(audio.getNvol());
    audioPlacement.setApi(CollectionUtils.copyCollection(audio.getApi(), config));
    audioPlacement.setDelivery(CollectionUtils.copyCollection(audio.getDelivery(), config));
    audioPlacement.setMaxseq(audio.getMaxseq());

    audioToAudioPlacementAfterMapping(audio, audioPlacement);
    removeFromExt(audioPlacement.getExt(), extraFieldsInExt);
  }

  private Collection<Companion> bannerListToCompanionList(
      Collection<Banner> list, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (list == null) {
      return null;
    }
    Converter<Banner, net.media.openrtb3.Companion> bannerCompanionConverter =
        converterProvider.fetch(new Conversion<>(Banner.class, Companion.class));
    Collection<Companion> list1 = new ArrayList<>(list.size());
    for (Banner banner : list) {
      list1.add(bannerCompanionConverter.map(banner, config, converterProvider));
    }

    return list1;
  }

  private void audioToAudioPlacementAfterMapping(Audio audio, AudioPlacement audioPlacement)
      throws OpenRtbConverterException {
    fetchFromExt(audioPlacement::setSkip, audio.getExt(), CommonConstants.SKIP, "Error in setting skip from Audio.ext");
    fetchFromExt(audioPlacement::setSkipmin, audio.getExt(), CommonConstants.SKIPMIN, "Error in setting skipmin from Audio.ext");
    fetchFromExt(audioPlacement::setSkipafter, audio.getExt(), CommonConstants.SKIPAFTER, "Error in setting skipafter from Audio.ext");
    fetchFromExt(audioPlacement::setPlaymethod, audio.getExt(), CommonConstants.PLAYMETHOD, "Error in setting playmethod from Audio.ext");
    fetchFromExt(audioPlacement::setPlayend, audio.getExt(), CommonConstants.PLAYEND, "Error in setting playend from Audio.ext");
  }
}
