package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Source;

/**
 * Created by rajat.go on 03/01/19.
 */
public class SourceToSourceConverter implements Converter<Source, net.media.openrtb3.Source> {
  @Override
  public net.media.openrtb3.Source map(Source source, Config config) {
    return null;
  }

  @Override
  public void inhance(Source source, net.media.openrtb3.Source target, Config config) {

  }
}
