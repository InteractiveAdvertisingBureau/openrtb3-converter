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
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Data;
import net.media.openrtb3.Segment;
import net.media.utils.CollectionUtils;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.Map;

public class DataToDataConverter implements Converter<Data, net.media.openrtb3.Data> {

  @Override
  public net.media.openrtb3.Data map(Data source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Data data1 = new net.media.openrtb3.Data();

    enhance(source, data1, config, converterProvider);

    return data1;
  }

  @Override
  public void enhance(
      Data source, net.media.openrtb3.Data target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    Converter<net.media.openrtb25.request.Segment, Segment> segmentSegmentConverter =
        converterProvider.fetch(
            new Conversion<>(net.media.openrtb25.request.Segment.class, Segment.class));
    target.setId(source.getId());
    target.setName(source.getName());
    target.setSegment(
        CollectionUtils.convert(
            source.getSegment(), segmentSegmentConverter, config, converterProvider));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
    }
  }
}
