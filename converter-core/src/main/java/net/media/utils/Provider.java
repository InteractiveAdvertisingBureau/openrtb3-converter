package net.media.utils;

import net.media.converters.Converter;
import net.media.driver.Conversion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

/**
 * Generic Provider maintains a one to one mapping of key object of type {@link K}
 * and a value of object of type {@link V}.
 *
 * This class is ThreadSafe. Allows concurrent access for both reads and writes.
 *
 * {@link #providerMap} is a {@link ConcurrentHashMap} used for maintaining this mapping.
 *
 * {@link #register(Object, Object)} used for assigning a new mapping from {@link K} to {@link V}
 *
 * {@link #fetch(Object)} used to fetch the value {@link V} corresponding to the input key {@link K}
 *
 * {@link #getDefaultValue()} used to get the default value for the provider.
 *
 * {@link #fetchDefaultIfNotFound(Object)} returns the value corresponding to the key {@link K}
 * if found in the map else returns the {@link #defaultValue}
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
    Converter<X, Y> value = providerMap.get(key);
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