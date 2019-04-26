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

package net.media.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;

public class CollectionToCollectionConverter {

  public static <S, T> Collection<T> convert(
      Collection<S> collection,
      Converter<S, T> stConverter,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (collection == null) {
      return null;
    }
    Collection<T> collection1;
    if (collection instanceof Set) {
      collection1 = new HashSet<>(collection.size());
    } else {
      collection1 = new ArrayList<>(collection.size());
    }
    for (S value : collection) {
      collection1.add(stConverter.map(value, config, converterProvider));
    }

    return collection1;
  }
}
