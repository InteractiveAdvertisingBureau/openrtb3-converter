package net.media.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author shiva.b
 */
public class Utils {

  private static ObjectMapper mapper = new ObjectMapper();

  public static ObjectMapper getMapper() {
    return mapper;
  }

  public static <T> List<T> copyList(List<T> input){
    return new ArrayList<T>(input);
  }
}
