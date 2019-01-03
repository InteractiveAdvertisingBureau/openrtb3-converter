package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Response {

  @NotNull
  private String id;//
  private String bidid;//
  private Integer nbr;//
  private String cur;//
  private String cdata;
  @Valid
  private List<Seatbid> seatbid = null;//
  private Map<String,Object> ext;//

}