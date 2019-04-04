package net.media.openrtb25.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */
public class Deal {

  private static final double DEFAULT_BIDFLOOR = 0.0;

  private String id;

  @JsonProperty("bidfloor")
  private double bidFloor = DEFAULT_BIDFLOOR;

  @JsonProperty("bidfloorcur")
  private String bidFloorCur;

  private Integer at;
  private Collection<String> wseat;
  private Collection<String> wadomain;
  private Map<String, Object> ext;

  public Deal() {
  }


  public String getBidFloorCur() {
    return bidFloorCur;
  }

  public void setBidFloorCur(String bidFloorCur) {
    this.bidFloorCur = bidFloorCur;
  }

  public String getId() {
    return this.id;
  }

  public double getBidFloor() {
    return this.bidFloor;
  }

  public Integer getAt() {
    return this.at;
  }

  public Collection<String> getWseat() {
    return this.wseat;
  }

  public Collection<String> getWadomain() {
    return this.wadomain;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setBidFloor(double bidFloor) {
    this.bidFloor = bidFloor;
  }

  public void setAt(Integer at) {
    this.at = at;
  }

  public void setWseat(Collection<String> wseat) {
    this.wseat = wseat;
  }

  public void setWadomain(Collection<String> wadomain) {
    this.wadomain = wadomain;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Deal)) return false;
    final Deal other = (Deal) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    if (Double.compare(this.getBidFloor(), other.getBidFloor()) != 0) return false;
    final Object this$bidFloorCur = this.getBidFloorCur();
    final Object other$bidFloorCur = other.getBidFloorCur();
    if (this$bidFloorCur == null ? other$bidFloorCur != null : !this$bidFloorCur.equals(other$bidFloorCur))
      return false;
    final Object this$at = this.getAt();
    final Object other$at = other.getAt();
    if (this$at == null ? other$at != null : !this$at.equals(other$at)) return false;
    final Object this$wseat = this.getWseat();
    final Object other$wseat = other.getWseat();
    if (this$wseat == null ? other$wseat != null : !this$wseat.equals(other$wseat)) return false;
    final Object this$wadomain = this.getWadomain();
    final Object other$wadomain = other.getWadomain();
    if (this$wadomain == null ? other$wadomain != null : !this$wadomain.equals(other$wadomain))
      return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final long $bidFloor = Double.doubleToLongBits(this.getBidFloor());
    result = result * PRIME + (int) ($bidFloor >>> 32 ^ $bidFloor);
    final Object $bidFloorCur = this.getBidFloorCur();
    result = result * PRIME + ($bidFloorCur == null ? 43 : $bidFloorCur.hashCode());
    final Object $at = this.getAt();
    result = result * PRIME + ($at == null ? 43 : $at.hashCode());
    final Object $wseat = this.getWseat();
    result = result * PRIME + ($wseat == null ? 43 : $wseat.hashCode());
    final Object $wadomain = this.getWadomain();
    result = result * PRIME + ($wadomain == null ? 43 : $wadomain.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Deal;
  }

  public String toString() {
    return "net.media.openrtb25.request.Deal(id=" + this.getId() + ", bidFloor=" + this.getBidFloor() + ", bidFloorCur=" + this.getBidFloorCur() + ", at=" + this.getAt() + ", wseat=" + this.getWseat() + ", wadomain=" + this.getWadomain() + ", ext=" + this.getExt() + ")";
  }
}
