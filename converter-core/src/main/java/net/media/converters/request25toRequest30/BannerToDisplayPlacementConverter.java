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
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CollectionUtils;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/01/19. */
public class BannerToDisplayPlacementConverter implements Converter<Banner, DisplayPlacement> {

  @Override
  public DisplayPlacement map(Banner banner, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(banner)) {
      return null;
    }
    DisplayPlacement displayPlacement = new DisplayPlacement();
    enhance(banner, displayPlacement, config, converterProvider);
    return displayPlacement;
  }

  @Override
  public void enhance(
      Banner banner, DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(banner) || isNull(displayPlacement)) {
      return;
    }
    displayPlacement.setDisplayfmt(formatListToDisplayFormatList(banner.getFormat(), config));
    if (nonNull(displayPlacement.getDisplayfmt())) {
      for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
        Collection<Integer> expdir = impBannerExpdir(banner);
        displayFormat.setExpdir(Utils.copyCollection(expdir, config));
      }
    }
    displayPlacement.setMime(Utils.copyCollection(banner.getMimes(), config));
    displayPlacement.setPos(banner.getPos());
    displayPlacement.setTopframe(banner.getTopframe());
    displayPlacement.setApi(Utils.copyCollection(banner.getApi(), config));
    displayPlacement.setW(banner.getW());
    displayPlacement.setH(banner.getH());
    Map<String, Object> bannerExt = banner.getExt();
    try {
      if (nonNull(bannerExt)) {
        if (isNull(displayPlacement.getExt())) {
          displayPlacement.setExt(new HashMap<>());
        }
        displayPlacement.getExt().putAll(bannerExt);
        if (bannerExt.containsKey("unit")) {
          displayPlacement.setUnit((Integer) bannerExt.get("unit"));
          displayPlacement.getExt().remove("unit");
        }
        if (bannerExt.containsKey("ctype")) {
          displayPlacement.setCtype(
              Utils.copyCollection((List<Integer>) bannerExt.get("ctype"), config));
          displayPlacement.getExt().remove("ctype");
        }
        if (bannerExt.containsKey("ptype")) {
          displayPlacement.setPtype((Integer) bannerExt.get("ptype"));
          displayPlacement.getExt().remove("ptype");
        }
        if (bannerExt.containsKey("context")) {
          displayPlacement.setContext((Integer) bannerExt.get("context"));
          displayPlacement.getExt().remove("context");
        }
        if (bannerExt.containsKey("priv")) {
          displayPlacement.setPriv((Integer) bannerExt.get("priv"));
          displayPlacement.getExt().remove("priv");
        }
        if (bannerExt.containsKey("seq")) {
          displayPlacement.getExt().remove("seq");
        }
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Banner", e);
    }
    if (!CollectionUtils.isEmpty(banner.getBtype())) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("btype", new ArrayList<>(banner.getBtype()));
    }
    if (banner.getId() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("id", banner.getId());
    }
    if (banner.getHmax() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("hmax", banner.getHmax());
    }
    if (banner.getHmin() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("hmin", banner.getHmin());
    }
    if (banner.getWmax() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("wmax", banner.getWmax());
    }
    if (banner.getWmin() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("wmin", banner.getWmin());
    }
  }

  private Collection<Integer> impBannerExpdir(Banner banner) {
    Collection<Integer> expdir = banner.getExpdir();
    if (expdir == null) {
      return null;
    }
    return expdir;
  }

  private Collection<DisplayFormat> formatListToDisplayFormatList(
      Collection<Format> list, Config config) {
    if (list == null) {
      return null;
    }

    List<DisplayFormat> list1 = new ArrayList<DisplayFormat>(list.size());
    for (Format format : list) {
      list1.add(map(format, config));
    }

    return list1;
  }

  private DisplayFormat map(Format format, Config config) {
    if (format == null) {
      return null;
    }

    DisplayFormat displayFormat = new DisplayFormat();

    displayFormat.setExt(Utils.copyMap(format.getExt(), config));
    displayFormat.setW(format.getW());
    displayFormat.setH(format.getH());
    displayFormat.setWratio(format.getWratio());
    displayFormat.setHratio(format.getHratio());
    if (nonNull(format.getWmin())) {
      if (displayFormat.getExt() == null) displayFormat.setExt(new HashMap<>());
      displayFormat.getExt().put("wmin", format.getWmin());
    }

    return displayFormat;
  }
}
