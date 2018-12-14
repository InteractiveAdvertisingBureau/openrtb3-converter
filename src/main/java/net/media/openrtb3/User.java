package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
  private String id;
  private String buyeruid;
  private Integer yob;
  private String gender;
  private String keywords;
  private String consent;
  private Geo geo;
  private Data[] data;
  private Ext ext;
}
