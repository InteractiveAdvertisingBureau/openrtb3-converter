package net.media.openrtb24.request;

import com.medianet.rtb.mowgli.utils.builder.AbstractBuilder;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NativeRequestBody extends AbstractExtensible<NativeRequestBody.NativeBodyExt> {

  public static final Integer DEFAULT_NATIVE_PLCMTCNT = 1;

  private String ver = Native.DEFAULT_NATIVE_VERSION;

  private Integer layout;

  private Integer adunit;

  private Integer context;

  private Integer contextsubtype;

  private Integer plcmttype;

  private Integer plcmtcnt;

  private Integer seq;

  private List<Asset> assets;

  private Ext ext;

  public NativeRequestBody(String ver, Integer layout, Integer adunit, Integer context, Integer
    contextsubtype, Integer plcmttype, Integer plcmtcnt, Integer seq, List<Asset> assets, Ext ext) {
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
    setReqExt(new NativeBodyExt());
  }

  public static class NativeBodyExt extends ReqExt {
  }

  public static class NativeRequestBodyBuilder extends
    NativeRequestBody.AbstractNativeRequestBodyBuilder<NativeRequestBody.NativeRequestBodyBuilder> {

    @Override
    public NativeRequestBody.NativeRequestBodyBuilder me() {
      return this;
    }
  }

  public abstract static class AbstractNativeRequestBodyBuilder<B extends NativeRequestBody
    .AbstractNativeRequestBodyBuilder<B>> extends AbstractBuilder<NativeRequestBody, B> {

    private String ver = Native.DEFAULT_NATIVE_VERSION;

    private Integer layout;

    private Integer adunit;

    private Integer context;

    private Integer contextsubtype;

    private Integer plcmttype;

    private Integer plcmtcnt;

    private Integer seq;

    private List<Asset> assets = new ArrayList<>();

    private Ext ext;

    public B ver(String ver) {
      this.ver = ver;
      return me();
    }

    public B layout(Integer layout) {
      this.layout = layout;
      return me();
    }

    public B adunit(Integer adunit) {
      this.adunit = adunit;
      return me();
    }

    public B context(Integer context) {
      this.context = context;
      return me();
    }

    public B contextsubtype(Integer contextsubtype) {
      this.contextsubtype = contextsubtype;
      return me();
    }

    public B plcmttype(Integer plcmttype) {
      this.plcmttype = plcmttype;
      return me();
    }

    public B plcmtcnt(Integer plcmtcnt) {
      this.plcmtcnt = plcmtcnt;
      return me();
    }

    public B seq(Integer seq) {
      this.seq = seq;
      return me();
    }

    public B assets(List<Asset> assets) {
      this.assets = assets;
      return me();
    }

    public B ext(Ext ext) {
      this.ext = ext;
      return me();
    }

    public AbstractNativeRequestBodyBuilder() {
    }

    public NativeRequestBody build() {
      return new NativeRequestBody(
        ver,
        layout,
        adunit,
        context,
        contextsubtype,
        plcmttype,
        plcmtcnt,
        seq,
        assets,
        ext
      );
    }
  }
}
