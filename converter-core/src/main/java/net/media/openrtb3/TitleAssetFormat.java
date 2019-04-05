package net.media.openrtb3;

import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by shiva.b on 14/12/18.
 */

public class TitleAssetFormat {
  @NotNull
  private Integer len;
  private Map<String, Object> ext;

  public @NotNull Integer getLen() {
    return this.len;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setLen(@NotNull Integer len) {
    this.len = len;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
