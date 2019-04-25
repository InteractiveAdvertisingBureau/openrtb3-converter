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
import net.media.openrtb3.Geo;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class GeoToGeoConverter implements Converter<Geo, net.media.openrtb25.request.Geo> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  static {
    extraFieldsInExt.add("regionfips104");
  }

  @Override
  public net.media.openrtb25.request.Geo map(Geo source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb25.request.Geo geo1 = new net.media.openrtb25.request.Geo();

    enhance(source, geo1, config, converterProvider);

    return geo1;
  }

  @Override
  public void enhance(
      Geo source, net.media.openrtb25.request.Geo target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    target.setIpservice(source.getIpserv());
    target.setAccuracy(source.getAccur());
    target.setType(source.getType());
    target.setRegion(source.getRegion());
    target.setMetro(source.getMetro());
    target.setCity(source.getCity());
    target.setZip(source.getZip());
    target.setCountry(source.getCountry());
    target.setLat(source.getLat());
    target.setLon(source.getLon());
    target.setUtcoffset(source.getUtcoffset());
    target.setLastfix(source.getLastfix());
    Map<String, Object> map = source.getExt();
    fetchFromExt(target::setRegionfips104, source.getExt(), CommonConstants.REGIONFIPS_104, "error while mapping regionfips104 from geo.ext");
    if(nonNull(map))
      target.setExt(new HashMap<>(map));
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
