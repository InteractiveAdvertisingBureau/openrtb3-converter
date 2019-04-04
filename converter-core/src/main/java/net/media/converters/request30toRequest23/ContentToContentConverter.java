package net.media.converters.request30toRequest23;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Producer;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class ContentToContentConverter extends net.media.converters.request30toRequest25.ContentToContentConverter {

  public void enhance(Content source, net.media.openrtb25.request.Content target, Config config,
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
      target.getExt().put("artist", target.getArtist());
      target.setArtist(null);
    }
    if (nonNull(target.getGenre())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("genre", target.getGenre());
      target.setGenre(null);
    }
    if (nonNull(target.getAlbum())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("album", target.getAlbum());
      target.setAlbum(null);
    }
    if (nonNull(target.getIsrc())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("isrc", target.getIsrc());
      target.setIsrc(null);
    }
    if (nonNull(target.getProdq())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("prodq", target.getProdq());
      target.setProdq(null);
    }
    if (nonNull(target.getData())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("data", target.getData());
      target.setData(null);
    }
  }

}
