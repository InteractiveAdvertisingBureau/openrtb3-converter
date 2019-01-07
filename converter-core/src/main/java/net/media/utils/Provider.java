package net.media.utils;

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
 */
public class Provider<K,V> {

  private Map<K,V> providerMap;

  private V defaultValue;

  public Provider(V defaultValue) {
    providerMap = new ConcurrentHashMap<>();
    this.defaultValue = defaultValue;
  }

  /**
   * @param key
   * @param value
   */
  public void register(K key, V value) {
    providerMap.put(key, value);
  }

  /**
   *
   * @param key
   * @return
   * @throws IllegalArgumentException
   */
  public V fetch(K key) throws IllegalArgumentException {
    V value = providerMap.get(key);
    if (isNull(value)) {
      throw new IllegalArgumentException("key not registered " + key);
    }
    return value;
  }

  /**
   * @apiNote final value could be null in case the key is not found in the map and default value
   * is null.
   *
   * @param key
   * @return
   */
  public V fetchDefaultIfNotFound(K key) {
    if (isNull(key)) {
      return defaultValue;
    }
    V value = providerMap.get(key);
    if (isNull(value)) {
      return defaultValue;
    }
    return value;
  }

  /**
   *
   * @return default value for the mapping
   */
  public V getDefaultValue() {
    return this.defaultValue;
  }
}