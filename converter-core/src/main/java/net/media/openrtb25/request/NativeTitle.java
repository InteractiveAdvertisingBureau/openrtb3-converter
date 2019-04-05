package net.media.openrtb25.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class NativeTitle {

  @NotNull
  private Integer len;

  private Map<String, Object> ext;

  public NativeTitle() {
  }

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

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeTitle)) return false;
    final NativeTitle other = (NativeTitle) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$len = this.getLen();
    final Object other$len = other.getLen();
    if (this$len == null ? other$len != null : !this$len.equals(other$len)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $len = this.getLen();
    result = result * PRIME + ($len == null ? 43 : $len.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeTitle;
  }

  public String toString() {
    return "net.media.openrtb25.request.NativeTitle(len=" + this.getLen() + ", ext=" + this.getExt() + ")";
  }
}
