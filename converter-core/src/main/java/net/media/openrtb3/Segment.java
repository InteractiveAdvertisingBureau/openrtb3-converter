package net.media.openrtb3;

import java.util.Map;

public class Segment {

  private String id;
  private String name;
  private String value;
  private Map<String, Object> ext;

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
