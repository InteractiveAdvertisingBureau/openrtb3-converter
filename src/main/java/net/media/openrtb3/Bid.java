package net.media.openrtb3;

import java.util.List;

public class Bid {

  private String id;//
  private String item;//
  private String deal;//
  private Double price; //
  private String cid;//
  private String tactic;//bid.ext.tactic
  private String purl;//bid.nurl
  private String burl;//bid.ext.burl
  private String lurl;//bid.ext.lurl
  private Integer exp;//
  private String mid;//bid.ext.mid
  private List<Macro> macro = null;
  private Media media;//ext+Bid

}