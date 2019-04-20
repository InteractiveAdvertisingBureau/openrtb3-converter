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
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.HashMap;
import java.util.Map;

public class SourceToSourceConverter
    implements Converter<Source, net.media.openrtb25.request.Source> {
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
    if (source == null || target == null) return;
    target.setTid(source.getTid());
    target.setPchain(source.getPchain());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
    }
    if (source.getTs() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put("ts", source.getTs());
    }
    if (source.getDs() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put("ds", source.getDs());
    }
    if (source.getDsmap() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put("dsmap", source.getDsmap());
    }
    if (source.getCert() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put("cert", source.getCert());
    }
    if (source.getDigest() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put("digest", source.getDigest());
    }

    if (source.getExt() == null) return;
    if (source.getExt().containsKey("fd")) {
      try {
        target.setFd((Integer) source.getExt().get("fd"));
        target.getExt().remove("fd");
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for Source", e);
      }
    }
  }
}
