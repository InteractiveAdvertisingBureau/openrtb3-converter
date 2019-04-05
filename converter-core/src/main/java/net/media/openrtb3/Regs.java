package net.media.openrtb3;

import java.util.Map;

public class Regs {

  private Integer coppa;
  private Integer gdpr;
  private Map<String, Object> ext;

  public Integer getCoppa() {
    return this.coppa;
  }

  public Integer getGdpr() {
    return this.gdpr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setCoppa(Integer coppa) {
    this.coppa = coppa;
  }

  public void setGdpr(Integer gdpr) {
    this.gdpr = gdpr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
