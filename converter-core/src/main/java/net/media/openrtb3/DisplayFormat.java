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

/**
 * Created by shiva.b on 14/12/18.
 */

public class DisplayFormat {
  private Integer w;
  private Integer h;
  private Integer wratio;
  private Integer hratio;
  private Collection<Integer> expdir;
  private Map<String, Object> ext;

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getWratio() {
    return this.wratio;
  }

  public Integer getHratio() {
    return this.hratio;
  }

  public Collection<Integer> getExpdir() {
    return this.expdir;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setWratio(Integer wratio) {
    this.wratio = wratio;
  }

  public void setHratio(Integer hratio) {
    this.hratio = hratio;
  }

  public void setExpdir(Collection<Integer> expdir) {
    this.expdir = expdir;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
