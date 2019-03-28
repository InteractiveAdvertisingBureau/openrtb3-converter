package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@CheckExactlyOneNotNull(fieldNames = {"adm", "banner", "_native", "curl"})
public class Display {

  private String mime;
  private List<Integer> api = null;//bid.api
  private Integer ctype;//bid.ext.ctype
  private Integer w;//bid.w
  private Integer h;//bid.h
  private Integer wratio;//bid.wratio
  private Integer hratio;//bid.hratio
  private String priv;//bid.ext.priv
  private Object adm;//bid.adm
  private String curl;//bid.ext.curl
  @Valid
  private Banner banner;//
  @Valid
  @JsonProperty("native")
  private Native _native;
  @Valid
  private List<Event> event = null;
  private Map<String,Object> ext;

  public Display() {
  }

  public String getMime() {
    return this.mime;
  }

  public List<Integer> getApi() {
    return this.api;
  }

  public Integer getCtype() {
    return this.ctype;
  }

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getWratio() {
    return this.wratio;
  }

  public Integer getHratio() {
    return this.hratio;
  }

  public String getPriv() {
    return this.priv;
  }

  public Object getAdm() {
    return this.adm;
  }

  public String getCurl() {
    return this.curl;
  }

  public @Valid Banner getBanner() {
    return this.banner;
  }

  public @Valid Native get_native() {
    return this._native;
  }

  public @Valid List<Event> getEvent() {
    return this.event;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }

  public void setApi(List<Integer> api) {
    this.api = api;
  }

  public void setCtype(Integer ctype) {
    this.ctype = ctype;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setWratio(Integer wratio) {
    this.wratio = wratio;
  }

  public void setHratio(Integer hratio) {
    this.hratio = hratio;
  }

  public void setPriv(String priv) {
    this.priv = priv;
  }

  public void setAdm(Object adm) {
    this.adm = adm;
  }

  public void setCurl(String curl) {
    this.curl = curl;
  }

  public void setBanner(@Valid Banner banner) {
    this.banner = banner;
  }

  public void set_native(@Valid Native _native) {
    this._native = _native;
  }

  public void setEvent(@Valid List<Event> event) {
    this.event = event;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Display)) return false;
    final Display other = (Display) o;
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
    final Object this$w = this.getW();
    final Object other$w = other.getW();
    if (this$w == null ? other$w != null : !this$w.equals(other$w)) return false;
    final Object this$h = this.getH();
    final Object other$h = other.getH();
    if (this$h == null ? other$h != null : !this$h.equals(other$h)) return false;
    final Object this$wratio = this.getWratio();
    final Object other$wratio = other.getWratio();
    if (this$wratio == null ? other$wratio != null : !this$wratio.equals(other$wratio))
      return false;
    final Object this$hratio = this.getHratio();
    final Object other$hratio = other.getHratio();
    if (this$hratio == null ? other$hratio != null : !this$hratio.equals(other$hratio))
      return false;
    final Object this$priv = this.getPriv();
    final Object other$priv = other.getPriv();
    if (this$priv == null ? other$priv != null : !this$priv.equals(other$priv)) return false;
    final Object this$adm = this.getAdm();
    final Object other$adm = other.getAdm();
    if (this$adm == null ? other$adm != null : !this$adm.equals(other$adm)) return false;
    final Object this$curl = this.getCurl();
    final Object other$curl = other.getCurl();
    if (this$curl == null ? other$curl != null : !this$curl.equals(other$curl)) return false;
    final Object this$banner = this.getBanner();
    final Object other$banner = other.getBanner();
    if (this$banner == null ? other$banner != null : !this$banner.equals(other$banner))
      return false;
    final Object this$_native = this.get_native();
    final Object other$_native = other.get_native();
    if (this$_native == null ? other$_native != null : !this$_native.equals(other$_native))
      return false;
    final Object this$event = this.getEvent();
    final Object other$event = other.getEvent();
    if (this$event == null ? other$event != null : !this$event.equals(other$event)) return false;
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
    final Object $w = this.getW();
    result = result * PRIME + ($w == null ? 43 : $w.hashCode());
    final Object $h = this.getH();
    result = result * PRIME + ($h == null ? 43 : $h.hashCode());
    final Object $wratio = this.getWratio();
    result = result * PRIME + ($wratio == null ? 43 : $wratio.hashCode());
    final Object $hratio = this.getHratio();
    result = result * PRIME + ($hratio == null ? 43 : $hratio.hashCode());
    final Object $priv = this.getPriv();
    result = result * PRIME + ($priv == null ? 43 : $priv.hashCode());
    final Object $adm = this.getAdm();
    result = result * PRIME + ($adm == null ? 43 : $adm.hashCode());
    final Object $curl = this.getCurl();
    result = result * PRIME + ($curl == null ? 43 : $curl.hashCode());
    final Object $banner = this.getBanner();
    result = result * PRIME + ($banner == null ? 43 : $banner.hashCode());
    final Object $_native = this.get_native();
    result = result * PRIME + ($_native == null ? 43 : $_native.hashCode());
    final Object $event = this.getEvent();
    result = result * PRIME + ($event == null ? 43 : $event.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Display;
  }

  public String toString() {
    return "net.media.openrtb3.Display(mime=" + this.getMime() + ", api=" + this.getApi() + ", ctype=" + this.getCtype() + ", w=" + this.getW() + ", h=" + this.getH() + ", wratio=" + this.getWratio() + ", hratio=" + this.getHratio() + ", priv=" + this.getPriv() + ", adm=" + this.getAdm() + ", curl=" + this.getCurl() + ", banner=" + this.getBanner() + ", _native=" + this.get_native() + ", event=" + this.getEvent() + ", ext=" + this.getExt() + ")";
  }
}
