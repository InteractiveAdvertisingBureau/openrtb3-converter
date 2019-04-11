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

package net.media.converters;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.utils.Provider;

/**
 * Interface from which classes responsible for executing transformation operations can be derived.
 *
 * @param <U> source class
 * @param <V> target class
 * @author shiva.b
 */
public interface Converter<U, V> {

  /**
   * Converts object of type {@link U} to an object of type {@link V}.
   *
   * @param source object that needs to be converted
   * @param config see {@link Config}
   * @param converterProvider see {@link Provider}
   * @return object generated after conversion
   * @throws OpenRtbConverterException when conversion fails
   */
  V map(U source, Config config, Provider converterProvider) throws OpenRtbConverterException;

  /**
   * Performs modification on the object of type {@link V} using the object of type {@link U}.
   *
   * @param source object whose fields will be used to perform modification
   * @param target object that needs to be modified
   * @param config see {@link Config}
   * @param converterProvider see {@link Provider}
   * @throws OpenRtbConverterException when modification operation fails
   */
  void enhance(U source, V target, Config config, Provider converterProvider)
      throws OpenRtbConverterException;
}
