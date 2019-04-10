package net.media.openrtb3;

/**
 * Created by shiva.b on 10/04/19.
 */
public class OpenRTBWrapper3_X {

  private OpenRTB3_X openrtb;

  public OpenRTBWrapper3_X() {
  }

  public OpenRTB3_X getOpenrtb() {
    return this.openrtb;
  }

  public void setOpenrtb(OpenRTB3_X openrtb) {
    this.openrtb = openrtb;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof OpenRTBWrapper3_X)) return false;
    final OpenRTBWrapper3_X other = (OpenRTBWrapper3_X) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$openrtb = this.getOpenrtb();
    final Object other$openrtb = other.getOpenrtb();
    if (this$openrtb == null ? other$openrtb != null : !this$openrtb.equals(other$openrtb))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $openrtb = this.getOpenrtb();
    result = result * PRIME + ($openrtb == null ? 43 : $openrtb.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof OpenRTBWrapper3_X;
  }

  public String toString() {
    return "net.media.openrtb3.OpenRTBWrapper3_X(openrtb=" + this.getOpenrtb() + ")";
  }
}
