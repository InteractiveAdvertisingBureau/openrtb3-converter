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
import net.media.utils.Provider;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.*;

public class DisplayPlacementToBannerConverter implements Converter<DisplayPlacement, Banner> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  private static List<String> extraFieldsInFormatExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.BTYPE);
    extraFieldsInExt.add(CommonConstants.ID);
    extraFieldsInExt.add(CommonConstants.HMAX);
    extraFieldsInExt.add(CommonConstants.WMAX);
    extraFieldsInExt.add(CommonConstants.HMIN);
    extraFieldsInExt.add(CommonConstants.WMIN);
    extraFieldsInFormatExt.add(CommonConstants.WMIN);
  }

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
          Collection<Integer> formatExpdir =
            CollectionUtils.copyCollection(displayFormat.getExpdir(), config);
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
      banner.setExt(new HashMap<>(map));
      fetchCollectionFromExt(
        banner::setBtype,
        map,
        CommonConstants.BTYPE,
        "error while mapping btype from displayplacement.ext",
        config);
      fetchFromExt(
        banner::setId,
        map,
        CommonConstants.ID,
        "error while mapping id from displayplacement.ext");
      fetchFromExt(
        banner::setHmax,
        map,
        CommonConstants.HMAX,
        "error while mapping hmax from displayplacement.ext");
      fetchFromExt(
        banner::setWmax,
        map,
        CommonConstants.WMAX,
        "error while mapping wmax from displayplacement.ext");
      fetchFromExt(
        banner::setHmin,
        map,
        CommonConstants.HMIN,
        "error while mapping hmin from displayplacement.ext");
      fetchFromExt(
        banner::setWmin,
        map,
        CommonConstants.WMIN,
        "error while mapping wmin from displayplacement.ext");
    }
    putToExt(displayPlacement::getUnit, banner.getExt(), CommonConstants.UNIT, banner::setExt);
    putToExt(displayPlacement::getPtype, banner.getExt(), CommonConstants.PTYPE, banner::setExt);
    putToExt(
      displayPlacement::getContext, banner.getExt(), CommonConstants.CONTEXT, banner::setExt);
    putToExt(displayPlacement::getCtype, banner.getExt(), CommonConstants.CTYPE, banner::setExt);
    putToExt(displayPlacement::getPriv, banner.getExt(), CommonConstants.PRIV, banner::setExt);
    removeFromExt(banner.getExt(), extraFieldsInExt);
  }

  private Collection<Format> displayFormatListToFormatList(
      Collection<DisplayFormat> list, Config config) throws OpenRtbConverterException {
    if (list == null) {
      return null;
    }

    Collection<Format> list1 = new ArrayList<>(list.size());
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
      format.setExt(new HashMap<>(map));
      fetchFromExt(
        format::setWmin,
        map,
        CommonConstants.WMIN,
        "error while mapping wmin from displayformat.ext");
    }
    removeFromExt(format.getExt(), extraFieldsInFormatExt);
    return format;
  }
}
