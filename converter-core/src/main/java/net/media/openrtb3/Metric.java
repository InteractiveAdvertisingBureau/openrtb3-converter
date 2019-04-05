package net.media.openrtb3;

import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by shiva.b on 14/12/18.
 */

public class Metric {
  private String type;
  @NotNull
  private Double value;
  private String vendor;
  private Map<String, Object> ext;

  public String getType() {
    return this.type;
  }

  public @NotNull Double getValue() {
    return this.value;
  }

  public String getVendor() {
    return this.vendor;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setValue(@NotNull Double value) {
    this.value = value;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
