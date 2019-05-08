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

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.OpenRtbVersion;
import net.media.utils.Provider;

import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Stores and manages {@link Converter} implementations that can be used for various transformation
 * operations.
 *
 * @author shiva.b
 */
@SuppressWarnings("unchecked")
public class ConverterManager {

  private static final Convert25To30RequestManager convert25To30RequestManager = new
    Convert25To30RequestManager();

  private static final Convert25To30ResponseManager convert25To30ResponseManager = new
    Convert25To30ResponseManager();

  private static final Convert30To25RequestManager convert30To25RequestManager = new
    Convert30To25RequestManager();

  private static final Convert30To25ResponseManager convert30To25ResponseManager = new
    Convert30To25ResponseManager();

  private static final Convert24To30RequestManager convert24To30RequestManager = new
    Convert24To30RequestManager();

  private static final Convert30To24RequestManager convert30To24RequestManager = new
    Convert30To24RequestManager();

  private static final Convert24To30ResponseManager convert24To30ResponseManager = new
    Convert24To30ResponseManager();

  private static final Convert30To24ResponseManager convert30To24ResponseManager = new
    Convert30To24ResponseManager();

  private static final Convert23To30RequestManager convert23To30RequestManager = new
    Convert23To30RequestManager();

  private static final Convert30To23RequestManager convert30To23RequestManager = new
    Convert30To23RequestManager();

  private static final Convert23To30ResponseManager convert23To30ResponseManager = new
    Convert23To30ResponseManager();

  private static final Convert30To23ResponseManager convert30To23ResponseManager = new
    Convert30To23ResponseManager();

  private Provider converterProvider;

  private Provider converterProvider2_3;

  private Provider converterProvider2_4;

  public ConverterManager() {
    converterProvider = new Provider();
    convert25To30RequestManager.accept(converterProvider);
    convert30To25RequestManager.accept(converterProvider);
    convert25To30ResponseManager.accept(converterProvider);
    convert30To25ResponseManager.accept(converterProvider);

    converterProvider2_4 = new Provider();
    convert25To30RequestManager.accept(converterProvider2_4);
    convert30To25RequestManager.accept(converterProvider2_4);
    convert25To30ResponseManager.accept(converterProvider2_4);
    convert30To25ResponseManager.accept(converterProvider2_4);
    convert24To30RequestManager.accept(converterProvider2_4);
    convert30To24RequestManager.accept(converterProvider2_4);
    convert24To30ResponseManager.accept(converterProvider2_4);
    convert30To24ResponseManager.accept(converterProvider2_4);

    converterProvider2_3 = new Provider();
    convert25To30RequestManager.accept(converterProvider2_3);
    convert30To25RequestManager.accept(converterProvider2_3);
    convert25To30ResponseManager.accept(converterProvider2_3);
    convert30To25ResponseManager.accept(converterProvider2_3);
    convert23To30RequestManager.accept(converterProvider2_3);
    convert30To23RequestManager.accept(converterProvider2_3);
    convert23To30ResponseManager.accept(converterProvider2_3);
    convert30To23ResponseManager.accept(converterProvider2_3);
  }

  public Provider getConverterProvider(Map<Conversion, Converter> overrideMap, Config config) {
    Provider provider = null;
    if (nonNull(config)) {
      if (OpenRtbVersion.TWO_DOT_FOUR.equals(config.getOpenRtbVersion2_XVersion())) {
        provider = new Provider(converterProvider2_4);
      } else if (OpenRtbVersion.TWO_DOT_THREE.equals(config.getOpenRtbVersion2_XVersion())) {
        provider = new Provider(converterProvider2_3);
      }
    }
    if (isNull(provider)) {
      provider = new Provider(converterProvider);
    }
    if (nonNull(overrideMap)) {
      for (Map.Entry<Conversion, Converter> entry : overrideMap.entrySet()) {
        provider.register(entry.getKey(), entry.getValue());
      }
    }
    return provider;
  }
}
