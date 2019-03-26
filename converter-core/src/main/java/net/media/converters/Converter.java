package net.media.converters;

import net.media.OpenRtbConverterException;
import net.media.config.Config;

/**
 *
 * @param <U> source object
 * @param <V> target object
 *
 * @author shiva.b
 */
public interface Converter<U, V> {

  V map(U source, Config config) throws OpenRtbConverterException;

  void enhance(U source, V target, Config config) throws OpenRtbConverterException;
}
