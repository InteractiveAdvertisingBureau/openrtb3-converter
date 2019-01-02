package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

import lombok.Data;

import static java.util.Objects.nonNull;

@Data
public class Imp extends AbstractExtensible<Imp.ImpReqExt> {

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

  List<Metric> metric;

  private Map<String, Object> ext;

  private transient String originalTagId;

  @Data
  public static class ImpReqExt extends ReqExt {
    private String tag_pos;
    private String seller_tag_id;
    private Integer visibility;
    private Double viewability;
    private Double ctr;
  }
}