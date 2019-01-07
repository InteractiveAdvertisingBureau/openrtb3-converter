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

  V map(U source, Config config);

  void inhance(U source, V target, Config config);
}
