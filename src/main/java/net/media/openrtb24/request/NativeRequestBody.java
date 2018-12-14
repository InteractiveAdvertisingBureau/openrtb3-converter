package net.media.openrtb24.request;

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

}
