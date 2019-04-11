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

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Data {

  private String id;
  private String name;
  private Collection<Segment> segment;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Collection<Segment> getSegment() {
    return this.segment;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSegment(Collection<Segment> segment) {
    this.segment = segment;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
