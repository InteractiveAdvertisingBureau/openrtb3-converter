package net.media.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import net.media.config.Config;
import net.media.driver.ConverterManager;
import net.media.driver.OpenRtbConverter;

public class ConverterModule extends AbstractModule{

  @Override
  protected void configure() {
    bind(ConverterManager.class).toInstance(new ConverterManager(null, null));
    bind(Config.class).toInstance(new Config());
    bind(OpenRtbConverter.class).in(Scopes.SINGLETON);
  }
}
