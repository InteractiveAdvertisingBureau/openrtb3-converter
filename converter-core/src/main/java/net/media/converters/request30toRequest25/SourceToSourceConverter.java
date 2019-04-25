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
import net.media.openrtb3.Source;
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

public class SourceToSourceConverter
    implements Converter<Source, net.media.openrtb25.request.Source> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.FD);
  }

  @Override
  public net.media.openrtb25.request.Source map(
      Source source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb25.request.Source source1 = new net.media.openrtb25.request.Source();

    enhance(source, source1, config, converterProvider);

    return source1;
  }

  @Override
  public void enhance(
      Source source,
      net.media.openrtb25.request.Source target,
      Config config,
      Provider converterProvider)
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
    target.setExt(putToExt(source::getTs, target.getExt(), CommonConstants.TS));
    target.setExt(putToExt(source::getDs, target.getExt(), CommonConstants.DS));
    target.setExt(putToExt(source::getDsmap, target.getExt(), CommonConstants.DSMAP));
    target.setExt(putToExt(source::getCert, target.getExt(), CommonConstants.CERT));
    target.setExt(putToExt(source::getDigest, target.getExt(), CommonConstants.DIGEST));
    fetchFromExt(target::setFd, source.getExt(), CommonConstants.FD, "error while mapping fd from Source");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
