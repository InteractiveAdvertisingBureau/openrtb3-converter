package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class Site extends AbstractExtensible<Site.SiteReqExt> {

  private String id;

  private String name;

  private String domain;

  private List<String> cat;

  @JsonIgnore
  private String SLD;

  private List<String> sectioncat;

  private Set<String> pagecat;

  private String page;

  private String ref;

  private String search;

  private Integer mobile;

  private Integer privacypolicy;

  private Publisher publisher;

  private Content content;

  private String keywords;

  private Object ext;

  private transient String dtld;

  private transient String forcedSLD;

  private transient String forcedUrl;

  private transient String forcedUrlDomain;

  private transient String forcedUrlName;

  public String getForcedSLD() {
    return StringUtils.isBlank(forcedSLD) ? SLD : forcedSLD;
  }

  public String getForcedUrl() {
    return StringUtils.isBlank(forcedUrl) ? page : forcedUrl;
  }

  public String getForcedUrlDomain() {
    return StringUtils.isBlank(forcedUrlDomain) ? domain : forcedUrlDomain;
  }

  public String getForcedUrlName() {
    return StringUtils.isBlank(forcedUrlName) ? name : forcedUrlName;
  }

  public Site() {
    setReqExt(new SiteReqExt());
  }

  public static class SiteReqExt extends ReqExt {
  }
}
