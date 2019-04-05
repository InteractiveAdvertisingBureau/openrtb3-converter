package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

public class NativeVideo {
  private String vasttag;
  private Map<String,Object> ext;

  public NativeVideo() {
  }

  public String getVasttag() {
    return this.vasttag;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setVasttag(String vasttag) {
    this.vasttag = vasttag;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeVideo)) return false;
    final NativeVideo other = (NativeVideo) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$vasttag = this.getVasttag();
    final Object other$vasttag = other.getVasttag();
    if (this$vasttag == null ? other$vasttag != null : !this$vasttag.equals(other$vasttag))
      return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $vasttag = this.getVasttag();
    result = result * PRIME + ($vasttag == null ? 43 : $vasttag.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeVideo;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.NativeVideo(vasttag=" + this.getVasttag() + ", ext=" + this.getExt() + ")";
  }
}
