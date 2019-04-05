package net.media.enums;

/**
 * Created by vishnu on 9/5/16.
 */
public enum AdType {
  BANNER(1),
  VIDEO(2),
  NATIVE(3),
  AUDIO(4),
  AUDIT(5);

  int value;

  AdType(int value) {
    this.value = value;
  }

  public static AdType getAdType(int value) {
    for (AdType adType : values()) {
      if (adType.getValue() == value)
        return adType;
    }
    throw new IllegalArgumentException("Invalid adtype value '" + value + "' provided");
  }

  public int getValue() {
    return this.value;
  }
}