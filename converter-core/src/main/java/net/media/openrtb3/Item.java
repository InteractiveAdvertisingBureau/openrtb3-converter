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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Item {

  private static final Integer DEFAULT_QUANTITY = 1;
  private static final Integer DEFAULT_DELIVERY_METHOD = 0;

  @NotBlank
  private String id;
  private Integer qty = DEFAULT_QUANTITY;
  private Integer seq;
  private double flr;
  private String flrcur;
  private Integer exp;
  private Integer dt;
  private Integer dlvy = DEFAULT_DELIVERY_METHOD;
  private Collection<Metric> metric;
  private Collection<Deal> deal;
  @JsonProperty("private")
  private Integer priv;
  @NotNull
  @Valid
  private Spec spec;
  private Map<String, Object> ext;

  public @NotBlank String getId() {
    return this.id;
  }

  public Integer getQty() {
    return this.qty;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public double getFlr() {
    return this.flr;
  }

  public String getFlrcur() {
    return this.flrcur;
  }

  public Integer getExp() {
    return this.exp;
  }

  public Integer getDt() {
    return this.dt;
  }

  public Integer getDlvy() {
    return this.dlvy;
  }

  public Collection<Metric> getMetric() {
    return this.metric;
  }

  public Collection<Deal> getDeal() {
    return this.deal;
  }

  public Integer getPriv() {
    return this.priv;
  }

  public @NotNull @Valid Spec getSpec() {
    return this.spec;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(@NotBlank String id) {
    this.id = id;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public void setFlr(double flr) {
    this.flr = flr;
  }

  public void setFlrcur(String flrcur) {
    this.flrcur = flrcur;
  }

  public void setExp(Integer exp) {
    this.exp = exp;
  }

  public void setDt(Integer dt) {
    this.dt = dt;
  }

  public void setDlvy(Integer dlvy) {
    this.dlvy = dlvy;
  }

  public void setMetric(Collection<Metric> metric) {
    this.metric = metric;
  }

  public void setDeal(Collection<Deal> deal) {
    this.deal = deal;
  }

  public void setPriv(Integer priv) {
    this.priv = priv;
  }

  public void setSpec(@NotNull @Valid Spec spec) {
    this.spec = spec;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
