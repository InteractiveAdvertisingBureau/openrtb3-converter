package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
  private String id;
  private Integer qty;
  private Integer seq;
  private double flr;
  private String flrcur;
  private Integer exp;
  private Integer dt;
  private Integer dlvy;
  private Metric[] metric;
  private List<Deal> deal;
  @JsonProperty("private")
  private Integer priv;
  private Spec spec;
  private Map<String, Object> ext;
}
