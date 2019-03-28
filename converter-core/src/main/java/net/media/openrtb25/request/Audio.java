package net.media.openrtb25.request;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

/**
 * Created by rajat.go on 19/12/18.
 */

public class Audio {

  @NotEmpty
  private List<String> mimes;

  private Integer minduration;

  private Integer maxduration;

  private List<Integer> protocols;

  private Integer startdelay;

  private Integer sequence;

  private List<Integer> battr;

  private Integer maxextended;

  private Integer minbitrate;

  private Integer maxbitrate;

  private List<Integer> delivery;

  private List<Banner> companionad;

  private List<Integer> api;

  private List<Integer> companiontype;

  private Integer maxseq;

  private Integer feed;

  private Integer stitched;

  private Integer nvol;

  private Map<String, Object> ext;

  public @NotEmpty List<String> getMimes() {
    return this.mimes;
  }

  public Integer getMinduration() {
    return this.minduration;
  }

  public Integer getMaxduration() {
    return this.maxduration;
  }

  public List<Integer> getProtocols() {
    return this.protocols;
  }

  public Integer getStartdelay() {
    return this.startdelay;
  }

  public Integer getSequence() {
    return this.sequence;
  }

  public List<Integer> getBattr() {
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

  public List<Integer> getDelivery() {
    return this.delivery;
  }

  public List<Banner> getCompanionad() {
    return this.companionad;
  }

  public List<Integer> getApi() {
    return this.api;
  }

  public List<Integer> getCompaniontype() {
    return this.companiontype;
  }

  public Integer getMaxseq() {
    return this.maxseq;
  }

  public Integer getFeed() {
    return this.feed;
  }

  public Integer getStitched() {
    return this.stitched;
  }

  public Integer getNvol() {
    return this.nvol;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMimes(@NotEmpty List<String> mimes) {
    this.mimes = mimes;
  }

  public void setMinduration(Integer minduration) {
    this.minduration = minduration;
  }

  public void setMaxduration(Integer maxduration) {
    this.maxduration = maxduration;
  }

  public void setProtocols(List<Integer> protocols) {
    this.protocols = protocols;
  }

  public void setStartdelay(Integer startdelay) {
    this.startdelay = startdelay;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public void setBattr(List<Integer> battr) {
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

  public void setDelivery(List<Integer> delivery) {
    this.delivery = delivery;
  }

  public void setCompanionad(List<Banner> companionad) {
    this.companionad = companionad;
  }

  public void setApi(List<Integer> api) {
    this.api = api;
  }

  public void setCompaniontype(List<Integer> companiontype) {
    this.companiontype = companiontype;
  }

  public void setMaxseq(Integer maxseq) {
    this.maxseq = maxseq;
  }

  public void setFeed(Integer feed) {
    this.feed = feed;
  }

  public void setStitched(Integer stitched) {
    this.stitched = stitched;
  }

  public void setNvol(Integer nvol) {
    this.nvol = nvol;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
