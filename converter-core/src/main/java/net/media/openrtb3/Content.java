package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
  private Collection<String> cat;
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
  private Collection<Data> data;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public Integer getEpisode() {
    return this.episode;
  }

  public String getTitle() {
    return this.title;
  }

  public String getSeries() {
    return this.series;
  }

  public String getSeason() {
    return this.season;
  }

  public String getArtist() {
    return this.artist;
  }

  public String getGenre() {
    return this.genre;
  }

  public String getAlbum() {
    return this.album;
  }

  public String getIsrc() {
    return this.isrc;
  }

  public String getUrl() {
    return this.url;
  }

  public Collection<String> getCat() {
    return this.cat;
  }

  public Integer getCattax() {
    return this.cattax;
  }

  public Integer getProdq() {
    return this.prodq;
  }

  public Integer getContext() {
    return this.context;
  }

  public String getRating() {
    return this.rating;
  }

  public String getUrating() {
    return this.urating;
  }

  public Integer getMrating() {
    return this.mrating;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public Integer getLive() {
    return this.live;
  }

  public Integer getSrcrel() {
    return this.srcrel;
  }

  public Integer getLen() {
    return this.len;
  }

  public String getLang() {
    return this.lang;
  }

  public Integer getEmbed() {
    return this.embed;
  }

  public Producer getProducer() {
    return this.producer;
  }

  public Collection<Data> getData() {
    return this.data;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEpisode(Integer episode) {
    this.episode = episode;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setCat(Collection<String> cat) {
    this.cat = cat;
  }

  public void setCattax(Integer cattax) {
    this.cattax = cattax;
  }

  public void setProdq(Integer prodq) {
    this.prodq = prodq;
  }

  public void setContext(Integer context) {
    this.context = context;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public void setUrating(String urating) {
    this.urating = urating;
  }

  public void setMrating(Integer mrating) {
    this.mrating = mrating;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setLive(Integer live) {
    this.live = live;
  }

  public void setSrcrel(Integer srcrel) {
    this.srcrel = srcrel;
  }

  public void setLen(Integer len) {
    this.len = len;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public void setEmbed(Integer embed) {
    this.embed = embed;
  }

  public void setProducer(Producer producer) {
    this.producer = producer;
  }

  public void setData(Collection<Data> data) {
    this.data = data;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
