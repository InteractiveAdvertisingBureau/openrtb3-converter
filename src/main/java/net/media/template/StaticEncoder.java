package net.media.template;

import com.google.common.base.Charsets;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.function.Function;

/**
 * Created by shiva.b on 02/01/19.
 */
public enum StaticEncoder implements Function<String, String> {


  B64(source -> Base64.getEncoder().encodeToString(source.getBytes())),
  UTF8(source -> {
    try {
      return URLEncoder.encode(source, Charsets.UTF_8.displayName());
    } catch (UnsupportedEncodingException e) {
      return source;
    }
  }),
  NO_ENCODE(data -> data);

  private Function<String, String> encoder;
  private String tag = name();

  StaticEncoder(Function<String, String> converter) {
    this.encoder = converter;
  }

  StaticEncoder(Function<String, String> encoder, String tag) {
    this.encoder = encoder;
    this.tag = tag;
  }

  public static StaticEncoder getValue(String tag) {
    if (tag == null || tag.isEmpty()) {
      return NO_ENCODE;
    }
    for (StaticEncoder encoder : StaticEncoder.values()) {
      if (encoder.tag.equalsIgnoreCase(tag)) {
        return encoder;
      }
    }
    return NO_ENCODE;
  }

  @Override
  public String apply(String source) {
    if (source == null || source.isEmpty()) {
      return source;
    }
    return encoder.apply(source);
  }
}
