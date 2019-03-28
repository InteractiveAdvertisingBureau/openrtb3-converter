package net.media.openrtb25.request;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class Video {

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

  @NotEmpty
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

  private Integer maxextended = 0;

  private Integer minbitrate;

  private Integer maxbitrate;

  private Integer boxingallowed = DEFAULT_BOXING_ALLOWED;

  private List<Integer> playbackmethod;

  private Integer playbackend;

  private List<Integer> delivery;

  private Integer pos;

  private List<Banner> companionad;

  private Set<Integer> api = DEFAULT_APIS;

  private List<Integer> companiontype;

  private Map<String, Object> ext;

  public @NotEmpty Set<String> getMimes() {
    return this.mimes;
  }

  public Integer getMinduration() {
    return this.minduration;
  }

  public Integer getMaxduration() {
    return this.maxduration;
  }

  public Set<Integer> getProtocols() {
    return this.protocols;
  }

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getStartdelay() {
    return this.startdelay;
  }

  public Integer getLinearity() {
    return this.linearity;
  }

  public Integer getSkip() {
    return this.skip;
  }

  public Integer getPlacement() {
    return this.placement;
  }

  public Integer getSkipmin() {
    return this.skipmin;
  }

  public Integer getSkipafter() {
    return this.skipafter;
  }

  public Integer getSequence() {
    return this.sequence;
  }

  public Set<Integer> getBattr() {
    return this.battr;
  }

  public Integer getMaxextended() {
    return this.maxextended;
  }

  public Integer getMinbitrate() {
    return this.minbitrate;
  }

  public Integer getMaxbitrate() {
    return this.maxbitrate;
  }

  public Integer getBoxingallowed() {
    return this.boxingallowed;
  }

  public List<Integer> getPlaybackmethod() {
    return this.playbackmethod;
  }

  public Integer getPlaybackend() {
    return this.playbackend;
  }

  public List<Integer> getDelivery() {
    return this.delivery;
  }

  public Integer getPos() {
    return this.pos;
  }

  public List<Banner> getCompanionad() {
    return this.companionad;
  }

  public Set<Integer> getApi() {
    return this.api;
  }

  public List<Integer> getCompaniontype() {
    return this.companiontype;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMimes(@NotEmpty Set<String> mimes) {
    this.mimes = mimes;
  }

  public void setMinduration(Integer minduration) {
    this.minduration = minduration;
  }

  public void setMaxduration(Integer maxduration) {
    this.maxduration = maxduration;
  }

  public void setProtocols(Set<Integer> protocols) {
    this.protocols = protocols;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setStartdelay(Integer startdelay) {
    this.startdelay = startdelay;
  }

  public void setLinearity(Integer linearity) {
    this.linearity = linearity;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public void setPlacement(Integer placement) {
    this.placement = placement;
  }

  public void setSkipmin(Integer skipmin) {
    this.skipmin = skipmin;
  }

  public void setSkipafter(Integer skipafter) {
    this.skipafter = skipafter;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public void setBattr(Set<Integer> battr) {
    this.battr = battr;
  }

  public void setMaxextended(Integer maxextended) {
    this.maxextended = maxextended;
  }

  public void setMinbitrate(Integer minbitrate) {
    this.minbitrate = minbitrate;
  }

  public void setMaxbitrate(Integer maxbitrate) {
    this.maxbitrate = maxbitrate;
  }

  public void setBoxingallowed(Integer boxingallowed) {
    this.boxingallowed = boxingallowed;
  }

  public void setPlaybackmethod(List<Integer> playbackmethod) {
    this.playbackmethod = playbackmethod;
  }

  public void setPlaybackend(Integer playbackend) {
    this.playbackend = playbackend;
  }

  public void setDelivery(List<Integer> delivery) {
    this.delivery = delivery;
  }

  public void setPos(Integer pos) {
    this.pos = pos;
  }

  public void setCompanionad(List<Banner> companionad) {
    this.companionad = companionad;
  }

  public void setApi(Set<Integer> api) {
    this.api = api;
  }

  public void setCompaniontype(List<Integer> companiontype) {
    this.companiontype = companiontype;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
