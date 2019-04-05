package net.media.api.models;

import net.media.config.Config;
import net.media.openrtb25.response.BidResponse;

public class Response2xPayload {
  private BidResponse response;
  private Config config;

  public Response2xPayload() {
  }

  public BidResponse getResponse() {
    return this.response;
  }

  public Config getConfig() {
    return this.config;
  }

  public void setResponse(BidResponse response) {
    this.response = response;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Response2xPayload)) return false;
    final Response2xPayload other = (Response2xPayload) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$response = this.getResponse();
    final Object other$response = other.getResponse();
    if (this$response == null ? other$response != null : !this$response.equals(other$response))
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
    final Object $response = this.getResponse();
    result = result * PRIME + ($response == null ? 43 : $response.hashCode());
    final Object $config = this.getConfig();
    result = result * PRIME + ($config == null ? 43 : $config.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Response2xPayload;
  }

  public String toString() {
    return "net.media.api.models.Response2xPayload(response=" + this.getResponse() + ", config=" + this.getConfig() + ")";
  }
}
