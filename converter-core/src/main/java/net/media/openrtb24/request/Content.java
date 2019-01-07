package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
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

  private Producer producer;

  private String url;

  private Integer videoquality;

  private List<String> cat;

  private Integer prodq;

  private Integer context;

  private String contentrating;

  private String userrating;

  private Integer qagmediarating;

  private String keywords;

  private Integer livestream;

  private Integer sourcerelationship;

  private Integer len;

  private String language;

  private Integer embeddable;

  private List<Data> data;

  private Map<String, Object> ext;

}
