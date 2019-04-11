/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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
import net.media.openrtb25.request.Segment;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SegmentToSegmentConverter implements Converter<Segment, net.media.openrtb3.Segment> {
  @Override
  public net.media.openrtb3.Segment map(Segment source, Config config, Provider converterProvider) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Segment segment1 = new net.media.openrtb3.Segment();

    enhance( source, segment1, config, converterProvider );

    return segment1;
  }

  @Override
  public void enhance(Segment source, net.media.openrtb3.Segment target, Config config, Provider converterProvider) {
    if(source == null || target == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setValue( source.getValue() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }
  }
}
