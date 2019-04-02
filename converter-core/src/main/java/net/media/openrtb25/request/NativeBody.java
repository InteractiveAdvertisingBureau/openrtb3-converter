package net.media.openrtb25.request;

import java.util.Collection;
import java.util.List;

public class NativeBody {

  public static final Integer DEFAULT_NATIVE_PLCMTCNT = 1;

  private String ver;

  private Integer layout;

  private Integer adunit;

  private Integer context;

  private Integer contextsubtype;

  private Integer plcmttype;

  private Integer plcmtcnt;

  private Integer seq;

  private Collection<Asset> assets;

  private Ext ext;

  public NativeBody(String ver, Integer layout, Integer adunit, Integer context, Integer contextsubtype, Integer plcmttype, Integer plcmtcnt, Integer seq, Collection<Asset> assets, Ext ext) {
    this.ver = ver;
    this.layout = layout;
    this.adunit = adunit;
    this.context = context;
    this.contextsubtype = contextsubtype;
    this.plcmttype = plcmttype;
    this.plcmtcnt = plcmtcnt;
    this.seq = seq;
    this.assets = assets;
    this.ext = ext;
  }

  public static NativeBodyBuilder builder() {
    return new NativeBodyBuilder();
  }

  public String getVer() {
    return this.ver;
  }

  public Integer getLayout() {
    return this.layout;
  }

  public Integer getAdunit() {
    return this.adunit;
  }

  public Integer getContext() {
    return this.context;
  }

  public Integer getContextsubtype() {
    return this.contextsubtype;
  }

  public Integer getPlcmttype() {
    return this.plcmttype;
  }

