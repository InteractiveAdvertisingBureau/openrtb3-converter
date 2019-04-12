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

package net.media.converters.response24toResponse30;

import net.media.config.Config;
import net.media.converters.response25toresponse30.Bid25ToBid30Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class Bid24ToBid30Converter extends Bid25ToBid30Converter {

  public void enhance(
      Bid source, net.media.openrtb3.Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("burl")) {
        source.setBurl((String) source.getExt().get("burl"));
        source.getExt().remove("burl");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("lurl")) {
        source.setLurl((String) source.getExt().get("lurl"));
        source.getExt().remove("lurl");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("tactic")) {
        source.setTactic((String) source.getExt().get("tactic"));
        source.getExt().remove("tactic");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("language")) {
        source.setLanguage((String) source.getExt().get("language"));
        source.getExt().remove("language");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("wratio")) {
        source.setWratio((Integer) source.getExt().get("wratio"));
        source.getExt().remove("wratio");
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("hratio")) {
        source.setHratio((Integer) source.getExt().get("hratio"));
        source.getExt().remove("hratio");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
