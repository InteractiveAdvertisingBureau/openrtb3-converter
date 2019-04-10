package net.media.openrtb3;

import java.util.Map;

public class Source {
  private String tid;
  private Long ts;
  private String ds;
  private String dsmap;
  private String cert;
  private String digest;
  private String pchain;
  private Map<String, Object> ext;

  public String getTid() {
    return this.tid;
  }

  public Long getTs() {
    return this.ts;
  }

  public String getDs() {
    return this.ds;
  }

  public String getDsmap() {
    return this.dsmap;
  }

  public String getCert() {
    return this.cert;
  }

  public String getDigest() {
    return this.digest;
  }

  public String getPchain() {
    return this.pchain;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public void setTs(Long ts) {
    this.ts = ts;
  }

  public void setDs(String ds) {
    this.ds = ds;
  }

  public void setDsmap(String dsmap) {
    this.dsmap = dsmap;
  }

  public void setCert(String cert) {
    this.cert = cert;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public void setPchain(String pchain) {
    this.pchain = pchain;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
