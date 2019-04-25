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
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.OsMap;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.putToExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class DeviceToDeviceConverter
    implements Converter<Device, net.media.openrtb25.request.Device> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.FLASHVER);
  }

  @Override
  public net.media.openrtb25.request.Device map(
      Device source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb25.request.Device device1 = new net.media.openrtb25.request.Device();

    enhance(source, device1, config, converterProvider);

    return device1;
  }

  @Override
  public void enhance(
      Device source,
      net.media.openrtb25.request.Device target,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter =
        converterProvider.fetch(new Conversion<>(Geo.class, net.media.openrtb25.request.Geo.class));
    target.setLanguage(source.getLang());
    target.setConnectiontype(source.getContype());
    target.setDevicetype(source.getType());
    target.setUa(source.getUa());
    target.setGeo(geoGeoConverter.map(source.getGeo(), config, converterProvider));
    if (source.getDnt() != null) {
      target.setDnt(Integer.parseInt(source.getDnt()));
    }
    target.setLmt(source.getLmt());
    target.setIp(source.getIp());
    target.setIpv6(source.getIpv6());
    if (source.getOs() != null) {
      if (OsMap.osMap.inverse().containsKey(source.getOs())) {
        target.setOs(OsMap.osMap.inverse().get(source.getOs()));
      }
    }
    target.setMake(source.getMake());
    target.setModel(source.getModel());
    target.setOsv(source.getOsv());
    target.setHwv(source.getHwv());
    target.setH(source.getH());
    target.setW(source.getW());
    target.setPpi(source.getPpi());
    target.setPxratio(source.getPxratio());
    target.setJs(source.getJs());
    target.setGeofetch(source.getGeofetch());
    target.setCarrier(source.getCarrier());
    target.setIfa(source.getIfa());
    target.setMccmnc(source.getMccmnc());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    fetchFromExt(target::setFlashver, source.getExt(), CommonConstants.FLASHVER, "error while mapping flashver from device.ext");
    target.setExt(putToExt(source::getXff, target.getExt(),CommonConstants.XFF));
    target.setExt(putToExt(source::getIptr, target.getExt(),CommonConstants.IPTR));
    target.setExt(putToExt(source::getMccmncsim, target.getExt(),CommonConstants.MCCMNCSIM));
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
