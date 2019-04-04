package net.media.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import net.media.ConverterPlumber;
import net.media.OpenRtbConverter;
import net.media.config.Config;

public class ConverterModule extends AbstractModule{

  @Override
  protected void configure() {
    bind(ConverterPlumber.class).toInstance(new ConverterPlumber());
    bind(Config.class).toInstance(new Config());
    bind(OpenRtbConverter.class).in(Scopes.SINGLETON);
  }
}
