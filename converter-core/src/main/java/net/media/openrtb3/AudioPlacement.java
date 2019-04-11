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

public class AudioPlacement {

  private static Integer DEFAULT_SKIPMIN = 0;
  private static Integer DEFAULT_SKIPAFTER = 0;

  private Integer delay;
  private Integer skip;
  private Integer skipmin = DEFAULT_SKIPMIN;
  private Integer skipafter = DEFAULT_SKIPAFTER;
  private Integer playmethod;
  private Integer playend;
  private Integer feed;
  private Integer nvol;
  private Collection<String> mime;
  private Collection<Integer> api;
  private Collection<Integer> ctype;
  private Integer mindur;
  private Integer maxdur;
  private Integer maxext;
  private Integer minbitr;
  private Integer maxbitr;
  private Collection<Integer> delivery;
  private Integer maxseq;
  private Collection<Companion> comp;
  private Collection<Integer> comptype;
  private Map<String, Object> ext;

  public Integer getDelay() {
    return this.delay;
  }

  public Integer getSkip() {
    return this.skip;
  }

  public Integer getSkipmin() {
    return this.skipmin;
  }

  public Integer getSkipafter() {
    return this.skipafter;
  }

  public Integer getPlaymethod() {
    return this.playmethod;
  }

  public Integer getPlayend() {
    return this.playend;
  }

  public Integer getFeed() {
    return this.feed;
  }

  public Integer getNvol() {
    return this.nvol;
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

  public Integer getMindur() {
    return this.mindur;
  }

  public Integer getMaxdur() {
    return this.maxdur;
  }

  public Integer getMaxext() {
    return this.maxext;
  }

  public Integer getMinbitr() {
    return this.minbitr;
  }

  public Integer getMaxbitr() {
    return this.maxbitr;
  }

  public Collection<Integer> getDelivery() {
    return this.delivery;
  }

  public Integer getMaxseq() {
    return this.maxseq;
  }

  public Collection<Companion> getComp() {
    return this.comp;
  }

  public Collection<Integer> getComptype() {
    return this.comptype;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setDelay(Integer delay) {
    this.delay = delay;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public void setSkipmin(Integer skipmin) {
    this.skipmin = skipmin;
  }

  public void setSkipafter(Integer skipafter) {
    this.skipafter = skipafter;
  }

  public void setPlaymethod(Integer playmethod) {
    this.playmethod = playmethod;
  }

  public void setPlayend(Integer playend) {
    this.playend = playend;
  }

  public void setFeed(Integer feed) {
    this.feed = feed;
  }

  public void setNvol(Integer nvol) {
    this.nvol = nvol;
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

  public void setMindur(Integer mindur) {
    this.mindur = mindur;
  }

  public void setMaxdur(Integer maxdur) {
    this.maxdur = maxdur;
  }

  public void setMaxext(Integer maxext) {
    this.maxext = maxext;
  }

  public void setMinbitr(Integer minbitr) {
    this.minbitr = minbitr;
  }

  public void setMaxbitr(Integer maxbitr) {
    this.maxbitr = maxbitr;
  }

  public void setDelivery(Collection<Integer> delivery) {
    this.delivery = delivery;
  }

  public void setMaxseq(Integer maxseq) {
    this.maxseq = maxseq;
  }

  public void setComp(Collection<Companion> comp) {
    this.comp = comp;
  }

  public void setComptype(Collection<Integer> comptype) {
    this.comptype = comptype;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
