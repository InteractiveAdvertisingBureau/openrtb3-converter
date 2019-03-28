package net.media.openrtb25.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NativeRequest {

  @JsonProperty("native")
  private NativeRequestBody nativeRequestBody;

  public NativeRequest() {
  }

  public NativeRequestBody getNativeRequestBody() {
    return this.nativeRequestBody;
  }

  public void setNativeRequestBody(NativeRequestBody nativeRequestBody) {
    this.nativeRequestBody = nativeRequestBody;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeRequest)) return false;
    final NativeRequest other = (NativeRequest) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$nativeRequestBody = this.getNativeRequestBody();
    final Object other$nativeRequestBody = other.getNativeRequestBody();
    if (this$nativeRequestBody == null ? other$nativeRequestBody != null : !this$nativeRequestBody.equals(other$nativeRequestBody))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $nativeRequestBody = this.getNativeRequestBody();
    result = result * PRIME + ($nativeRequestBody == null ? 43 : $nativeRequestBody.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeRequest;
  }

  public String toString() {
    return "net.media.openrtb25.request.NativeRequest(nativeRequestBody=" + this.getNativeRequestBody() + ")";
  }
}
