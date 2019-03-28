package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

public class NativeTitle {

  private String text;

  private Map<String, Object> ext;

  public NativeTitle() {
  }

  public String getText() {
    return this.text;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeTitle)) return false;
    final NativeTitle other = (NativeTitle) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$text = this.getText();
    final Object other$text = other.getText();
    if (this$text == null ? other$text != null : !this$text.equals(other$text)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $text = this.getText();
    result = result * PRIME + ($text == null ? 43 : $text.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeTitle;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.NativeTitle(text=" + this.getText() + ", ext=" + this.getExt() + ")";
  }
}