  public Integer getPlcmtcnt() {
    return this.plcmtcnt;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public Collection<Asset> getAssets() {
    return this.assets;
  }

  public Ext getExt() {
    return this.ext;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public void setLayout(Integer layout) {
    this.layout = layout;
  }

  public void setAdunit(Integer adunit) {
    this.adunit = adunit;
  }

  public void setContext(Integer context) {
    this.context = context;
  }

  public void setContextsubtype(Integer contextsubtype) {
    this.contextsubtype = contextsubtype;
  }

  public void setPlcmttype(Integer plcmttype) {
    this.plcmttype = plcmttype;
  }

  public void setPlcmtcnt(Integer plcmtcnt) {
    this.plcmtcnt = plcmtcnt;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public void setAssets(Collection<Asset> assets) {
    this.assets = assets;
  }

  public void setExt(Ext ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof NativeBody)) return false;
    final NativeBody other = (NativeBody) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$ver = this.getVer();
    final Object other$ver = other.getVer();
    if (this$ver == null ? other$ver != null : !this$ver.equals(other$ver)) return false;
    final Object this$layout = this.getLayout();
    final Object other$layout = other.getLayout();
    if (this$layout == null ? other$layout != null : !this$layout.equals(other$layout))
      return false;
    final Object this$adunit = this.getAdunit();
    final Object other$adunit = other.getAdunit();
    if (this$adunit == null ? other$adunit != null : !this$adunit.equals(other$adunit))
      return false;
    final Object this$context = this.getContext();
    final Object other$context = other.getContext();
    if (this$context == null ? other$context != null : !this$context.equals(other$context))
      return false;
    final Object this$contextsubtype = this.getContextsubtype();
    final Object other$contextsubtype = other.getContextsubtype();
    if (this$contextsubtype == null ? other$contextsubtype != null : !this$contextsubtype.equals(other$contextsubtype))
      return false;
    final Object this$plcmttype = this.getPlcmttype();
    final Object other$plcmttype = other.getPlcmttype();
    if (this$plcmttype == null ? other$plcmttype != null : !this$plcmttype.equals(other$plcmttype))
      return false;
    final Object this$plcmtcnt = this.getPlcmtcnt();
    final Object other$plcmtcnt = other.getPlcmtcnt();
    if (this$plcmtcnt == null ? other$plcmtcnt != null : !this$plcmtcnt.equals(other$plcmtcnt))
      return false;
    final Object this$seq = this.getSeq();
    final Object other$seq = other.getSeq();
    if (this$seq == null ? other$seq != null : !this$seq.equals(other$seq)) return false;
    final Object this$assets = this.getAssets();
    final Object other$assets = other.getAssets();
    if (this$assets == null ? other$assets != null : !this$assets.equals(other$assets))
      return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $ver = this.getVer();
    result = result * PRIME + ($ver == null ? 43 : $ver.hashCode());
    final Object $layout = this.getLayout();
    result = result * PRIME + ($layout == null ? 43 : $layout.hashCode());
    final Object $adunit = this.getAdunit();
    result = result * PRIME + ($adunit == null ? 43 : $adunit.hashCode());
    final Object $context = this.getContext();
    result = result * PRIME + ($context == null ? 43 : $context.hashCode());
    final Object $contextsubtype = this.getContextsubtype();
    result = result * PRIME + ($contextsubtype == null ? 43 : $contextsubtype.hashCode());
    final Object $plcmttype = this.getPlcmttype();
    result = result * PRIME + ($plcmttype == null ? 43 : $plcmttype.hashCode());
    final Object $plcmtcnt = this.getPlcmtcnt();
    result = result * PRIME + ($plcmtcnt == null ? 43 : $plcmtcnt.hashCode());
    final Object $seq = this.getSeq();
    result = result * PRIME + ($seq == null ? 43 : $seq.hashCode());
    final Object $assets = this.getAssets();
    result = result * PRIME + ($assets == null ? 43 : $assets.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof NativeBody;
  }

  public String toString() {
    return "net.media.openrtb25.request.NativeBody(ver=" + this.getVer() + ", layout=" + this.getLayout() + ", adunit=" + this.getAdunit() + ", context=" + this.getContext() + ", contextsubtype=" + this.getContextsubtype() + ", plcmttype=" + this.getPlcmttype() + ", plcmtcnt=" + this.getPlcmtcnt() + ", seq=" + this.getSeq() + ", assets=" + this.getAssets() + ", ext=" + this.getExt() + ")";
  }

  public static class NativeBodyBuilder {
    private String ver;
    private Integer layout;
    private Integer adunit;
    private Integer context;
    private Integer contextsubtype;
    private Integer plcmttype;
    private Integer plcmtcnt;
    private Integer seq;
    private Collection<Asset> assets;
    private Ext ext;

    NativeBodyBuilder() {
    }

    public NativeBody.NativeBodyBuilder ver(String ver) {
      this.ver = ver;
      return this;
    }

    public NativeBody.NativeBodyBuilder layout(Integer layout) {
      this.layout = layout;
      return this;
    }

    public NativeBody.NativeBodyBuilder adunit(Integer adunit) {
      this.adunit = adunit;
      return this;
    }

    public NativeBody.NativeBodyBuilder context(Integer context) {
      this.context = context;
      return this;
    }

    public NativeBody.NativeBodyBuilder contextsubtype(Integer contextsubtype) {
      this.contextsubtype = contextsubtype;
      return this;
    }

    public NativeBody.NativeBodyBuilder plcmttype(Integer plcmttype) {
      this.plcmttype = plcmttype;
      return this;
    }

    public NativeBody.NativeBodyBuilder plcmtcnt(Integer plcmtcnt) {
      this.plcmtcnt = plcmtcnt;
      return this;
    }

    public NativeBody.NativeBodyBuilder seq(Integer seq) {
      this.seq = seq;
      return this;
    }

    public NativeBody.NativeBodyBuilder assets(Collection<Asset> assets) {
      this.assets = assets;
      return this;
    }

    public NativeBody.NativeBodyBuilder ext(Ext ext) {
      this.ext = ext;
      return this;
    }

    public NativeBody build() {
      return new NativeBody(ver, layout, adunit, context, contextsubtype, plcmttype, plcmtcnt, seq, assets, ext);
    }

    public String toString() {
      return "net.media.openrtb25.request.NativeBody.NativeBodyBuilder(ver=" + this.ver + ", layout=" + this.layout + ", adunit=" + this.adunit + ", context=" + this.context + ", contextsubtype=" + this.contextsubtype + ", plcmttype=" + this.plcmttype + ", plcmtcnt=" + this.plcmtcnt + ", seq=" + this.seq + ", assets=" + this.assets + ", ext=" + this.ext + ")";
    }
  }
}
