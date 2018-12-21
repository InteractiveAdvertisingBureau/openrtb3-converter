package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Publisher {

  private String id;
  private String name;
  private String domain;
  private String[] cat;
  private Integer cattax;
  private Map<String, Object> ext;

}
