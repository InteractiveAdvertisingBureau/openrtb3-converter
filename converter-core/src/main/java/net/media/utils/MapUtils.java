package net.media.utils;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;
import net.media.config.Config;

/**
 * Created by rajat.go on 21/04/19.
 */
public class MapUtils {

  public static <U, V> Map<U, V> copyMap(Map<U, V> input, Config config) {
    if (config.isCloningDisabled()) {
      return input;
    }
    if (nonNull(input)) {
      return new HashMap<>(input);
    }
    return null;
  }
}
