package net.media.utils;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionToCollectionConverter {
  public static <S,T> Collection<T> convert(Collection<S> collection, Converter<S, T>
    stConverter, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if ( collection == null ) {
      return null;
    }
    Collection<T> collection1;
    if (collection instanceof Set) {
      collection1 = new HashSet<T>( collection.size() );
    } else {
      collection1 = new ArrayList<T>( collection.size() );
    }
    for ( S value : collection ) {
      collection1.add( stConverter.map( value, config, converterProvider ) );
    }

    return collection1;
  }
}
