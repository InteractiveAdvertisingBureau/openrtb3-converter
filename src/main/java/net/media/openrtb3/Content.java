package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Content {

  private String id;
  private Integer episode;
  private String title;
  private String series;
  private String season;
  private String artist;
  private String genre;
  private String album;
  private String isrc;
  private String url;
  private List<String> cat;
  private Integer cattax;
  private Integer prodq;
  private Integer context;
  private String rating;
  private String urating;
  private Integer mrating;
  private String keywords;
  private Integer live;
  private Integer srcrel;
  private Integer len;
  private String lang;
  private Integer embed;
  private Producer producer;
  private List<Data> data;
  private Map<String, Object> ext;

}
