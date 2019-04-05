package net.media.api.models;

import net.media.config.Config;
import net.media.openrtb3.OpenRTB;

public class RequestResponse3xPayload {
  private OpenRTB openRTB;
  private Config config;

  public RequestResponse3xPayload() {
  }

  public OpenRTB getOpenRTB() {
    return this.openRTB;
  }

  public Config getConfig() {
    return this.config;
  }

  public void setOpenRTB(OpenRTB openRTB) {
    this.openRTB = openRTB;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof RequestResponse3xPayload)) return false;
    final RequestResponse3xPayload other = (RequestResponse3xPayload) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$openRTB = this.getOpenRTB();
    final Object other$openRTB = other.getOpenRTB();
    if (this$openRTB == null ? other$openRTB != null : !this$openRTB.equals(other$openRTB))
      return false;
    final Object this$config = this.getConfig();
    final Object other$config = other.getConfig();
    if (this$config == null ? other$config != null : !this$config.equals(other$config))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $openRTB = this.getOpenRTB();
    result = result * PRIME + ($openRTB == null ? 43 : $openRTB.hashCode());
    final Object $config = this.getConfig();
    result = result * PRIME + ($config == null ? 43 : $config.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof RequestResponse3xPayload;
  }

  public String toString() {
    return "net.media.api.models.RequestResponse3xPayload(openRTB=" + this.getOpenRTB() + ", config=" + this.getConfig() + ")";
  }
}
