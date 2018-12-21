package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class App extends DistributionChannel {

  private String domain;
  private String[] cat;
  private String[] sectcat;
  private String[] pagecat;
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
