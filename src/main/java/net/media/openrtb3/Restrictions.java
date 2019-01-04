package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Restrictions {

  private Set<String> bcat;
  private Integer cattax = 2;
  private Set<String> badv;
  private List<String> bapp;
  private Set<Integer> battr;
  private Map<String, Object> ext;

}
