package net.media.openrtb25.request;

import java.util.Collection;
import java.util.List;

/**
 * Created by vishnu on 6/5/16.
 */
public class Pmp {

  public static final Integer DEFAULT_PRIVATE_AUCTION = 0;
  private Integer private_auction = DEFAULT_PRIVATE_AUCTION;

  private Collection<Deal> deals;

  private Ext ext;

  public Pmp() {
  }

  public Integer getPrivate_auction() {
    return this.private_auction;
  }

  public Collection<Deal> getDeals() {
    return this.deals;
  }

  public Ext getExt() {
    return this.ext;
  }

  public void setPrivate_auction(Integer private_auction) {
    this.private_auction = private_auction;
  }

  public void setDeals(Collection<Deal> deals) {
    this.deals = deals;
  }

  public void setExt(Ext ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Pmp)) return false;
    final Pmp other = (Pmp) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$private_auction = this.getPrivate_auction();
    final Object other$private_auction = other.getPrivate_auction();
    if (this$private_auction == null ? other$private_auction != null : !this$private_auction.equals(other$private_auction))
      return false;
    final Object this$deals = this.getDeals();
    final Object other$deals = other.getDeals();
    if (this$deals == null ? other$deals != null : !this$deals.equals(other$deals)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $private_auction = this.getPrivate_auction();
    result = result * PRIME + ($private_auction == null ? 43 : $private_auction.hashCode());
    final Object $deals = this.getDeals();
    result = result * PRIME + ($deals == null ? 43 : $deals.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Pmp;
  }

  public String toString() {
    return "net.media.openrtb25.request.Pmp(private_auction=" + this.getPrivate_auction() + ", deals=" + this.getDeals() + ", ext=" + this.getExt() + ")";
  }
}
