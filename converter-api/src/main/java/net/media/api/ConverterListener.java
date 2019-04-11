/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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
