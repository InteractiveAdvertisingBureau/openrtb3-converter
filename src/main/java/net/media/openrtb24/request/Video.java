package net.media.openrtb24.request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@lombok.Data
public class Video extends ReqExt {

  public static final Integer DEFAULT_MINDURATION = null;

  public static final Integer DEFAULT_MAXDURATION = null;
  public static final Integer DEFAULT_SKIP_MIN = null;
  public static final Integer DEFAULT_SKIP_AFTER = null;
  public static final Integer DEFAULT_BOXING_ALLOWED = 1;
  private static final int DEFAULT_MIME_SIZE = 6;
  public static final Set<String> DEFAULT_MIME_TYPES = new HashSet<>
    (DEFAULT_MIME_SIZE);
  private static final int DEFAULT_BLOCKED_DOMAIN_SIZE = 5;
  public static final Set<String> DEFAULT_BLOCKED_DOMAINS = new
    HashSet<>(DEFAULT_BLOCKED_DOMAIN_SIZE);
  private static final int DEFAULT_PROTOCOLS_SIZE = 2;
  public static final Set<Integer> DEFAULT_PROTOCOLS = new HashSet<>
    (DEFAULT_PROTOCOLS_SIZE);
  private static final int DEFAULT_API_SIZE = 2;
  public static final Set<Integer> DEFAULT_APIS = new HashSet<>(DEFAULT_API_SIZE);

  static {
    DEFAULT_MIME_TYPES.add("video/x-flv");
    DEFAULT_MIME_TYPES.add("video/mp4");
    DEFAULT_MIME_TYPES.add("application/x-shockwave-flash");
    DEFAULT_MIME_TYPES.add("application/javascript");
    DEFAULT_MIME_TYPES.add("video/webm");

    DEFAULT_BLOCKED_DOMAINS.add("trackmytraffic.biz");
    DEFAULT_BLOCKED_DOMAINS.add("talk915.pw");
    DEFAULT_BLOCKED_DOMAINS.add("evangmedia.com");
    DEFAULT_BLOCKED_DOMAINS.add("shangjiamedia.com");
    DEFAULT_BLOCKED_DOMAINS.add("ad.com");

    DEFAULT_PROTOCOLS.add(VideoProtocol.VAST2_0.getProtocol());
    DEFAULT_PROTOCOLS.add(VideoProtocol.VAST2_0_WRAPPER.getProtocol());
    DEFAULT_PROTOCOLS.add(VideoProtocol.VAST3_0.getProtocol());
    DEFAULT_PROTOCOLS.add(VideoProtocol.VAST3_0_WRAPPER.getProtocol());

    DEFAULT_APIS.add(VideoAPIs.VPAID_1_0.getApi());
    DEFAULT_APIS.add(VideoAPIs.VPAID_2_0.getApi());
  }

  private Set<String> mimes = DEFAULT_MIME_TYPES;

  private Integer minduration = DEFAULT_MINDURATION;

  private Integer maxduration = DEFAULT_MAXDURATION;

  private Set<Integer> protocols = DEFAULT_PROTOCOLS;

  private Integer w;

  private Integer h;

  private Integer startdelay;

  private Integer linearity;

  private Integer skip;

  private Integer placement;

  private Integer skipmin = DEFAULT_SKIP_MIN;

  private Integer skipafter = DEFAULT_SKIP_AFTER;

  private Integer sequence;

  private Set<Integer> battr;

  private Integer maxExtended;

  private Integer minbitrate;

  private Integer maxbitrate;

  private Integer boxingallowed = DEFAULT_BOXING_ALLOWED;

  private Set<Integer> playbackmethod;

  private List<Integer> delivery;

  private Integer pos;

  private List<Banner> companionad;

  private Set<Integer> api = DEFAULT_APIS;

  private List<Integer> companiontype;

  private VideoExt ext;

  public Video() {
  }

  public Video(Video video) {
    this.mimes = video.mimes;
    this.minduration = video.minduration;
    this.maxduration = video.maxduration;
    this.protocols = video.protocols;
    this.w = video.w;
    this.h = video.h;
    this.startdelay = video.startdelay;
    this.linearity = video.linearity;
    this.skip = video.skip;
    this.placement = video.placement;
    this.skipmin = video.skipmin;
    this.skipafter = video.skipafter;
    this.sequence = video.sequence;
    this.battr = video.battr;
    this.maxExtended = video.maxExtended;
    this.minbitrate = video.minbitrate;
    this.maxbitrate = video.maxbitrate;
    this.boxingallowed = video.boxingallowed;
    this.playbackmethod = video.playbackmethod;
    this.delivery = video.delivery;
    this.pos = video.pos;
    this.companionad = video.companionad;
    this.api = video.api;
    this.ext = video.ext;
  }

  @lombok.Data
  public static class VideoExt extends ReqExt {

    private Boolean incentivized;

    public Boolean isIncentivized() {
      return incentivized;
    }

    public void setIncentivized(Boolean incentivized) {
      this.incentivized = incentivized;
    }

  }
}
