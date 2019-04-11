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

package net.media.openrtb3;

import javax.validation.constraints.NotNull;

public abstract class DistributionChannel {
  @NotNull
  private String id;
  private String name;
  private Publisher pub;
  private Content content;

  public @NotNull String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Publisher getPub() {
    return this.pub;
  }

  public Content getContent() {
    return this.content;
  }

  public void setId(@NotNull String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPub(Publisher pub) {
    this.pub = pub;
  }

  public void setContent(Content content) {
    this.content = content;
  }
}
