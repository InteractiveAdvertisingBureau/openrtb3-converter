package net.media.driver;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.enums.OpenRtbVersion;
import net.media.utils.Provider;

import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * Stores and manages {@link Converter} implementations that can be used for various transformation operations.
 *
 * @author shiva.b
 */
@SuppressWarnings("unchecked")
public class ConverterManager {

  private Provider converterProvider;

  public ConverterManager(Map<Conversion, Converter> overrideMap, Config config) {
    converterProvider = new Provider();
    new Convert25To30RequestManager(converterProvider);
    new Convert30To25RequestManager(converterProvider);
    new Convert25To30ResponseManager(converterProvider);
    new Convert30To25ResponseManager(converterProvider);
    add2_XConverters(converterProvider, config);
    if (nonNull(overrideMap)) {
      overrideMap.forEach((conversion, converter) -> {
        converterProvider.register(conversion, converter);
      });
    }
  }

  private void add2_XConverters(Provider converterProvider, Config config) {
    if (nonNull(config)) {
      if (OpenRtbVersion.TWO_DOT_FIVE.equals(config.getOpenRtbVersion2_XVersion())) {
        new Convert25To30RequestManager(converterProvider);
        new Convert30To25RequestManager(converterProvider);
        new Convert25To30ResponseManager(converterProvider);
        new Convert30To25ResponseManager(converterProvider);
      } else if (OpenRtbVersion.TWO_DOT_FOUR.equals(config.getOpenRtbVersion2_XVersion())) {
        new Convert25To30RequestManager(converterProvider);
        new Convert30To25RequestManager(converterProvider);
        new Convert25To30ResponseManager(converterProvider);
        new Convert30To25ResponseManager(converterProvider);
        new Convert24To30RequestManager(converterProvider);
        new Convert30To24RequestManager(converterProvider);
        new Convert24To30ResponseManager(converterProvider);
        new Convert30To24ResponseManager(converterProvider);
      } else if (OpenRtbVersion.TWO_DOT_THREE.equals(config.getOpenRtbVersion2_XVersion())) {
        new Convert25To30RequestManager(converterProvider);
        new Convert30To25RequestManager(converterProvider);
        new Convert25To30ResponseManager(converterProvider);
        new Convert30To25ResponseManager(converterProvider);
        new Convert23To30RequestManager(converterProvider);
        new Convert30To23RequestManager(converterProvider);
        new Convert23To30ResponseManager(converterProvider);
        new Convert30To23ResponseManager(converterProvider);
      }
    }
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion<>(source, target));
  }

  public Provider getConverterProvider(Map<Conversion, Converter> overrideMap, Config config) {
    Provider provider = new Provider(converterProvider);
    add2_XConverters(provider, config);
    if (nonNull(overrideMap)) {
      overrideMap.forEach((conversion, converter) -> {
        provider.register(conversion, converter);
      });
    }
    return provider;
  }

}
