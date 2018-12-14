package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {

  private String id;
  private String name;
  private Segment[] segment;
  private Ext ext;

}
