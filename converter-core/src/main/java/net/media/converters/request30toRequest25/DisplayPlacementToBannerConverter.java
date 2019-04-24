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

package net.media.converters.request30toRequest25;

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

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DisplayPlacementToBannerConverter implements Converter<DisplayPlacement, Banner> {

  @Override
  public Banner map(DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (displayPlacement == null) {
      return null;
    }
    if (nonNull(displayPlacement.getNativefmt())) {
      return null;
    }
    Banner banner = new Banner();
    enhance(displayPlacement, banner, config, converterProvider);
    return banner;
  }

  @Override
  public void enhance(
      DisplayPlacement displayPlacement, Banner banner, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(displayPlacement) || isNull(banner)) {
      return;
    }
    banner.setMimes(CollectionUtils.copyCollection(displayPlacement.getMime(), config));
    banner.setFormat(displayFormatListToFormatList(displayPlacement.getDisplayfmt(), config));
    if (nonNull(displayPlacement.getDisplayfmt())) {
      for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
        if (!CollectionUtils.isEmpty(displayFormat.getExpdir())) {
          Collection<Integer> formatExpdir = CollectionUtils.copyCollection(displayFormat.getExpdir(),
            config);
          if (isNull(banner.getExpdir())) {
            banner.setExpdir(formatExpdir);
          } else {
            try {
              banner.setExpdir(
                  (Collection<Integer>)
                      org.apache.commons.collections.CollectionUtils.union(
                          banner.getExpdir(), formatExpdir));
            } catch (ClassCastException e) {
              throw new OpenRtbConverterException(
                  "error while typecasting expdir for DisplayPlacement", e);
            }
          }
        }
      }
    }
    banner.setW(displayPlacement.getW());
    banner.setH(displayPlacement.getH());
    banner.setPos(displayPlacement.getPos());
    banner.setTopframe(displayPlacement.getTopframe());
    banner.setApi(CollectionUtils.copyCollection(displayPlacement.getApi(), config));
    Map<String, Object> map = displayPlacement.getExt();
    if (map != null) {
      banner.setExt(MapUtils.copyMap(map, config));
      if (map.containsKey(CommonConstants.BTYPE)) {
        try {
          banner.setBtype(CollectionUtils.copyCollection((Collection<Integer>) map.get(CommonConstants.BTYPE), config));
          banner.getExt().remove(CommonConstants.BTYPE);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey(CommonConstants.ID)) {
        try {
          banner.setId((String) map.get(CommonConstants.ID));
          banner.getExt().remove(CommonConstants.ID);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey(CommonConstants.HMAX)) {
        try {
          banner.setHmax((Integer) map.get(CommonConstants.HMAX));
          banner.getExt().remove(CommonConstants.HMAX);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey(CommonConstants.WMAX)) {
        try {
          banner.setWmax((Integer) map.get(CommonConstants.WMAX));
          banner.getExt().remove(CommonConstants.WMAX);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey(CommonConstants.HMIN)) {
        try {
          banner.setHmin((Integer) map.get(CommonConstants.HMIN));
          banner.getExt().remove(CommonConstants.HMIN);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey(CommonConstants.WMIN)) {
        try {
          banner.setWmin((Integer) map.get(CommonConstants.WMIN));
          banner.getExt().remove(CommonConstants.WMIN);
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException(
              "error while typecasting ext for DisplayPlacement", e);
        }
      }
    }
    if (nonNull(displayPlacement.getUnit())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put(CommonConstants.UNIT, displayPlacement.getUnit());
    }
    if (nonNull(displayPlacement.getPtype())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put(CommonConstants.PTYPE, displayPlacement.getPtype());
    }
    if (nonNull(displayPlacement.getContext())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put(CommonConstants.CONTEXT, displayPlacement.getContext());
    }
    if (nonNull(displayPlacement.getCtype())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put(CommonConstants.CTYPE, displayPlacement.getCtype());
    }
    if (nonNull(displayPlacement.getPriv())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put(CommonConstants.PRIV, displayPlacement.getPriv());
    }
  }

  private Collection<Format> displayFormatListToFormatList(
      Collection<DisplayFormat> list, Config config) throws OpenRtbConverterException {
    if (list == null) {
      return null;
    }

    Collection<Format> list1 = new ArrayList<Format>(list.size());
    for (DisplayFormat displayFormat : list) {
      list1.add(displayFormatToFormat(displayFormat, config));
    }

    return list1;
  }

  private Format displayFormatToFormat(DisplayFormat displayFormat, Config config)
      throws OpenRtbConverterException {
    if (displayFormat == null) {
      return null;
    }

    Format format = new Format();

    format.setW(displayFormat.getW());
    format.setH(displayFormat.getH());
    format.setWratio(displayFormat.getWratio());
    format.setHratio(displayFormat.getHratio());
    Map<String, Object> map = displayFormat.getExt();
    if (map != null) {
      format.setExt(MapUtils.copyMap(map, config));
      try {
        if (map.containsKey(CommonConstants.WMIN)) {
          format.setWmin((Integer) map.get(CommonConstants.WMIN));
          format.getExt().remove(CommonConstants.WMIN);
        }
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
      }
    }

    return format;
  }
}
