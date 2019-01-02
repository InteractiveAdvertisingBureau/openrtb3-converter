package net.media.openrtb3;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Event {

  private Integer type;
  private Integer method;
  private String url;
  private List<Integer> api;
  private Map<String,Object> cdata;
  private Object  ext;


}