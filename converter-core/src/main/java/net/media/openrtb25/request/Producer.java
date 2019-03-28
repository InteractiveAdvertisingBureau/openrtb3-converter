package net.media.openrtb25.request;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by vishnu on 6/5/16.
 */
public class Producer {

  @NotNull
  private String id;

  private String name;

  private List<String> cat;

  private String domain;

  private Map<String, Object> ext;

  public Producer() {
  }

  public @NotNull String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<String> getCat() {
    return this.cat;
  }

  public String getDomain() {
    return this.domain;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(@NotNull String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCat(List<String> cat) {
    this.cat = cat;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Producer)) return false;
    final Producer other = (Producer) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    final Object this$cat = this.getCat();
    final Object other$cat = other.getCat();
    if (this$cat == null ? other$cat != null : !this$cat.equals(other$cat)) return false;
    final Object this$domain = this.getDomain();
    final Object other$domain = other.getDomain();
    if (this$domain == null ? other$domain != null : !this$domain.equals(other$domain))
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
    final Object $cat = this.getCat();
    result = result * PRIME + ($cat == null ? 43 : $cat.hashCode());
    final Object $domain = this.getDomain();
    result = result * PRIME + ($domain == null ? 43 : $domain.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Producer;
  }

  public String toString() {
    return "net.media.openrtb25.request.Producer(id=" + this.getId() + ", name=" + this.getName() + ", cat=" + this.getCat() + ", domain=" + this.getDomain() + ", ext=" + this.getExt() + ")";
  }
}
