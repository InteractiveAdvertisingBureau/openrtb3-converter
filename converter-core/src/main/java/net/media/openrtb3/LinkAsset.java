package net.media.openrtb3;

import net.media.exceptions.OpenRtbConverterException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class LinkAsset {

  @NotNull
  private String url;
  private String urlfb;
  private Collection<String> trkr = null;
  private Map<String,Object> ext;

  public LinkAsset() {
  }

  public @NotNull String getUrl() {
    return this.url;
  }

  public String getUrlfb() {
    return this.urlfb;
  }

  public Collection<String> getTrkr() {
    return this.trkr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setUrl(@NotNull String url) {
    this.url = url;
  }

  public void setUrlfb(String urlfb) {
    this.urlfb = urlfb;
  }

  public void setTrkr(Collection<String> trkr) {
    this.trkr = trkr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof LinkAsset)) return false;
    final LinkAsset other = (LinkAsset) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$url = this.getUrl();
    final Object other$url = other.getUrl();
    if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
    final Object this$urlfb = this.getUrlfb();
    final Object other$urlfb = other.getUrlfb();
    if (this$urlfb == null ? other$urlfb != null : !this$urlfb.equals(other$urlfb)) return false;
    final Object this$trkr = this.getTrkr();
    final Object other$trkr = other.getTrkr();
    if (this$trkr == null ? other$trkr != null : !this$trkr.equals(other$trkr)) return false;
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
    final Object $urlfb = this.getUrlfb();
    result = result * PRIME + ($urlfb == null ? 43 : $urlfb.hashCode());
    final Object $trkr = this.getTrkr();
    result = result * PRIME + ($trkr == null ? 43 : $trkr.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof LinkAsset;
  }

  public String toString() {
    return "net.media.openrtb3.LinkAsset(url=" + this.getUrl() + ", urlfb=" + this.getUrlfb() + ", trkr=" + this.getTrkr() + ", ext=" + this.getExt() + ")";
  }
}