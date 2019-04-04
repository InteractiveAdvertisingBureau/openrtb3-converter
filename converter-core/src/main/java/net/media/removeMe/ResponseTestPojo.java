package net.media.removeMe;

import net.media.openrtb25.response.BidResponse;
import net.media.openrtb3.OpenRTB;

/**
 * Created by shiva.b on 24/12/18.
 */
public class ResponseTestPojo {

  private String description;
  private BidResponse response25;
  private OpenRTB response30;
  private String type;

  public ResponseTestPojo() {
  }

  public String getDescription() {
    return this.description;
  }

  public BidResponse getResponse25() {
    return this.response25;
  }

  public OpenRTB getResponse30() {
    return this.response30;
  }

  public String getType() {
    return this.type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setResponse25(BidResponse response25) {
    this.response25 = response25;
  }

  public void setResponse30(OpenRTB response30) {
    this.response30 = response30;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ResponseTestPojo)) return false;
    final ResponseTestPojo other = (ResponseTestPojo) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$description = this.getDescription();
    final Object other$description = other.getDescription();
    if (this$description == null ? other$description != null : !this$description.equals(other$description))
      return false;
    final Object this$response25 = this.getResponse25();
    final Object other$response25 = other.getResponse25();
    if (this$response25 == null ? other$response25 != null : !this$response25.equals(other$response25))
      return false;
    final Object this$response30 = this.getResponse30();
    final Object other$response30 = other.getResponse30();
    if (this$response30 == null ? other$response30 != null : !this$response30.equals(other$response30))
      return false;
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $description = this.getDescription();
    result = result * PRIME + ($description == null ? 43 : $description.hashCode());
    final Object $response25 = this.getResponse25();
    result = result * PRIME + ($response25 == null ? 43 : $response25.hashCode());
    final Object $response30 = this.getResponse30();
    result = result * PRIME + ($response30 == null ? 43 : $response30.hashCode());
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof ResponseTestPojo;
  }

  public String toString() {
    return "net.media.removeMe.ResponseTestPojo(description=" + this.getDescription() + ", response25=" + this.getResponse25() + ", response30=" + this.getResponse30() + ", type=" + this.getType() + ")";
  }
}
