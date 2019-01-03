package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Bid {

  @NotNull
  private String id;//
  @NotNull
  private String item;//bid.impid
  private String deal;//bid.dealid
  @NotNull
  private Double price; //
  private String cid;//
  private String tactic;//bid.ext.tactic
  private String purl;//bid.nurl
  private String burl;//
  private String lurl;//
  private Integer exp;//
  private String mid;//bid.ext.mid
  //Todo code change  for  this  field
  @NotNull
  @Valid
  private List<Macro> macro = null;
  @NotNull
  @Valid
  private Media media;//ext+Bid
  private Map<String,Object> ext;

}