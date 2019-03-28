package net.media.openrtb3;

import java.util.List;
import java.util.Map;

public class Video {

  private List<String> mime = null;
  private List<Integer> api = null;//bid.api
  private Integer ctype;//bid.ext.ctype
  private Integer dur;//bid.ext.dur
  private Object adm;//bid.adm
  private String curl;//bid.ext.curl
  private Map<String,Object> ext;

  public Video() {
  }

  public List<String> getMime() {
    return this.mime;
  }

  public List<Integer> getApi() {
    return this.api;
  }

  public Integer getCtype() {
    return this.ctype;
  }

  public Integer getDur() {
    return this.dur;
  }

  public Object getAdm() {
    return this.adm;
  }

  public String getCurl() {
    return this.curl;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMime(List<String> mime) {
    this.mime = mime;
  }

  public void setApi(List<Integer> api) {
    this.api = api;
  }

  public void setCtype(Integer ctype) {
    this.ctype = ctype;
  }

  public void setDur(Integer dur) {
    this.dur = dur;
  }

  public void setAdm(Object adm) {
    this.adm = adm;
  }

  public void setCurl(String curl) {
    this.curl = curl;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Video)) return false;
    final Video other = (Video) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$mime = this.getMime();
    final Object other$mime = other.getMime();
    if (this$mime == null ? other$mime != null : !this$mime.equals(other$mime)) return false;
    final Object this$api = this.getApi();
    final Object other$api = other.getApi();
    if (this$api == null ? other$api != null : !this$api.equals(other$api)) return false;
    final Object this$ctype = this.getCtype();
    final Object other$ctype = other.getCtype();
    if (this$ctype == null ? other$ctype != null : !this$ctype.equals(other$ctype)) return false;
    final Object this$dur = this.getDur();
    final Object other$dur = other.getDur();
    if (this$dur == null ? other$dur != null : !this$dur.equals(other$dur)) return false;
    final Object this$adm = this.getAdm();
    final Object other$adm = other.getAdm();
    if (this$adm == null ? other$adm != null : !this$adm.equals(other$adm)) return false;
    final Object this$curl = this.getCurl();
    final Object other$curl = other.getCurl();
    if (this$curl == null ? other$curl != null : !this$curl.equals(other$curl)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $mime = this.getMime();
    result = result * PRIME + ($mime == null ? 43 : $mime.hashCode());
    final Object $api = this.getApi();
    result = result * PRIME + ($api == null ? 43 : $api.hashCode());
    final Object $ctype = this.getCtype();
    result = result * PRIME + ($ctype == null ? 43 : $ctype.hashCode());
    final Object $dur = this.getDur();
    result = result * PRIME + ($dur == null ? 43 : $dur.hashCode());
    final Object $adm = this.getAdm();
    result = result * PRIME + ($adm == null ? 43 : $adm.hashCode());
    final Object $curl = this.getCurl();
    result = result * PRIME + ($curl == null ? 43 : $curl.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Video;
  }

  public String toString() {
    return "net.media.openrtb3.Video(mime=" + this.getMime() + ", api=" + this.getApi() + ", ctype=" + this.getCtype() + ", dur=" + this.getDur() + ", adm=" + this.getAdm() + ", curl=" + this.getCurl() + ", ext=" + this.getExt() + ")";
  }
}