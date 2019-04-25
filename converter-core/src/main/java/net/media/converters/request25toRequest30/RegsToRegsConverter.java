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
import net.media.openrtb25.request.Regs;
import net.media.utils.CommonConstants;
import net.media.utils.MapUtils;
import net.media.utils.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.media.utils.ExtUtils.fetchFromExt;
import static net.media.utils.ExtUtils.removeFromExt;

public class RegsToRegsConverter implements Converter<Regs, net.media.openrtb3.Regs> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  static {
    extraFieldsInExt.add("gdpr");
  }

  @Override
  public net.media.openrtb3.Regs map(Regs source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    net.media.openrtb3.Regs regs1 = new net.media.openrtb3.Regs();

    enhance(source, regs1, config, converterProvider);

    return regs1;
  }

  @Override
  public void enhance(
      Regs source, net.media.openrtb3.Regs target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) return;
    target.setCoppa(source.getCoppa());
    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(new HashMap<>(map));
    }
    fetchFromExt(target::setGdpr, source.getExt(), CommonConstants.GDPR, "error while mapping gdpr from Regs");
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
