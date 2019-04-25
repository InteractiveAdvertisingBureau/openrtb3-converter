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
import net.media.openrtb25.request.Source;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.putToExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/01/19. */
public class SourceToSourceConverter implements Converter<Source, net.media.openrtb3.Source> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  static {
    extraFieldsInExt.add(CommonConstants.TS);
    extraFieldsInExt.add(CommonConstants.DS);
    extraFieldsInExt.add(CommonConstants.DSMAP);
    extraFieldsInExt.add(CommonConstants.CERT);
    extraFieldsInExt.add(CommonConstants.DIGEST);
  }

  @Override
  public net.media.openrtb3.Source map(Source source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Source source1 = new net.media.openrtb3.Source();

    enhance(source, source1, config, converterProvider);

    return source1;
  }

  @Override
  public void enhance(
      Source source, net.media.openrtb3.Source target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setTid(source.getTid());
    target.setPchain(source.getPchain());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    target.setExt(putToExt(source::getFd, target.getExt(), CommonConstants.FD));
    try {
      fetchFromExt(target::setTs, source.getExt(), CommonConstants.TS, "error while mapping ts from source");
      fetchFromExt(target::setDs, source.getExt(), CommonConstants.DS, "error while mapping ds from source");
      fetchFromExt(target::setDsmap, source.getExt(), CommonConstants.DSMAP, "error while mapping dsmap from source");
      fetchFromExt(target::setCert, source.getExt(), CommonConstants.CERT, "error while mapping cert from source");
      fetchFromExt(target::setDigest, source.getExt(), CommonConstants.DIGEST, "error while mapping digest from source");
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Source", e);
    }
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
