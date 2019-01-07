package net.media.openrtb24.request;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

import java.util.Map;

import static java.util.Objects.isNull;


@Data
public class Geo {
  private Integer type;
  private String region;
  private String regionfips104;
  private String metro;
  private String city;
  private String zip;
  private Integer utcoffset;
  private String country;
  private Float lat;
  private Float lon;
  private Integer accuracy;
  private Integer lastfix;
  private Integer ipservice;
  private Map<String, Object> ext;

  public boolean isEmpty() {
    return (isNull(this.type) && isNull(this.lat) && isNull(this.lon) &&
      isNull(this.country) && StringUtils.isBlank(this.region) && StringUtils.isBlank(this.regionfips104) &&
      StringUtils.isBlank(this.metro) && StringUtils.isBlank(this.city) && StringUtils.isBlank(this.zip) &&
      isNull(this.utcoffset) && isNull(this.ext));
  }
}
