package net.media.openrtb25.response.nativeresponse;


import com.fasterxml.jackson.annotation.JsonProperty;

public class NativeResponse {

  @JsonProperty("native")
  private NativeResponseBody nativeResponseBody;

  public NativeResponse() {
  }

  public NativeResponseBody getNativeResponseBody() {
    return this.nativeResponseBody;
  }

  public void setNativeResponseBody(NativeResponseBody nativeResponseBody) {
    this.nativeResponseBody = nativeResponseBody;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeResponse)) return false;
    final NativeResponse other = (NativeResponse) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$nativeResponseBody = this.getNativeResponseBody();
    final Object other$nativeResponseBody = other.getNativeResponseBody();
    if (this$nativeResponseBody == null ? other$nativeResponseBody != null : !this$nativeResponseBody.equals(other$nativeResponseBody))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $nativeResponseBody = this.getNativeResponseBody();
    result = result * PRIME + ($nativeResponseBody == null ? 43 : $nativeResponseBody.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeResponse;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.NativeResponse(nativeResponseBody=" + this.getNativeResponseBody() + ")";
  }
}
