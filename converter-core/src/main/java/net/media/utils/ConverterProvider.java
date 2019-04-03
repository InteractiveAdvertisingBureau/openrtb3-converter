package net.media.utils;

import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;

/**
 * Created by shiva.b on 03/04/19.
 */
public class ConverterProvider extends Provider<Conversion, Converter> {
  public ConverterProvider(Converter defaultValue) {
    super(defaultValue);
  }

  public <X, Y> void register(Conversion<X, Y> conversion, Converter<X, Y> converter) {
    super.register(conversion, converter);
  }

  public <X, Y> Converter<X, Y> fetch(Conversion<X, Y> conversion) throws OpenRtbConverterException {
    try {
      return super.fetch(conversion);
    }
    catch (Exception e) {
      throw new OpenRtbConverterException("Wrong type for conversion " + conversion.getSource() +
        " " + conversion.getTarget(), e);
    }
  }
}
