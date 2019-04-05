package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

public class NativeData {
  private String label;

  private String value;

  private Map<String, Object> ext;

  public NativeData() {
  }

  public String getLabel() {
    return this.label;
  }

  public String getValue() {
    return this.value;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeData)) return false;
    final NativeData other = (NativeData) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$label = this.getLabel();
    final Object other$label = other.getLabel();
    if (this$label == null ? other$label != null : !this$label.equals(other$label)) return false;
    final Object this$value = this.getValue();
    final Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $label = this.getLabel();
    result = result * PRIME + ($label == null ? 43 : $label.hashCode());
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeData;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.NativeData(label=" + this.getLabel() + ", value=" + this.getValue() + ", ext=" + this.getExt() + ")";
  }
}
