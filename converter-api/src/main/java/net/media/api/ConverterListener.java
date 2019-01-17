package net.media.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ConverterListener implements ServletContextListener {
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
