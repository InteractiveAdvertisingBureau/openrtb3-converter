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
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Restrictions;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

public class BidRequestToRestrictionsConverter implements Converter<BidRequest2_X, Restrictions> {

  @Override
  public Restrictions map(BidRequest2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    Restrictions restrictions = new Restrictions();

    enhance(source, restrictions, config, converterProvider);

    return restrictions;
  }

  @Override
  public void enhance(
      BidRequest2_X source, Restrictions target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    target.setBapp(CollectionUtils.copyCollection(source.getBapp(), config));
    target.setBcat(CollectionUtils.copyCollection(source.getBcat(), config));
    target.setBadv(CollectionUtils.copyCollection(source.getBadv(), config));
    if (source.getImp() == null) return;
    if (source.getImp().size() == 0) return;
    Collection<Integer> battr = new HashSet<>();
    for (Imp imp : source.getImp()) {
      if (imp.getBanner() != null && imp.getBanner().getBattr() != null) {
        battr.addAll(imp.getBanner().getBattr());
      } else if (imp.getVideo() != null && imp.getVideo().getBattr() != null) {
        battr.addAll(imp.getVideo().getBattr());
      } else if (imp.getNat() != null && imp.getNat().getBattr() != null) {
        battr.addAll(imp.getNat().getBattr());
      } else if (imp.getAudio() != null && imp.getAudio().getBattr() != null) {
        battr.addAll(imp.getAudio().getBattr());
      }
    }
    if (battr.size() > 0) {
      target.setBattr(CollectionUtils.copyCollection(battr, config));
    }
    target.setCattax(DEFAULT_CATTAX_TWODOTX);
    if (source.getExt() == null) return;
    try {
      if (source.getExt().containsKey(CommonConstants.CATTAX)) {
        target.setCattax((Integer) source.getExt().get(CommonConstants.CATTAX));
        source.getExt().remove(CommonConstants.CATTAX);
      }
      if (source.getExt().containsKey(CommonConstants.RESTRICTIONS)) {
        try {
          Map<String, Object> restrictions =
              (Map<String, Object>) source.getExt().get(CommonConstants.RESTRICTIONS);
          if (restrictions.containsKey(CommonConstants.EXT)) {
            target.setExt((Map<String, Object>) restrictions.get(CommonConstants.EXT));
          }
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("Error in converting pmp ext ", e);
        }
        source.getExt().remove(CommonConstants.RESTRICTIONS);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for BidRequest2_X", e);
    }
  }
}
