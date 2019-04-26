/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.api;

import java.io.File;
import net.media.api.servlets.ConverterServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class ConverterApplication {

  private static void registerServletAndMapping(Context ctx) {
    Tomcat.addServlet(ctx, "converter", new ConverterServlet()).setLoadOnStartup(1);
    ctx.addServletMapping("/*", "converter");
  }

  public static void main(String[] args) throws LifecycleException {
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
