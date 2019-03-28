package net.media.utils;

import net.media.Conversion;
import net.media.converters.Converter;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author shiva.b
 * @since 1.0
 */
public class ConverterProxy implements Function<Conversion,Converter> {

  private static final Provider<Conversion, Converter> BACKING_MAP = new Provider<>(null);

  private Supplier<Converter> backingSupplier;

  public ConverterProxy(Supplier<Converter> supplier) {
    this.backingSupplier = supplier;
  }

  public static void clear() {
    BACKING_MAP.clear();
  }

  @Override
  public Converter apply(Conversion conversion) {
    if (BACKING_MAP.contains(conversion)) {
      return BACKING_MAP.fetch(conversion);
    }
    else {
      synchronized (BACKING_MAP) {
        if (!BACKING_MAP.contains(conversion)) {
          BACKING_MAP.register(conversion, backingSupplier.get());
          return BACKING_MAP.fetch(conversion);
        }
        else {
          return BACKING_MAP.fetch(conversion);
        }
      }
    }
  }
}
