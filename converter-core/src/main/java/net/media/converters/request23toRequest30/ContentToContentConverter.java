package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Data;
import net.media.openrtb25.request.Producer;
import net.media.utils.Provider;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ContentToContentConverter extends net.media.converters
  .request25toRequest30.ContentToContentConverter {

  public void enhance(Content source, net.media.openrtb3.Content target, Config config,
                      Provider<Conversion, Converter> converterProvider) throws
    OpenRtbConverterException {
    if (source == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("artist")) {
        source.setArtist((String) source.getExt().get("artist"));
        source.getExt().remove("artist");
      }
      if (source.getExt().containsKey("genre")) {
        source.setGenre((String) source.getExt().get("genre"));
        source.getExt().remove("genre");
      }
      if (source.getExt().containsKey("album")) {
        source.setAlbum((String) source.getExt().get("album"));
        source.getExt().remove("album");
      }
      if (source.getExt().containsKey("isrc")) {
        source.setIsrc((String) source.getExt().get("isrc"));
        source.getExt().remove("isrc");
      }
      if (source.getExt().containsKey("prodq")) {
        source.setProdq((Integer) source.getExt().get("prodq"));
        source.getExt().remove("prodq");
      }
      if (source.getExt().containsKey("data")) {
        source.setData((Collection<Data>) source.getExt().get("data"));
        source.getExt().remove("data");
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
