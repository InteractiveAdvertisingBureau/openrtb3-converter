package net.media.openrtb3;


import lombok.Data;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

@Data
public class Event {

  @NotNull
  private Integer type;
  @NotNull
  private Integer method;
  private String url;
  private List<Integer> api;
  private Map<String,Object> cdata;
  private Object  ext;


}