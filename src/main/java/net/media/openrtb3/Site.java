package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Site extends DistributionChannel {

  private String domain;
  private List<String> cat;
  private String[] sectcat;
  private String[] pagecat;
  private Integer cattax;
  private Integer privpolicy;
  private String keywords;
  private String page;
  private String ref;
  private String search;
  private Integer mobile;
  private Integer amp;
  private Map<String, Object> ext;
}
