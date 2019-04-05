
package net.media.openrtb3;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class TitleAsset {

  @NotNull
  private String text;
  private Integer len;
  private Map<String, Object> ext;

  public @NotNull String getText() {
    return this.text;
  }

  public Integer getLen() {
    return this.len;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setText(@NotNull String text) {
    this.text = text;
  }

  public void setLen(Integer len) {
    this.len = len;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}