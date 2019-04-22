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

package net.media;

import com.fasterxml.jackson.databind.JavaType;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Data;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Collection;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class CustomContentToContentConverter
  extends net.media.converters.request25toRequest30.ContentToContentConverter {

  private static final JavaType javaTypeForDataCollection = Utils.getMapper().getTypeFactory()
    .constructCollectionType(Collection.class, Data.class);

  public void enhance(
    Content source, net.media.openrtb3.Content target, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("artist")) {
        try {
          source.setArtist((String) source.getExt().get("artist"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting artist from content.ext.artist", e);
        }
        source.getExt().remove("artist");
      }
      if (source.getExt().containsKey("genre")) {
        try {
          source.setGenre((String) source.getExt().get("genre"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting genre from content.ext.genre", e);
        }
        source.getExt().remove("genre");
      }
      if (source.getExt().containsKey("album")) {
        try {
          source.setAlbum((String) source.getExt().get("album"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting album from content.ext.album", e);
        }
        source.getExt().remove("album");
      }
      if (source.getExt().containsKey("isrc")) {
        try {
          source.setIsrc((String) source.getExt().get("isrc"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting isrc from content.ext.isrc", e);
        }
        source.getExt().remove("isrc");
      }
      if (source.getExt().containsKey("prodq")) {
        try {
          source.setProdq((Integer) source.getExt().get("prodq"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting prodq from content.ext.prodq", e);
        }
        source.getExt().remove("prodq");
      }
//      if (source.getExt().containsKey("data")) {
//        try {
//          source.setData(Utils.getMapper().convertValue(source.getExt().get("data"),
//            javaTypeForDataCollection));
//        } catch (Exception e) {
//          throw new OpenRtbConverterException("Error in setting data from content.ext.data", e);
//        }
        source.getExt().remove("data");
//      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
