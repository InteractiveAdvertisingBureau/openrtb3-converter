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
import net.media.openrtb3.Audit;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** @author shiva.b */
public class BidToAuditConverter implements Converter<Bid, Audit> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CORR);
    extraFieldsInExt.add(CommonConstants.STATUS);
    extraFieldsInExt.add(CommonConstants.AUDIT);
    extraFieldsInExt.add(CommonConstants.LASTMOD);
    extraFieldsInExt.add(CommonConstants.FEEDBACK);
  }

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
    fetchFromExt(
      target::setCorr,
      source.getExt(),
      CommonConstants.CORR,
      "Error while mapping corr from bid.ext");
    fetchFromExt(
      target::setStatus,
      source.getExt(),
      CommonConstants.STATUS,
      "Error while mapping status from bid.ext");
    fetchFromExt(
      target::setInit,
      source.getExt(),
      CommonConstants.INIT,
      "Error while mapping init from bid.ext");
    fetchFromExt(
      target::setLastmod,
      source.getExt(),
      CommonConstants.LASTMOD,
      "Error while mapping lastmod from bid.ext");
    fetchFromExt(
      target::setFeedback,
      source.getExt(),
      CommonConstants.FEEDBACK,
      "Error while mapping feedback from bid.ext");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
