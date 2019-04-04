package net.media.converters;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.utils.Provider;

/**
 *
 * @param <U> source object
 * @param <V> target object
 *
 * @author shiva.b
 */
public interface Converter<U, V> {

  V map(U source, Config config, Provider converterProvider) throws
    OpenRtbConverterException;

  void enhance(U source, V target, Config config, Provider converterProvider) throws
    OpenRtbConverterException;
}
