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

import net.media.utils.validator.CheckAtLeastOneNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@CheckAtLeastOneNotNull(fieldNames = {"id", "buyeruid"})
public class User {
  private String id;
  private String buyeruid;
  private Integer yob;
  private String gender;
  private String keywords;
  private String consent;
  private Geo geo;
  private Collection<Data> data;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public String getBuyeruid() {
    return this.buyeruid;
  }

  public Integer getYob() {
    return this.yob;
  }

  public String getGender() {
    return this.gender;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public String getConsent() {
    return this.consent;
  }

  public Geo getGeo() {
    return this.geo;
  }

  public Collection<Data> getData() {
    return this.data;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setBuyeruid(String buyeruid) {
    this.buyeruid = buyeruid;
  }

  public void setYob(Integer yob) {
    this.yob = yob;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setConsent(String consent) {
    this.consent = consent;
  }

  public void setGeo(Geo geo) {
    this.geo = geo;
  }

  public void setData(Collection<Data> data) {
    this.data = data;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
