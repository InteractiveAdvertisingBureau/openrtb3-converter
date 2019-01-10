package net.media.api;


import net.media.api.servlets.ConverterServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class ConverterApplication {

  private static void registerServletAndMapping(Context ctx) {
    Tomcat.addServlet(ctx, "converter", new ConverterServlet()).setLoadOnStartup(1);
    ctx.addServletMapping("/*", "converter");

  }

  public static void main(String[] args) throws LifecycleException, ServletException {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    String webappDirLocation = ".";

    Context ctx = tomcat.addContext("/Converter", new File(webappDirLocation).getAbsolutePath());

    ctx.setDisplayName("Video Ads");
    ctx.addParameter("isLog4jAutoInitializationDisabled", "true");
    ctx.addLifecycleListener(new Tomcat.FixContextListener());
    ctx.addApplicationListener(ConverterApplication.class.getName());
    registerServletAndMapping(ctx);
    tomcat.setPort(9090);

    tomcat.start();
    tomcat.getServer().await();
  }
}
