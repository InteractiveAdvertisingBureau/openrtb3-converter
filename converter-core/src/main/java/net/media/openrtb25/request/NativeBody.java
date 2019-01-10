package net.media.openrtb25.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

  private List<Asset> assets;

  private Ext ext;

  public NativeBody(String ver, Integer layout, Integer adunit, Integer context, Integer contextsubtype, Integer plcmttype, Integer plcmtcnt, Integer seq, List<Asset> assets, Ext ext) {
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

}
