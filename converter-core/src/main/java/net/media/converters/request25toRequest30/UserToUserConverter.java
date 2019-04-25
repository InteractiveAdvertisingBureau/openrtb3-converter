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
import net.media.openrtb25.request.Geo;
import net.media.openrtb25.request.User;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class UserToUserConverter implements Converter<User, net.media.openrtb3.User> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.CONSENT);
  }

  @Override
  public net.media.openrtb3.User map(User source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.User user1 = new net.media.openrtb3.User();

    enhance(source, user1, config, converterProvider);

    return user1;
  }

  @Override
  public void enhance(
      User source, net.media.openrtb3.User target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<Geo, net.media.openrtb3.Geo> geoToGeoConverter =
        converterProvider.fetch(new Conversion<>(Geo.class, net.media.openrtb3.Geo.class));
    Converter<Data, net.media.openrtb3.Data> dataDataConverter =
        converterProvider.fetch(new Conversion<>(Data.class, net.media.openrtb3.Data.class));
    target.setId(source.getId());
    target.setBuyeruid(source.getBuyeruid());
    target.setYob(source.getYob());
    target.setGender(source.getGender());
    target.setKeywords(source.getKeywords());
    target.setGeo(geoToGeoConverter.map(source.getGeo(), config, converterProvider));
    target.setData(
        CollectionToCollectionConverter.convert(
            source.getData(), dataDataConverter, config, converterProvider));
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    fetchFromExt(target::setConsent, source.getExt(), CommonConstants.CONSENT, "error while mapping consent from user");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
