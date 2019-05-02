/*
 * Copyright  2019 - present. IAB Tech Lab
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
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.*;

public class DeviceToDeviceConverter implements Converter<Device, net.media.openrtb3.Device> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.XFF);
    extraFieldsInExt.add(CommonConstants.IPTR);
    extraFieldsInExt.add(CommonConstants.MCCMNCSIM);
  }

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
    if (source == null || target == null) {
      return;
    }
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
      target.setOs(CommonConstants.osMap.getOrDefault(source.getOs().trim().toLowerCase(), 0));
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
      target.setExt(new HashMap<>(map));
    }
    putToExt(source::getFlashver, target.getExt(), CommonConstants.FLASHVER, target::setExt);
    if (source.getExt() == null) {
      return;
    }
    fetchFromExt(
      target::setXff, source.getExt(), CommonConstants.XFF, "error while mapping xff for Device");
    fetchFromExt(
      target::setIptr,
      source.getExt(),
      CommonConstants.IPTR,
      "error while mapping iptr for Device");
    fetchFromExt(
      target::setMccmncsim,
      source.getExt(),
      CommonConstants.MCCMNCSIM,
      "error while mapping mccmncsim for Device");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
