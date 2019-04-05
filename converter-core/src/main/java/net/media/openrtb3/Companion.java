package net.media.openrtb3;

import java.util.Map;

/**
 * Created by shiva.b on 14/12/18.
 */

public class Companion {
  private String id;
  private Integer vcm;
  private DisplayPlacement display;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public Integer getVcm() {
    return this.vcm;
  }

  public DisplayPlacement getDisplay() {
    return this.display;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setVcm(Integer vcm) {
    this.vcm = vcm;
  }

  public void setDisplay(DisplayPlacement display) {
    this.display = display;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
