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
import java.util.Map;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_THREEDOTX;

public class Restrictions {

  private Collection<String> bcat;
  private Integer cattax = DEFAULT_CATTAX_THREEDOTX;
  private Collection<String> badv;
  private Collection<String> bapp;
  private Collection<Integer> battr;
  private Map<String, Object> ext;

  public Collection<String> getBcat() {
    return this.bcat;
  }

  public Integer getCattax() {
    return this.cattax;
  }

  public Collection<String> getBadv() {
    return this.badv;
  }

  public Collection<String> getBapp() {
    return this.bapp;
  }

  public Collection<Integer> getBattr() {
    return this.battr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setBcat(Collection<String> bcat) {
    this.bcat = bcat;
  }

  public void setCattax(Integer cattax) {
    this.cattax = cattax;
  }

  public void setBadv(Collection<String> badv) {
    this.badv = badv;
  }

  public void setBapp(Collection<String> bapp) {
    this.bapp = bapp;
  }

  public void setBattr(Collection<Integer> battr) {
    this.battr = battr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
