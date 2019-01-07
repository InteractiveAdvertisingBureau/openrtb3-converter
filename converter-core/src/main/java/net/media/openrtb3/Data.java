package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Data {

  private String id;
  private String name;
  private List<Segment> segment;
  private Map<String, Object> ext;

}
