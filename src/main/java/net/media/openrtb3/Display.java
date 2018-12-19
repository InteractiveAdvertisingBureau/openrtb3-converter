package net.media.openrtb3;

import java.util.List;

import lombok.*;

@lombok.Data
public class Display {

  private String mime;
  private List<Integer> api = null;
  private Integer ctype;
  private Integer w;//bid.w
  private Integer h;//bid.h
  private Integer wratio;
  private Integer hratio;
  private String priv;
  private String adm;
  private String curl;
  private Banner banner;
  private Native _native;
  private List<Event> event = null;
}
