package net.media.openrtb3;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Restrictions {

  private Set<String> bcat;
  private Integer cattax = 2;
  private Set<String> badv;
  private List<String> bapp;
  private Set<Integer> battr;
  private Map<String, Object> ext;

  public Set<String> getBcat() {
    return this.bcat;
  }

  public Integer getCattax() {
    return this.cattax;
  }

  public Set<String> getBadv() {
    return this.badv;
  }

  public List<String> getBapp() {
    return this.bapp;
  }

  public Set<Integer> getBattr() {
    return this.battr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setBcat(Set<String> bcat) {
    this.bcat = bcat;
  }

  public void setCattax(Integer cattax) {
    this.cattax = cattax;
  }

  public void setBadv(Set<String> badv) {
    this.badv = badv;
  }

  public void setBapp(List<String> bapp) {
    this.bapp = bapp;
  }

  public void setBattr(Set<Integer> battr) {
    this.battr = battr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
