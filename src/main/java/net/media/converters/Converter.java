package net.media.converters;

import net.media.config.Config;

/**
 *
 * @param <U> source object
 * @param <V> target object
 *
 * @author shiva.b
 */
public interface Converter<U, V> {

  public V map(U source, Config config);

  public void inhance(U source, V target, Config config);
}
