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

package net.media.driver;

import net.media.converters.response30toResponse24.Bid30ToBid24Converter;
import net.media.openrtb3.Bid;
import net.media.utils.Provider;

import java.util.function.Consumer;

/** Created by rajat.go on 04/04/19. */
public class Convert30To24ResponseManager implements Consumer<Provider> {

  @Override
  public void accept(Provider converterProvider) {
    converterProvider.register(
      new Conversion<>(Bid.class, net.media.openrtb25.response.Bid.class),
      new Bid30ToBid24Converter());
  }
}
