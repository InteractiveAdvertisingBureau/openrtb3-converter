package net.media.openrtb3;

import net.media.utils.validator.CheckAtLeastOneNotNull;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
@CheckAtLeastOneNotNull(fieldNames={"display", "video", "audio"})
public class Placement {
  private String tagid;
  private Integer ssai = 0;
  private String sdk;
  private String sdkver;
  private Integer reward = 0;
  private List<String> wlang;
  private Integer secure;
  private Integer admx;
  private Integer curlx;
  @Valid
  private DisplayPlacement display;
  @Valid
  private VideoPlacement video;
  @Valid
  private AudioPlacement audio;
  private Object ext;
}
