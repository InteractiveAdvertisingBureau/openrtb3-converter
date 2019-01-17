package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Seatbid {

  private String seat;//

  @JsonProperty("package")
  private Integer _package;//seatbid.group
  @NotNull
  @Valid
  private List<Bid> bid = null;//
  private Map<String,Object> ext;//

}