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

import javax.validation.Valid;

public class DisplayPlacement {

  private static final Integer DEFAULT_CLICKTYPE = 1;
  private static final Integer DEFAULT_UNITS = 1;
  private static final Integer DEFAULT_PRIVACY_NOTICE = 0;

  private Integer pos;
  private Integer instl;
  private Integer topframe;
  private Collection<String> ifrbust;
  private Integer clktype = DEFAULT_CLICKTYPE;
  private Integer ampren;
  private Integer ptype;
  private Integer context;
  private Collection<String> mime;
  private Collection<Integer> api;
  private Collection<Integer> ctype;
  private Integer w;
  private Integer h;
  private Integer unit = DEFAULT_UNITS;
  private Integer priv = DEFAULT_PRIVACY_NOTICE;
  private Collection<DisplayFormat> displayfmt;
  @Valid
  private NativeFormat nativefmt;
  @Valid
  private Collection<EventSpec> event;
  private Map<String, Object> ext;

  public Integer getPos() {
    return this.pos;
  }

  public Integer getInstl() {
    return this.instl;
  }

  public Integer getTopframe() {
    return this.topframe;
  }

  public Collection<String> getIfrbust() {
    return this.ifrbust;
  }

  public Integer getClktype() {
    return this.clktype;
  }

  public Integer getAmpren() {
    return this.ampren;
  }

  public Integer getPtype() {
    return this.ptype;
  }

  public Integer getContext() {
    return this.context;
  }

  public Collection<String> getMime() {
    return this.mime;
  }

  public Collection<Integer> getApi() {
    return this.api;
  }

  public Collection<Integer> getCtype() {
    return this.ctype;
  }

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getUnit() {
    return this.unit;
  }

  public Integer getPriv() {
    return this.priv;
  }

  public Collection<DisplayFormat> getDisplayfmt() {
    return this.displayfmt;
  }

  public @Valid NativeFormat getNativefmt() {
    return this.nativefmt;
  }

  public @Valid Collection<EventSpec> getEvent() {
    return this.event;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setPos(Integer pos) {
    this.pos = pos;
  }

  public void setInstl(Integer instl) {
    this.instl = instl;
  }

  public void setTopframe(Integer topframe) {
    this.topframe = topframe;
  }

  public void setIfrbust(Collection<String> ifrbust) {
    this.ifrbust = ifrbust;
  }

  public void setClktype(Integer clktype) {
    this.clktype = clktype;
  }

  public void setAmpren(Integer ampren) {
    this.ampren = ampren;
  }

  public void setPtype(Integer ptype) {
    this.ptype = ptype;
  }

  public void setContext(Integer context) {
    this.context = context;
  }

  public void setMime(Collection<String> mime) {
    this.mime = mime;
  }

  public void setApi(Collection<Integer> api) {
    this.api = api;
  }

  public void setCtype(Collection<Integer> ctype) {
    this.ctype = ctype;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setUnit(Integer unit) {
    this.unit = unit;
  }

  public void setPriv(Integer priv) {
    this.priv = priv;
  }

  public void setDisplayfmt(Collection<DisplayFormat> displayfmt) {
    this.displayfmt = displayfmt;
  }

  public void setNativefmt(@Valid NativeFormat nativefmt) {
    this.nativefmt = nativefmt;
  }

  public void setEvent(@Valid Collection<EventSpec> event) {
    this.event = event;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}