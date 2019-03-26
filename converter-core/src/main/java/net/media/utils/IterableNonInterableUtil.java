package net.media.utils;

import java.util.Collection;

/**
 * Created by rajat.go on 19/12/18.
 */
public class IterableNonInterableUtil {

  @FirstElement
  public <T> T first( Collection<T> in ) {
    if ( in != null && !in.isEmpty() ) {
      return in.iterator().next();
    }
    else {
      return null;
    }
  }
}
