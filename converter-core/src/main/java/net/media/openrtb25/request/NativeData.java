package net.media.openrtb25.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class NativeData {

  @NotNull
  private Integer type;

  private Integer len;

  private Map<String, Object> ext;

  public NativeData() {
  }

  public @NotNull Integer getType() {
    return this.type;
  }

  public Integer getLen() {
    return this.len;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setType(@NotNull Integer type) {
    this.type = type;
  }

  public void setLen(Integer len) {
    this.len = len;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeData)) return false;
    final NativeData other = (NativeData) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
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
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $len = this.getLen();
    result = result * PRIME + ($len == null ? 43 : $len.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeData;
  }

  public String toString() {
    return "net.media.openrtb25.request.NativeData(type=" + this.getType() + ", len=" + this.getLen() + ", ext=" + this.getExt() + ")";
  }
}
