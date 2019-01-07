package net.media.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author shiva.b
 */
public class Utils {

  private static ObjectMapper mapper = new ObjectMapper();

  public static ObjectMapper getMapper() {
    return mapper;
  }

  public static <T> List<T> copyList(List<T> input, Config config){
    return new ArrayList<T>(input);
  }

  public static <T> Set<T> copyList(Set<T> input, Config config){
    return new HashSet<T>(input);
  }

  public static <U,V> Map<U,V> copyMap(Map<U,V> input, Config config){
    return new HashMap<>(input);
  }
}
