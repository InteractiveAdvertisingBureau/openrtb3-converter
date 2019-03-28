package net.media.openrtb3;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Site extends DistributionChannel {

  private String domain;
  private List<String> cat;
  private List<String> sectcat;
  private Set<String> pagecat;
  private Integer cattax;
  private Integer privpolicy;
  private String keywords;
  private String page;
  private String ref;
  private String search;
  private Integer mobile;
  private Integer amp;
  private Map<String, Object> ext;

  public String getDomain() {
    return this.domain;
  }

  public List<String> getCat() {
    return this.cat;
  }

  public List<String> getSectcat() {
    return this.sectcat;
  }

  public Set<String> getPagecat() {
    return this.pagecat;
  }

  public Integer getCattax() {
    return this.cattax;
  }

  public Integer getPrivpolicy() {
    return this.privpolicy;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public String getPage() {
    return this.page;
  }

  public String getRef() {
    return this.ref;
  }

  public String getSearch() {
    return this.search;
  }

  public Integer getMobile() {
    return this.mobile;
  }

  public Integer getAmp() {
    return this.amp;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public void setCat(List<String> cat) {
    this.cat = cat;
  }

  public void setSectcat(List<String> sectcat) {
    this.sectcat = sectcat;
  }

  public void setPagecat(Set<String> pagecat) {
    this.pagecat = pagecat;
  }

  public void setCattax(Integer cattax) {
    this.cattax = cattax;
  }

  public void setPrivpolicy(Integer privpolicy) {
    this.privpolicy = privpolicy;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public void setMobile(Integer mobile) {
    this.mobile = mobile;
  }

  public void setAmp(Integer amp) {
    this.amp = amp;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
