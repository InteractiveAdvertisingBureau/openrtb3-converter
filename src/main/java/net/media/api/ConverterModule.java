package net.media.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import net.media.api.controllers.ConverterController;
import net.media.mapper.RequestConverter;

public class ConverterModule extends AbstractModule{

  @Override
  protected void configure() {
    bind(RequestConverter.class).in(Scopes.SINGLETON);
    bind(ConverterController.class).in(Scopes.SINGLETON);
  }
}
