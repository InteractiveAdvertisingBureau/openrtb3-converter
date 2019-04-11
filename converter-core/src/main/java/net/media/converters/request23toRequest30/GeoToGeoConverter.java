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

package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class GeoToGeoConverter extends net.media.converters.request25toRequest30.GeoToGeoConverter {

  public void enhance(Geo source, net.media.openrtb3.Geo target, Config config,
                      Provider converterProvider) throws
    OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("accuracy")) {
        try {
          source.setAccuracy((Integer) source.getExt().get("accuracy"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting accuracy from geo.ext.accuracy",
            e);
        }
        source.getExt().remove("accuracy");
      }
      if (source.getExt().containsKey("lastfix")) {
        try {
          source.setLastfix((Integer) source.getExt().get("lastfix"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting lastfix from geo.ext.lastfix",
            e);
        }
        source.getExt().remove("lastfix");
      }
      if (source.getExt().containsKey("ipservice")) {
        try {
          source.setIpservice((Integer) source.getExt().get("ipservice"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting ipservice from geo.ext.ipservice",
            e);
        }
        source.getExt().remove("ipservice");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
