package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

public class NativeImage {
  private String url;

  private Integer w;

  private Integer h;

  private Map<String, Object> ext;

  public NativeImage() {
  }

  public String getUrl() {
    return this.url;
  }

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeImage)) return false;
    final NativeImage other = (NativeImage) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$url = this.getUrl();
    final Object other$url = other.getUrl();
    if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
    final Object this$w = this.getW();
    final Object other$w = other.getW();
    if (this$w == null ? other$w != null : !this$w.equals(other$w)) return false;
    final Object this$h = this.getH();
    final Object other$h = other.getH();
    if (this$h == null ? other$h != null : !this$h.equals(other$h)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $url = this.getUrl();
    result = result * PRIME + ($url == null ? 43 : $url.hashCode());
    final Object $w = this.getW();
    result = result * PRIME + ($w == null ? 43 : $w.hashCode());
    final Object $h = this.getH();
    result = result * PRIME + ($h == null ? 43 : $h.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeImage;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.NativeImage(url=" + this.getUrl() + ", w=" + this.getW() + ", h=" + this.getH() + ", ext=" + this.getExt() + ")";
  }
}
