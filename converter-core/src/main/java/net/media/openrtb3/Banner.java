package net.media.openrtb3;


import net.media.exceptions.OpenRtbConverterException;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Banner {

  @NotNull
  private String img;
  @Valid
  private LinkAsset link;
  private Map<String,Object> ext;

  public Banner() {
  }

  public @NotNull String getImg() {
    return this.img;
  }

  public @Valid LinkAsset getLink() {
    return this.link;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setImg(@NotNull String img) {
    this.img = img;
  }

  public void setLink(@Valid LinkAsset link) {
    this.link = link;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Banner)) return false;
    final Banner other = (Banner) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$img = this.getImg();
    final Object other$img = other.getImg();
    if (this$img == null ? other$img != null : !this$img.equals(other$img)) return false;
    final Object this$link = this.getLink();
    final Object other$link = other.getLink();
    if (this$link == null ? other$link != null : !this$link.equals(other$link)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $img = this.getImg();
    result = result * PRIME + ($img == null ? 43 : $img.hashCode());
    final Object $link = this.getLink();
    result = result * PRIME + ($link == null ? 43 : $link.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Banner;
  }

  public String toString() {
    return "net.media.openrtb3.Banner(img=" + this.getImg() + ", link=" + this.getLink() + ", ext=" + this.getExt() + ")";
  }
}