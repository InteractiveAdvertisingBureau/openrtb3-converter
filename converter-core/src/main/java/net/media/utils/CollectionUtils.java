package net.media.utils;

import java.util.Collection;

/**
 * Created by rajat.go on 04/01/19.
 */
public class CollectionUtils {

  public static <T> T firstElementFromCollection( Collection<T> in ) {
    if ( in != null && !in.isEmpty() ) {
      return in.iterator().next();
    }
    else {
      return null;
    }
  }

  public static boolean isEmpty(Collection collection) {
    return (collection == null || collection.isEmpty());
  }
}
