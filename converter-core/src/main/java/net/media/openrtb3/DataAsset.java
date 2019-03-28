package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by rajat.go on 14/12/18.
 */
public class DataAsset {
    @NotNull
    private List<String> value = null;
    private Integer len;
    private Integer type;
    private Map<String,Object> ext;

  public DataAsset() {
  }

  public @NotNull List<String> getValue() {
    return this.value;
  }

  public Integer getLen() {
    return this.len;
  }

  public Integer getType() {
    return this.type;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setValue(@NotNull List<String> value) {
    this.value = value;
  }

  public void setLen(Integer len) {
    this.len = len;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof DataAsset)) return false;
    final DataAsset other = (DataAsset) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$value = this.getValue();
    final Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
    final Object this$len = this.getLen();
    final Object other$len = other.getLen();
    if (this$len == null ? other$len != null : !this$len.equals(other$len)) return false;
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $len = this.getLen();
    result = result * PRIME + ($len == null ? 43 : $len.hashCode());
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof DataAsset;
  }

  public String toString() {
    return "net.media.openrtb3.DataAsset(value=" + this.getValue() + ", len=" + this.getLen() + ", type=" + this.getType() + ", ext=" + this.getExt() + ")";
  }
}
