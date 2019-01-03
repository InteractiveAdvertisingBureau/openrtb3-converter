package net.media.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author shiva.b
 */
public class Utils {

  private static ObjectMapper mapper = new ObjectMapper();

  public static ObjectMapper getMapper() {
    return mapper;
  }
}
