package net.media.openrtb25.request;

/**
 * Created by rajat.go on 21/12/16.
 */
public enum VideoProtocol {
  VAST1_0(1),
  VAST2_0(2),
  VAST3_0(3),
  VAST1_0_WRAPPER(4),
  VAST2_0_WRAPPER(5),
  VAST3_0_WRAPPER(6),
  VAST4_0(7),
  DAAST1_0(8);

  private int protocol;

  VideoProtocol(int protocol) {
    this.protocol = protocol;
  }

  public int getProtocol() {
    return protocol;
  }
}
