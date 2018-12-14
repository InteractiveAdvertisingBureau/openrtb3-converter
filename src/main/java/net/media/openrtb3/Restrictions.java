package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restrictions {

  private String[] bcat;
  private Integer cattax;
  private String[] badv;
  private String[] bapp;
  private Integer[] battr;
  private Ext ext;

}
