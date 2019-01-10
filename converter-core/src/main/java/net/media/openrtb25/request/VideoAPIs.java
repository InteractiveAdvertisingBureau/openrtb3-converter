package net.media.openrtb25.request;

/**
 * Created by rajat.go on 24/06/17.
 */
public enum VideoAPIs {
  VPAID_1_0(1),
  VPAID_2_0(2),
  MRAID_1(3),
  ORMMA(4),
  MRAID_2(5);

  private int api;

  VideoAPIs(int api) {
    this.api = api;
  }

  public int getApi() {
    return api;
  }
}
