package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Item {
  @NotBlank
  private String id;
  private Integer qty = 1;
  private Integer seq;
  private double flr;
  private String flrcur = "USD";
  private Integer exp;
  private Integer dt;
  private Integer dlvy = 0;
  private List<Metric> metric;
  private List<Deal> deal;
  @JsonProperty("private")
  private Integer priv = 0;
  @NotNull
  @Valid
  private Spec spec;
  private Map<String, Object> ext;
}
