package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

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

  private Object ext;

  private transient String originalTagId;

  public Imp() {
    setReqExt(new ImpReqExt());
  }

  public Imp(Imp imp) {
    this.id = imp.id;
    this.video = nonNull(imp.video) ? new Video(imp.video) : null;
    this.banner = nonNull(imp.banner) ? new Banner(imp.banner) : null;
    this.nat = imp.nat;
    this.tagId = imp.tagId;
    this.displaymanager = imp.displaymanager;
    this.displaymanagerver = imp.displaymanagerver;
    this.instl = imp.instl;
    this.bidfloor = imp.bidfloor;
    this.bidfloorcur = imp.bidfloorcur;
    this.secure = imp.secure;
    this.iframebuster = imp.iframebuster;
    this.pmp = imp.pmp;
    this.clickbrowser = imp.clickbrowser;
    this.exp = imp.exp;
    this.ext = imp.ext;
    setReqExt(imp.getReqExt());
  }

  @Data
  public static class ImpReqExt extends ReqExt {
    private String tag_pos;
    private String seller_tag_id;
    private Integer visibility;
    private Double viewability;
    private Double ctr;
  }
}