package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
  private String id;
  private Integer qty;
  private Integer seq;
  private Float flr;
  private String flrcur;
  private Integer exp;
  private Integer dt;
  private Integer dlvy;
  private Metric[] metric;
  private Deal[] deal;
  @JsonProperty("private")
  private Integer priv;
  private Ad spec;
  private Ext ext;
}
