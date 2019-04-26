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

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.Map;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audit;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

/**
 * @author shiva.b
 */
public class BidToAuditConverter implements Converter<Bid, Audit> {

  @Override
  public Audit map(Bid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    Audit audit = new Audit();
    enhance(source, audit, config, converterProvider);
    return audit;
  }

  @Override
  public void enhance(Bid source, Audit target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source) || isNull(target)) {
      return;
    }
    Map<String, Object> map = source.getExt();
    if (map != null) {
      try {
        target.setExt(MapUtils.copyMap(map, config));
        if (map.containsKey(CommonConstants.CORR)) {
          target.setCorr((Map<String, Object>) map.get(CommonConstants.CORR));
        }
        if (map.containsKey(CommonConstants.STATUS)) {
          target.setStatus((Integer) map.get(CommonConstants.STATUS));
        }
        if (map.containsKey(CommonConstants.AUDIT)) {
          target.setInit(((Audit) map.get(CommonConstants.AUDIT)).getInit());
        }
        if (map.containsKey(CommonConstants.LASTMOD)) {
          target.setLastmod((Integer) map.get(CommonConstants.LASTMOD));
        }
        if (map.containsKey(CommonConstants.FEEDBACK)) {
          target.setFeedback((Collection<String>) map.get(CommonConstants.FEEDBACK));
        }
      } catch (Exception e) {
        throw new OpenRtbConverterException("error while type casting in bid.ext", e);
      }
    } else {
      target.setExt(null);
    }
  }
}
