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
