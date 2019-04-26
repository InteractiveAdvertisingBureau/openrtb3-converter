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

package net.media.converters.response23toResponse30;

import static java.util.Objects.nonNull;

import net.media.config.Config;
import net.media.converters.response25toresponse30.Bid25ToBid30Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 03/04/19.
 */
public class Bid23ToBid30Converter extends Bid25ToBid30Converter {

  public void enhance(
      Bid source, net.media.openrtb3.Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.BURL)) {
        source.setBurl((String) source.getExt().get(CommonConstants.BURL));
        source.getExt().remove(CommonConstants.BURL);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.LURL)) {
        source.setLurl((String) source.getExt().get(CommonConstants.LURL));
        source.getExt().remove(CommonConstants.LURL);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.TACTIC)) {
        source.setTactic((String) source.getExt().get(CommonConstants.TACTIC));
        source.getExt().remove(CommonConstants.TACTIC);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.LANGUAGE)) {
        source.setLanguage((String) source.getExt().get(CommonConstants.LANGUAGE));
        source.getExt().remove(CommonConstants.LANGUAGE);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.WRATIO)) {
        source.setWratio((Integer) source.getExt().get(CommonConstants.WRATIO));
        source.getExt().remove(CommonConstants.WRATIO);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.HRATIO)) {
        source.setHratio((Integer) source.getExt().get(CommonConstants.HRATIO));
        source.getExt().remove(CommonConstants.HRATIO);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.ADID)) {
        source.setAdid((String) source.getExt().get(CommonConstants.ADID));
        source.getExt().remove(CommonConstants.ADID);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.API)) {
        source.setApi((Integer) source.getExt().get(CommonConstants.API));
        source.getExt().remove(CommonConstants.API);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.PROTOCOL)) {
        source.setProtocol((Integer) source.getExt().get(CommonConstants.PROTOCOL));
        source.getExt().remove(CommonConstants.PROTOCOL);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.QAGMEDIARATING)) {
        source.setQagmediarating((Integer) source.getExt().get(CommonConstants.QAGMEDIARATING));
        source.getExt().remove(CommonConstants.QAGMEDIARATING);
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.EXP)) {
        source.setExp((Integer) source.getExt().get(CommonConstants.EXP));
        source.getExt().remove(CommonConstants.EXP);
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
