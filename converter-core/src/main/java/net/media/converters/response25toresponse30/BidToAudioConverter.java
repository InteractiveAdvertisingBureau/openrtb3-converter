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

package net.media.converters.response25toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audio;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;

/** @author shiva.b */
public class BidToAudioConverter implements Converter<Bid, Audio> {

  @Override
  public Audio map(Bid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Audio audio = new Audio();
    enhance(source, audio, config, converterProvider);
    return audio;
  }

  @Override
  public void enhance(Bid source, Audio target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {

    if (isNull(source) || isNull(target)) {
      return;
    }

    target.setAdm(source.getAdm());

    if (nonNull(source.getApi())) {
      target.setApi(new ArrayList<>(Collections.singletonList(source.getApi())));
    }
    target.setCurl(source.getNurl());
    fetchFromExt(target::setCtype, source.getExt(), CommonConstants.CTYPE, "Error while mapping ctype from bid.ext");
    fetchFromExt(target::setDur, source.getExt(), CommonConstants.DUR, "Error while mapping dur from bid.ext");
    fetchFromExt(target::setMime, source.getExt(), CommonConstants.MIME, "Error while mapping mime from bid.ext");
  }
}
