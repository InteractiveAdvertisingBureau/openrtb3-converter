package net.media.openrtb24.request;

/**
 * Created by parag on 4/8/16.
 */

@lombok.Data
public class BidderCookie {
  private static BidderCookie EMPTY_COOKIE = new BidderCookie(null, null);
  String buyerId;
  String cookieSource;

  public BidderCookie(String buyerId, String cookieSource) {
    this.buyerId = buyerId;
    this.cookieSource = cookieSource;
  }

  public static BidderCookie getTombstoneCookie() {
    return EMPTY_COOKIE;
  }
}
