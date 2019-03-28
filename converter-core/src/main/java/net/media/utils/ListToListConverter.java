package net.media.utils;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;

import java.util.ArrayList;
import java.util.List;

public class ListToListConverter {
  public static <S,T> List<T> convert(List<S> list, Converter<S, T> stConverter, Config config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    List<T> list1 = new ArrayList<T>( list.size() );
    for ( S value : list ) {
      list1.add( stConverter.map( value, config ) );
    }

    return list1;
  }
}
