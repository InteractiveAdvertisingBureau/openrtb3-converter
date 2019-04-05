package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by shiva.b on 14/12/18.
 */

public class Deal {
  private String id;
  private Double flr;
  private String flrcur;
  private Integer at;
  private Collection<String> wseat;
  private Collection<String> wadomain;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public Double getFlr() {
    return this.flr;
  }

  public String getFlrcur() {
    return this.flrcur;
  }

  public Integer getAt() {
    return this.at;
  }

  public Collection<String> getWseat() {
    return this.wseat;
  }

  public Collection<String> getWadomain() {
    return this.wadomain;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setFlr(Double flr) {
    this.flr = flr;
  }

  public void setFlrcur(String flrcur) {
    this.flrcur = flrcur;
  }

  public void setAt(Integer at) {
    this.at = at;
  }

  public void setWseat(Collection<String> wseat) {
    this.wseat = wseat;
  }

  public void setWadomain(Collection<String> wadomain) {
    this.wadomain = wadomain;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
