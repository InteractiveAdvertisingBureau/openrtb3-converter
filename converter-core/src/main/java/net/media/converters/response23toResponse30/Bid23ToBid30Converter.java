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

import net.media.config.Config;
import net.media.converters.response25toresponse30.Bid25ToBid30Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class Bid23ToBid30Converter extends Bid25ToBid30Converter {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  static {
    extraFieldsInExt.add("burl");
    extraFieldsInExt.add("lurl");
    extraFieldsInExt.add("tactic");
    extraFieldsInExt.add("language");
    extraFieldsInExt.add("wratio");
    extraFieldsInExt.add("hratio");
    extraFieldsInExt.add("adid");
    extraFieldsInExt.add("api");
    extraFieldsInExt.add("protocol");
    extraFieldsInExt.add("qagmediarating");
    extraFieldsInExt.add("exp");
  }
  public void enhance(
      Bid source, net.media.openrtb3.Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("burl")) {
        source.setBurl((String) source.getExt().get("burl"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("lurl")) {
        source.setLurl((String) source.getExt().get("lurl"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("tactic")) {
        source.setTactic((String) source.getExt().get("tactic"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("language")) {
        source.setLanguage((String) source.getExt().get("language"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("wratio")) {
        source.setWratio((Integer) source.getExt().get("wratio"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("hratio")) {
        source.setHratio((Integer) source.getExt().get("hratio"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("adid")) {
        source.setAdid((String) source.getExt().get("adid"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("api")) {
        source.setApi((Integer) source.getExt().get("api"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("protocol")) {
        source.setProtocol((Integer) source.getExt().get("protocol"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("qagmediarating")) {
        source.setQagmediarating((Integer) source.getExt().get("qagmediarating"));
      }
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("exp")) {
        source.setExp((Integer) source.getExt().get("exp"));
      }
    }
    super.enhance(source, target, config, converterProvider);
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
