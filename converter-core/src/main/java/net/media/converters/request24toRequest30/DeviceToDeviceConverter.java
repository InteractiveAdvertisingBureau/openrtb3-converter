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

package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Device;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

/** Created by rajat.go on 03/04/19. */
public class DeviceToDeviceConverter
    extends net.media.converters.request25toRequest30.DeviceToDeviceConverter {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  static {
    extraFieldsInExt.add("mccmnc");
  }

  public void enhance(
      Device source, net.media.openrtb3.Device target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    fetchFromExt(source::setMccmnc, source.getExt(), CommonConstants.MCCMNC, "Error in setting mccmnc from device.ext.mccmnc");
    super.enhance(source, target, config, converterProvider);
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
