package net.media.converters;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.utils.Provider;

/**
 *  Interface from which classes responsible for executing transformation operations can be derived.
 *
 * @param <U> source class
 * @param <V> target class
 *
 * @author shiva.b
 */
public interface Converter<U, V> {

  /**
   * Converts object of type {@link U} to an object of type {@link V}.
   *
   * @param source object that needs to be converted
   * @param config see {@link Config}
   * @param converterProvider see {@link Provider}
   *
   * @return object generated after conversion
   *
   * @throws OpenRtbConverterException when conversion fails
   */
  V map(U source, Config config, Provider converterProvider) throws
    OpenRtbConverterException;

  /**
   * Performs modification on the object of type {@link V} using the object of type {@link U}.
   *
   * @param source object whose fields will be used to perform modification
   * @param target object that needs to be modified
   * @param config see {@link Config}
   * @param converterProvider see {@link Provider}
   *
   * @throws OpenRtbConverterException when modification operation fails
   */
  void enhance(U source, V target, Config config, Provider converterProvider) throws
    OpenRtbConverterException;
}
