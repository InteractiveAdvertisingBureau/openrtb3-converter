package net.media.openrtb3;


import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class Event {

  @NotNull
  private Integer type;
  @NotNull
  private Integer method;
  private String url;
  private List<Integer> api;
  private Map<String,Object> cdata;
  private Object  ext;

  public Event() {
  }


  public @NotNull Integer getType() {
    return this.type;
  }

  public @NotNull Integer getMethod() {
    return this.method;
  }

  public String getUrl() {
    return this.url;
  }

  public List<Integer> getApi() {
    return this.api;
  }

  public Map<String, Object> getCdata() {
    return this.cdata;
  }

  public Object getExt() {
    return this.ext;
  }

  public void setType(@NotNull Integer type) {
    this.type = type;
  }

  public void setMethod(@NotNull Integer method) {
    this.method = method;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setApi(List<Integer> api) {
    this.api = api;
  }

  public void setCdata(Map<String, Object> cdata) {
    this.cdata = cdata;
  }

  public void setExt(Object ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Event)) return false;
    final Event other = (Event) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
    final Object this$method = this.getMethod();
    final Object other$method = other.getMethod();
    if (this$method == null ? other$method != null : !this$method.equals(other$method))
      return false;
    final Object this$url = this.getUrl();
    final Object other$url = other.getUrl();
    if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
    final Object this$api = this.getApi();
    final Object other$api = other.getApi();
    if (this$api == null ? other$api != null : !this$api.equals(other$api)) return false;
    final Object this$cdata = this.getCdata();
    final Object other$cdata = other.getCdata();
    if (this$cdata == null ? other$cdata != null : !this$cdata.equals(other$cdata)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $method = this.getMethod();
    result = result * PRIME + ($method == null ? 43 : $method.hashCode());
    final Object $url = this.getUrl();
    result = result * PRIME + ($url == null ? 43 : $url.hashCode());
    final Object $api = this.getApi();
    result = result * PRIME + ($api == null ? 43 : $api.hashCode());
    final Object $cdata = this.getCdata();
    result = result * PRIME + ($cdata == null ? 43 : $cdata.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Event;
  }

  public String toString() {
    return "net.media.openrtb3.Event(type=" + this.getType() + ", method=" + this.getMethod() + ", url=" + this.getUrl() + ", api=" + this.getApi() + ", cdata=" + this.getCdata() + ", ext=" + this.getExt() + ")";
  }
}