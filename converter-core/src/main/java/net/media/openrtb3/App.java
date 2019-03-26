package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class App extends DistributionChannel {

  private String domain;
  private List<String> cat;
  private List<String> sectcat;
  private Set<String> pagecat;
  private Integer cattax;
  private Integer privpolicy;
  private String keywords;
  private String bundle;
  private String storeid;
  private String storeurl;
  private String ver;
  private Integer paid;
  private Map<String, Object> ext;
}
