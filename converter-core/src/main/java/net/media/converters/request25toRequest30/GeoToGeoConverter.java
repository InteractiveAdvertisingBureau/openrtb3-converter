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
import net.media.openrtb25.request.Geo;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static net.media.utils.ExtUtils.putToExt;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb3.Geo> {
  @Override
  public net.media.openrtb3.Geo map(Geo source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Geo geo1 = new net.media.openrtb3.Geo();

    enhance(source, geo1, config, converterProvider);

    return geo1;
  }

  @Override
  public void enhance(
      Geo source, net.media.openrtb3.Geo target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    target.setIpserv(source.getIpservice());
    target.setAccur(source.getAccuracy());
    target.setType(source.getType());
    target.setLat(source.getLat());
    target.setLon(source.getLon());
    target.setLastfix(source.getLastfix());
    target.setCountry(source.getCountry());
    target.setRegion(source.getRegion());
    target.setMetro(source.getMetro());
    target.setCity(source.getCity());
    target.setUtcoffset(source.getUtcoffset());
    target.setZip(source.getZip());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    target.setExt(putToExt(source::getRegionfips104, target.getExt(), "regionfips104"));
  }
}
