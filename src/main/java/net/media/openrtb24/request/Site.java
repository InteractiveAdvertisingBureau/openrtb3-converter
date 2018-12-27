package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class Site {

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

  private Map<String, Object> ext;
}
