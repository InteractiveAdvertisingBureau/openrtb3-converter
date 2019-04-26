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
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Geo;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.OsMap;
import net.media.utils.Provider;

import java.util.HashMap;
import java.util.Map;

public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb3.Device> {

  @Override
  public net.media.openrtb3.Device map(Device source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Device device1 = new net.media.openrtb3.Device();

    enhance(source, device1, config, converterProvider);

    return device1;
  }

  @Override
  public void enhance(
      Device source, net.media.openrtb3.Device target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter =
        converterProvider.fetch(new Conversion<>(Geo.class, net.media.openrtb3.Geo.class));
    target.setContype(source.getConnectiontype());
    target.setType(source.getDevicetype());
    target.setLang(source.getLanguage());
    target.setUa(source.getUa());
    target.setIfa(source.getIfa());
    if (source.getDnt() != null) {
      target.setDnt(String.valueOf(source.getDnt()));
    }
    target.setLmt(source.getLmt());
    target.setMake(source.getMake());
    target.setModel(source.getModel());
    if (source.getOs() != null) {
      target.setOs(OsMap.osMap.getOrDefault(source.getOs().trim().toLowerCase(), 0));
    }
    target.setOsv(source.getOsv());
    target.setHwv(source.getHwv());
    target.setH(source.getH());
    target.setW(source.getW());
    target.setPpi(source.getPpi());
    target.setPxratio(source.getPxratio());
    target.setJs(source.getJs());
    target.setIp(source.getIp());
    target.setIpv6(source.getIpv6());
    target.setCarrier(source.getCarrier());
    target.setGeofetch(source.getGeofetch());
    target.setGeo(geoToGeoConverter.map(source.getGeo(), config, converterProvider));
    target.setMccmnc(source.getMccmnc());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
    }
    if (source.getFlashver() != null) {
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put(CommonConstants.FLASHVER, source.getFlashver());
    }
    if (source.getExt() == null) return;
    try {
      if (source.getExt().containsKey(CommonConstants.XFF)) {
        target.setXff((String) source.getExt().get(CommonConstants.XFF));
        target.getExt().remove(CommonConstants.XFF);
      }
      if (source.getExt().containsKey(CommonConstants.IPTR)) {
        target.setIptr((Integer) source.getExt().get(CommonConstants.IPTR));
        target.getExt().remove(CommonConstants.IPTR);
      }
      if (source.getExt().containsKey(CommonConstants.MCCMNCSIM)) {
        target.setMccmncsim((String) source.getExt().get(CommonConstants.MCCMNCSIM));
        target.getExt().remove(CommonConstants.MCCMNCSIM);
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Device", e);
    }
  }
}
