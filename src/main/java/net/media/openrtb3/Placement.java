package net.media.openrtb3;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class Placement {
  private String tagid;
  private Integer ssai;
  private String sdk;
  private String sdkver;
  private Integer reward;
  private List<String> wlang;
  private Integer secure;
  private Integer admx;
  private Integer curlx;
  private DisplayPlacement display;
  private VideoPlacement video;
  private AudioPlacement audio;
  private Object ext;
}
