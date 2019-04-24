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
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        displayFormat.setExpdir(CollectionUtils.copyCollection(expdir, config));
      }
    }
    displayPlacement.setMime(CollectionUtils.copyCollection(banner.getMimes(), config));
    displayPlacement.setPos(banner.getPos());
    displayPlacement.setTopframe(banner.getTopframe());
    displayPlacement.setApi(CollectionUtils.copyCollection(banner.getApi(), config));
    displayPlacement.setW(banner.getW());
    displayPlacement.setH(banner.getH());
    Map<String, Object> bannerExt = banner.getExt();
    try {
      if (nonNull(bannerExt)) {
        if (isNull(displayPlacement.getExt())) {
          displayPlacement.setExt(new HashMap<>());
        }
        displayPlacement.getExt().putAll(bannerExt);
        if (bannerExt.containsKey(CommonConstants.UNIT)) {
          displayPlacement.setUnit((Integer) bannerExt.get(CommonConstants.UNIT));
          displayPlacement.getExt().remove(CommonConstants.UNIT);
        }
        if (bannerExt.containsKey(CommonConstants.CTYPE)) {
          displayPlacement.setCtype(
              CollectionUtils.copyCollection((Collection<Integer>) bannerExt.get(CommonConstants
                .CTYPE), config));
          displayPlacement.getExt().remove(CommonConstants.CTYPE);
        }
        if (bannerExt.containsKey(CommonConstants.PTYPE)) {
          displayPlacement.setPtype((Integer) bannerExt.get(CommonConstants.PTYPE));
          displayPlacement.getExt().remove(CommonConstants.PTYPE);
        }
        if (bannerExt.containsKey(CommonConstants.CONTEXT)) {
          displayPlacement.setContext((Integer) bannerExt.get(CommonConstants.CONTEXT));
          displayPlacement.getExt().remove(CommonConstants.CONTEXT);
        }
        if (bannerExt.containsKey(CommonConstants.PRIV)) {
          displayPlacement.setPriv((Integer) bannerExt.get(CommonConstants.PRIV));
          displayPlacement.getExt().remove(CommonConstants.PRIV);
        }
        if (bannerExt.containsKey(CommonConstants.SEQ)) {
          displayPlacement.getExt().remove(CommonConstants.SEQ);
        }
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Banner", e);
    }
    if (!CollectionUtils.isEmpty(banner.getBtype())) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.BTYPE, new ArrayList<>(banner.getBtype()));
    }
    if (banner.getId() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.ID, banner.getId());
    }
    if (banner.getHmax() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.HMAX, banner.getHmax());
    }
    if (banner.getHmin() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.HMIN, banner.getHmin());
    }
    if (banner.getWmax() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.WMAX, banner.getWmax());
    }
    if (banner.getWmin() != null) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put(CommonConstants.WMIN, banner.getWmin());
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

    displayFormat.setExt(MapUtils.copyMap(format.getExt(), config));
    displayFormat.setW(format.getW());
    displayFormat.setH(format.getH());
    displayFormat.setWratio(format.getWratio());
    displayFormat.setHratio(format.getHratio());
    if (nonNull(format.getWmin())) {
      if (displayFormat.getExt() == null) displayFormat.setExt(new HashMap<>());
      displayFormat.getExt().put(CommonConstants.WMIN, format.getWmin());
    }

    return displayFormat;
  }
}
