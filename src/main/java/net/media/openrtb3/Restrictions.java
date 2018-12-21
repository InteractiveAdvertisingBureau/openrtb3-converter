package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Restrictions {

  private String[] bcat;
  private Integer cattax;
  private String[] badv;
  private String[] bapp;
  private Set<Integer> battr;
  private Map<String, Object> ext;

}
