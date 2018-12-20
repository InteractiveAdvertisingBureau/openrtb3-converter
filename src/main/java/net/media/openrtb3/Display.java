package net.media.openrtb3;

import java.util.List;

import lombok.*;

@lombok.Data
public class Display {

  private String mime;
  private List<Integer> api = null;//bid.api
  private Integer ctype;//bid.ext.ctype
  private Integer w;//bid.w
  private Integer h;//bid.h
  private Integer wratio;//bid.wratio
  private Integer hratio;//bid.hratio
  private String priv;//bid.ext.priv
  private Object adm;//bid.adm
  private String curl;//bid.ext.curl
  private Banner banner;//
  private Native _native;
  private List<Event> event = null;
}
