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

package net.media.converters.request30toRequest23;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.HashMap;
import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Content;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ContentToContentConverter
    extends net.media.converters.request30toRequest25.ContentToContentConverter {

  public void enhance(
      Content source,
      net.media.openrtb25.request.Content target,
      Config config,
      Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    super.enhance(source, target, config, converterProvider);
    if (nonNull(target.getArtist())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.ARTIST, target.getArtist());
      target.setArtist(null);
    }
    if (nonNull(target.getGenre())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.GENRE, target.getGenre());
      target.setGenre(null);
    }
    if (nonNull(target.getAlbum())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.ALBUM, target.getAlbum());
      target.setAlbum(null);
    }
    if (nonNull(target.getIsrc())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.ISRC, target.getIsrc());
      target.setIsrc(null);
    }
    if (nonNull(target.getProdq())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.PRODQ, target.getProdq());
      target.setProdq(null);
    }
    if (nonNull(target.getData())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put(CommonConstants.DATA, target.getData());
      target.setData(null);
    }
  }
}
