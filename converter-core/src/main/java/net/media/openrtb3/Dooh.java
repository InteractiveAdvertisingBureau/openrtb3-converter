package net.media.openrtb3;

import java.util.Map;

public class Dooh extends DistributionChannel {

  private Integer venue;
  private Integer fixed;
  private Integer etime;
  private Integer dpi;
  private Map<String, Object> ext;

  public Integer getVenue() {
    return this.venue;
  }

  public Integer getFixed() {
    return this.fixed;
  }

  public Integer getEtime() {
    return this.etime;
  }

  public Integer getDpi() {
    return this.dpi;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setVenue(Integer venue) {
    this.venue = venue;
  }

  public void setFixed(Integer fixed) {
    this.fixed = fixed;
  }

  public void setEtime(Integer etime) {
    this.etime = etime;
  }

  public void setDpi(Integer dpi) {
    this.dpi = dpi;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
