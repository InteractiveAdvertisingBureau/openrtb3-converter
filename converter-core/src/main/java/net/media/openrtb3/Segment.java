package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Segment {

  private String id;
  private String name;
  private String value;
  private Map<String, Object> ext;

}
