package net.media.openrtb25.request;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NativeVideo {

  @NotEmpty
  private Collection<String> mimes;

  @NotNull
  private Integer minduration;

  @NotNull
  private Integer maxduration;

  @NotEmpty
  private Collection<Integer> protocols;

  private Map<String, Object> ext;

  public NativeVideo() {
  }

  public @NotEmpty Collection<String> getMimes() {
    return this.mimes;
  }

  public @NotNull Integer getMinduration() {
    return this.minduration;
  }

  public @NotNull Integer getMaxduration() {
    return this.maxduration;
  }

  public @NotEmpty Collection<Integer> getProtocols() {
    return this.protocols;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMimes(@NotEmpty Collection<String> mimes) {
    this.mimes = mimes;
  }

  public void setMinduration(@NotNull Integer minduration) {
    this.minduration = minduration;
  }

  public void setMaxduration(@NotNull Integer maxduration) {
    this.maxduration = maxduration;
  }

  public void setProtocols(@NotEmpty Collection<Integer> protocols) {
    this.protocols = protocols;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeVideo)) return false;
    final NativeVideo other = (NativeVideo) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$mimes = this.getMimes();
    final Object other$mimes = other.getMimes();
    if (this$mimes == null ? other$mimes != null : !this$mimes.equals(other$mimes)) return false;
    final Object this$minduration = this.getMinduration();
    final Object other$minduration = other.getMinduration();
    if (this$minduration == null ? other$minduration != null : !this$minduration.equals(other$minduration))
      return false;
    final Object this$maxduration = this.getMaxduration();
    final Object other$maxduration = other.getMaxduration();
    if (this$maxduration == null ? other$maxduration != null : !this$maxduration.equals(other$maxduration))
      return false;
    final Object this$protocols = this.getProtocols();
    final Object other$protocols = other.getProtocols();
    if (this$protocols == null ? other$protocols != null : !this$protocols.equals(other$protocols))
      return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $mimes = this.getMimes();
    result = result * PRIME + ($mimes == null ? 43 : $mimes.hashCode());
    final Object $minduration = this.getMinduration();
    result = result * PRIME + ($minduration == null ? 43 : $minduration.hashCode());
    final Object $maxduration = this.getMaxduration();
    result = result * PRIME + ($maxduration == null ? 43 : $maxduration.hashCode());
    final Object $protocols = this.getProtocols();
    result = result * PRIME + ($protocols == null ? 43 : $protocols.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeVideo;
  }

  public String toString() {
    return "net.media.openrtb25.request.NativeVideo(mimes=" + this.getMimes() + ", minduration=" + this.getMinduration() + ", maxduration=" + this.getMaxduration() + ", protocols=" + this.getProtocols() + ", ext=" + this.getExt() + ")";
  }
}
