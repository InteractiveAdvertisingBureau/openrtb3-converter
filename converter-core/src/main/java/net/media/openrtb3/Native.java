package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

public class Native {


  @Valid
  private LinkAsset link;
  @Valid
  private List<Asset> asset = null;
  private Map<String,Object> ext;

  public Native() {
  }

  public @Valid LinkAsset getLink() {
    return this.link;
  }

  public @Valid List<Asset> getAsset() {
    return this.asset;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setLink(@Valid LinkAsset link) {
    this.link = link;
  }

  public void setAsset(@Valid List<Asset> asset) {
    this.asset = asset;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Native)) return false;
    final Native other = (Native) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$link = this.getLink();
    final Object other$link = other.getLink();
    if (this$link == null ? other$link != null : !this$link.equals(other$link)) return false;
    final Object this$asset = this.getAsset();
    final Object other$asset = other.getAsset();
    if (this$asset == null ? other$asset != null : !this$asset.equals(other$asset)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $link = this.getLink();
    result = result * PRIME + ($link == null ? 43 : $link.hashCode());
    final Object $asset = this.getAsset();
    result = result * PRIME + ($asset == null ? 43 : $asset.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Native;
  }

  public String toString() {
    return "net.media.openrtb3.Native(link=" + this.getLink() + ", asset=" + this.getAsset() + ", ext=" + this.getExt() + ")";
  }
}