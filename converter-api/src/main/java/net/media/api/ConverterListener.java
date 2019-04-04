package net.media.api;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConverterListener implements ServletContextListener {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConverterListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      Injector injector = Guice.createInjector(new ConverterModule());
      sce.getServletContext().setAttribute("INJECTOR", injector);
    } catch (Exception e) {
      log.error("" ,e);
    }
  }
}
