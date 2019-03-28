package net.media.openrtb3;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva.b on 14/12/18.
 */

public class AudioPlacement {
  private Integer delay;
  private Integer skip;
  private Integer skipmin = 0;
  private Integer skipafter = 0;
  private Integer playmethod;
  private Integer playend;
  private Integer feed;
  private Integer nvol;
  private List<String> mime;
  private List<Integer> api;
  private List<Integer> ctype;
  private Integer mindur;
  private Integer maxdur;
  private Integer maxext;
  private Integer minbitr;
  private Integer maxbitr;
  private List<Integer> delivery;
  private Integer maxseq;
  private List<Companion> comp;
  private List<Integer> comptype;
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

  public List<String> getMime() {
    return this.mime;
  }

  public List<Integer> getApi() {
    return this.api;
  }

  public List<Integer> getCtype() {
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

  public List<Integer> getDelivery() {
    return this.delivery;
  }

  public Integer getMaxseq() {
    return this.maxseq;
  }

  public List<Companion> getComp() {
    return this.comp;
  }

  public List<Integer> getComptype() {
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

  public void setMime(List<String> mime) {
    this.mime = mime;
  }

  public void setApi(List<Integer> api) {
    this.api = api;
  }

  public void setCtype(List<Integer> ctype) {
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

  public void setDelivery(List<Integer> delivery) {
    this.delivery = delivery;
  }

  public void setMaxseq(Integer maxseq) {
    this.maxseq = maxseq;
  }

  public void setComp(List<Companion> comp) {
    this.comp = comp;
  }

  public void setComptype(List<Integer> comptype) {
    this.comptype = comptype;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
