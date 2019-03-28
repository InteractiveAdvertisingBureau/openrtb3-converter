package net.media.openrtb25.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by vishnu on 30/5/16.
 */
public class SeatBid {

  @NotNull
  @Valid
  private List<Bid> bid = new ArrayList<>();//

  private String seat;//

  private Integer group;

  private Map<String, Object> ext;//

  public SeatBid() {
  }

  public @NotNull @Valid List<Bid> getBid() {
    return this.bid;
  }

  public String getSeat() {
    return this.seat;
  }

  public Integer getGroup() {
    return this.group;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setBid(@NotNull @Valid List<Bid> bid) {
    this.bid = bid;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public void setGroup(Integer group) {
    this.group = group;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof SeatBid)) return false;
    final SeatBid other = (SeatBid) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$bid = this.getBid();
    final Object other$bid = other.getBid();
    if (this$bid == null ? other$bid != null : !this$bid.equals(other$bid)) return false;
    final Object this$seat = this.getSeat();
    final Object other$seat = other.getSeat();
    if (this$seat == null ? other$seat != null : !this$seat.equals(other$seat)) return false;
    final Object this$group = this.getGroup();
    final Object other$group = other.getGroup();
    if (this$group == null ? other$group != null : !this$group.equals(other$group)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $bid = this.getBid();
    result = result * PRIME + ($bid == null ? 43 : $bid.hashCode());
    final Object $seat = this.getSeat();
    result = result * PRIME + ($seat == null ? 43 : $seat.hashCode());
    final Object $group = this.getGroup();
    result = result * PRIME + ($group == null ? 43 : $group.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof SeatBid;
  }

  public String toString() {
    return "net.media.openrtb25.response.SeatBid(bid=" + this.getBid() + ", seat=" + this.getSeat() + ", group=" + this.getGroup() + ", ext=" + this.getExt() + ")";
  }
}
