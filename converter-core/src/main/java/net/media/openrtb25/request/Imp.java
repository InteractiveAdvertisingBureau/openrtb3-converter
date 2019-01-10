package net.media.openrtb25.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.media.utils.validator.CheckAtLeastOneNotNull;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
@CheckAtLeastOneNotNull(fieldNames = {"video", "banner", "nat", "audio"})
public class Imp {

  public static final Integer DEFAULT_INTERSTITIAL = null;
  public static final int MINIMUM_POS_VALUE = 0;
  public static final int MAXIMUM_POS_VALUE = 7;

  private String id;

  private Video video;

  private Banner banner;

  private Audio audio;

  @JsonProperty("native")
  private Native nat;

  @JsonProperty("tagid")
  private String tagId;

  private String displaymanager;

  private String displaymanagerver;

  private Integer instl = DEFAULT_INTERSTITIAL;

  private double bidfloor;

  private String bidfloorcur;

  private Integer secure;

  private List<String> iframebuster;

  private Pmp pmp;

  private Integer clickbrowser;

  private Integer exp;

  @NotEmpty
  List<Metric> metric;

  private Map<String, Object> ext;

}