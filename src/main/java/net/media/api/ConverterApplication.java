package net.media.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.media.api.controllers.ConverterController;

public class ConverterApplication extends Application<Configuration> {

  @Override
  public void initialize(Bootstrap<Configuration> b) {
    b.getObjectMapper().setVisibility(
      b.getObjectMapper()
        .getSerializationConfig()
        .getDefaultVisibilityChecker()
        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
  }


  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {
    Injector injector = Guice.createInjector(new ConverterModule());
    environment.jersey().register(injector.getInstance(ConverterController.class));
    environment.jersey().setUrlPattern("/api/*");
  }

  public static void main(String[] args) throws Exception {
    new ConverterApplication().run(args);
  }
}
