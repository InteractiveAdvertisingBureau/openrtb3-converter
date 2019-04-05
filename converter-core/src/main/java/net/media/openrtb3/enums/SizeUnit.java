package net.media.openrtb3.enums;

/**
 * Created by rajat.go on 18/12/18.
 */
public enum SizeUnit {
  DIPS(1),
  INCHES(2),
  CENTIMETERS(3);

  public Integer val;

  SizeUnit(Integer val) {
    this.val = val;
  }
}
