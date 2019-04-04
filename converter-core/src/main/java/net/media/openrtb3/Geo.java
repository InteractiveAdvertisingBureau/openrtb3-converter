package net.media.openrtb3;

import java.util.Map;

public class Geo {

  private Integer type;
  private Float lat;
  private Float lon;
  private Integer accur;
  private Integer lastfix;
  private Integer ipserv;
  private String country;
  private String region;
  private String metro;
  private String city;
  private String zip;
  private Integer utcoffset;
  private Map<String, Object> ext;

  public Integer getType() {
    return this.type;
  }

  public Float getLat() {
    return this.lat;
  }

  public Float getLon() {
    return this.lon;
  }

  public Integer getAccur() {
    return this.accur;
  }

  public Integer getLastfix() {
    return this.lastfix;
  }

  public Integer getIpserv() {
    return this.ipserv;
  }

  public String getCountry() {
    return this.country;
  }

  public String getRegion() {
    return this.region;
  }

  public String getMetro() {
    return this.metro;
  }

  public String getCity() {
    return this.city;
  }

  public String getZip() {
    return this.zip;
  }

  public Integer getUtcoffset() {
    return this.utcoffset;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public void setLat(Float lat) {
    this.lat = lat;
  }

  public void setLon(Float lon) {
    this.lon = lon;
  }

  public void setAccur(Integer accur) {
    this.accur = accur;
  }

  public void setLastfix(Integer lastfix) {
    this.lastfix = lastfix;
  }

  public void setIpserv(Integer ipserv) {
    this.ipserv = ipserv;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setMetro(String metro) {
    this.metro = metro;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setUtcoffset(Integer utcoffset) {
    this.utcoffset = utcoffset;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
