package net.media.openrtb25.request;

import java.util.List;
import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */

public class Data {
  private String id;

  private String name;

  private List<Segment> segment;

  private Map<String, Object> ext;

  public Data() {
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<Segment> getSegment() {
    return this.segment;
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

  public void setSegment(List<Segment> segment) {
    this.segment = segment;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Data)) return false;
    final Data other = (Data) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    final Object this$segment = this.getSegment();
    final Object other$segment = other.getSegment();
    if (this$segment == null ? other$segment != null : !this$segment.equals(other$segment))
      return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $segment = this.getSegment();
    result = result * PRIME + ($segment == null ? 43 : $segment.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Data;
  }

  public String toString() {
    return "net.media.openrtb25.request.Data(id=" + this.getId() + ", name=" + this.getName() + ", segment=" + this.getSegment() + ", ext=" + this.getExt() + ")";
  }
}
