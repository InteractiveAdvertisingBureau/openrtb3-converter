package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

public class DisplayPlacement {
  private Integer pos;
  private Integer instl = 0;
  private Integer topframe;
  private Collection<String> ifrbust;
  private Integer clktype = 1;
  private Integer ampren;
  private Integer ptype;
  private Integer context;
  private Collection<String> mime;
  private Collection<Integer> api;
  private Collection<Integer> ctype;
  private Integer w;
  private Integer h;
  private Integer unit = 1;
  private Integer priv = 0;
  private Collection<DisplayFormat> displayfmt;
  @Valid
  private NativeFormat nativefmt;
  @Valid
  private EventSpec event;
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

  public @Valid EventSpec getEvent() {
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

  public void setEvent(@Valid EventSpec event) {
    this.event = event;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}