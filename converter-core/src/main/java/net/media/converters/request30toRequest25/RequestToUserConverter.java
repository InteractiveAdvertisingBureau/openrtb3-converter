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

package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.openrtb25.request.User;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

public class RequestToUserConverter implements Converter<Request, User> {
  @Override
  public User map(Request source, Config config, Provider converterProvider) {
    return null;
  }

  @Override
  public void enhance(Request source, User target, Config config, Provider converterProvider) {
    if ( source == null ) {
      return;
    }

    target.setCustomdata( source.getCdata() );
  }
}
