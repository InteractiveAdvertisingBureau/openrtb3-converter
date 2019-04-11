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
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.*;
import net.media.openrtb3.Context;
import net.media.openrtb3.Restrictions;
import net.media.utils.Provider;

public class BidRequestToContextConverter implements Converter<BidRequest2_X, Context> {

  @Override
  public Context map(BidRequest2_X source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    Context context = new Context();

    enhance(source, context, config, converterProvider);

    return context;
  }

  @Override
  public void enhance(
      BidRequest2_X source, Context target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    Converter<Regs, net.media.openrtb3.Regs> regsRegsConverter =
        converterProvider.fetch(new Conversion<>(Regs.class, net.media.openrtb3.Regs.class));
    Converter<Site, net.media.openrtb3.Site> siteSiteConverter =
        converterProvider.fetch(new Conversion<>(Site.class, net.media.openrtb3.Site.class));
    Converter<User, net.media.openrtb3.User> userUserConverter =
        converterProvider.fetch(new Conversion<>(User.class, net.media.openrtb3.User.class));
    Converter<BidRequest2_X, Restrictions> bidRequestRestrictionsConverter =
        converterProvider.fetch(new Conversion<>(BidRequest2_X.class, Restrictions.class));
    Converter<App, net.media.openrtb3.App> appAppConverter =
        converterProvider.fetch(new Conversion<>(App.class, net.media.openrtb3.App.class));
    Converter<Device, net.media.openrtb3.Device> deviceDeviceConverter =
        converterProvider.fetch(new Conversion<>(Device.class, net.media.openrtb3.Device.class));
    target.setRegs(regsRegsConverter.map(source.getRegs(), config, converterProvider));
    target.setSite(siteSiteConverter.map(source.getSite(), config, converterProvider));
    target.setUser(userUserConverter.map(source.getUser(), config, converterProvider));
    target.setRestrictions(bidRequestRestrictionsConverter.map(source, config, converterProvider));
    target.setApp(appAppConverter.map(source.getApp(), config, converterProvider));
    target.setDevice(deviceDeviceConverter.map(source.getDevice(), config, converterProvider));
  }
}
