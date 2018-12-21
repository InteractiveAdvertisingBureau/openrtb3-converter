package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Bid {

  private String id;//
  private String item;//bid.impid
  private String deal;//bid.dealid
  private Double price; //
  private String cid;//
  private String tactic;//bid.ext.tactic
  private String purl;//bid.nurl
  private String burl;//
  private String lurl;//
  private Integer exp;//
  private String mid;//bid.ext.mid
  //Todo code change  for  this  field
  private List<Macro> macro = null;
  private Media media;//ext+Bid
  private Map<String,Object> ext;

}