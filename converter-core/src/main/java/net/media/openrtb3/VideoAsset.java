package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.Map;

@CheckExactlyOneNotNull(fieldNames = {"adm", "curl"})
public class VideoAsset {

  private String adm;
  private String curl;
  private Map<String,Object> ext;

  public VideoAsset() {
  }

  public String getAdm() {
    return this.adm;
  }

  public String getCurl() {
    return this.curl;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setAdm(String adm) {
    this.adm = adm;
  }

  public void setCurl(String curl) {
    this.curl = curl;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof VideoAsset)) return false;
    final VideoAsset other = (VideoAsset) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$adm = this.getAdm();
    final Object other$adm = other.getAdm();
    if (this$adm == null ? other$adm != null : !this$adm.equals(other$adm)) return false;
    final Object this$curl = this.getCurl();
    final Object other$curl = other.getCurl();
    if (this$curl == null ? other$curl != null : !this$curl.equals(other$curl)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $adm = this.getAdm();
    result = result * PRIME + ($adm == null ? 43 : $adm.hashCode());
    final Object $curl = this.getCurl();
    result = result * PRIME + ($curl == null ? 43 : $curl.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof VideoAsset;
  }

  public String toString() {
    return "net.media.openrtb3.VideoAsset(adm=" + this.getAdm() + ", curl=" + this.getCurl() + ", ext=" + this.getExt() + ")";
  }
}