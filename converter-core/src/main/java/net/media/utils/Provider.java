package net.media.utils;

import net.media.converters.Converter;
import net.media.driver.Conversion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

/**
 * Generic Provider maintains a one to one mapping of a key to a value.
 *
 * This class is thread safe. Allows concurrent access for both reads and writes.
 *
 * @author shiva.b
 * @since 1.0
 */
public class Provider {

  private Map<Conversion, Converter> providerMap;

  public Provider() {
    providerMap = new ConcurrentHashMap<>();
  }

  public Provider(Provider provider) {
    providerMap = new ConcurrentHashMap<>(provider.providerMap);
  }

  /**
   * @param key
   * @param value
   */
  public <X, Y> void register(Conversion<X, Y> key, Converter<X, Y> value) {
    providerMap.put(key, value);
  }

  /**
   *
   * @param key
   * @return
   * @throws IllegalArgumentException
   */
  public <X, Y> Converter<X, Y> fetch(Conversion<X, Y> key) throws IllegalArgumentException {
    Converter<X, Y> value;
    try {
      value = providerMap.get(key);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in casting converter ", e);
    }
    if (isNull(value)) {
      throw new IllegalArgumentException("key not registered " + key);
    }
    return value;
  }

  public <X, Y> boolean contains(Conversion<X, Y> key) {
    if (isNull(key) || !providerMap.containsKey(key)) {
      return false;
    }
    return true;
  }

  /**
   * clears the entire map
   */
  public void clear() {
    providerMap.clear();
  }

}