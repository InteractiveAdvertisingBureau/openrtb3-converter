package net.media.openrtb3;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

/**
 * Created by shiva.b on 14/12/18.
 */

public class VideoPlacement {
  private Integer ptype;
  private Integer pos;
  private Integer delay;
  private Integer skip;
  private Integer skipmin = 0;
  private Integer skipafter = 0;
  private Integer playmethod;
  private Integer playend;
  private Integer clktype;
  @NotEmpty
  private Set<String> mime;
  private Set<Integer> api;
  private Set<Integer> ctype;
  private Integer w;
  private Integer h;
  private Integer unit = 1;
  private Integer mindur;
  private Integer maxdur;
  private Integer maxext = 0;
  private Integer minbitr;
  private Integer maxbitr;
  private List<Integer> delivery;
  private Integer maxseq;
  private Integer linear;
  private Integer boxing = 1;
  private List<Companion> comp;
  private List<Integer> comptype;
  private Map<String, Object> ext;

  public Integer getPtype() {
    return this.ptype;
  }

  public Integer getPos() {
    return this.pos;
  }

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

  public Integer getClktype() {
    return this.clktype;
  }

  public @NotEmpty Set<String> getMime() {
    return this.mime;
  }

  public Set<Integer> getApi() {
    return this.api;
  }

  public Set<Integer> getCtype() {
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

  public Integer getLinear() {
    return this.linear;
  }

  public Integer getBoxing() {
    return this.boxing;
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

  public void setPtype(Integer ptype) {
    this.ptype = ptype;
  }

  public void setPos(Integer pos) {
    this.pos = pos;
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

  public void setClktype(Integer clktype) {
    this.clktype = clktype;
  }

  public void setMime(@NotEmpty Set<String> mime) {
    this.mime = mime;
  }

  public void setApi(Set<Integer> api) {
    this.api = api;
  }

  public void setCtype(Set<Integer> ctype) {
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

  public void setLinear(Integer linear) {
    this.linear = linear;
  }

  public void setBoxing(Integer boxing) {
    this.boxing = boxing;
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
