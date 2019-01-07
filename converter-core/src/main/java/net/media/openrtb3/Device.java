package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Device {
  private Integer type;
  private String ua;
  private String ifa;
  private String dnt;
  private Integer lmt;
  private String make;
  private String model;
  private Integer os;
  private String osv;
  private String hwv;
  private Integer h;
  private Integer w;
  private Integer ppi;
  private Float pxratio;
  private Integer js;
  private String lang;
  private String ip;
  private String ipv6;
  private String xff;
  private Integer iptr;
  private String carrier;
  private String mccmnc;
  private String mccmncsim;
  private Integer contype;
  private Integer geofetch;
  private Geo geo;
  private Map<String, Object> ext;
}
