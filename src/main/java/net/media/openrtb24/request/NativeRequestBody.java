package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;

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

  private Map<String, Object> ext;

  public static class NativeBodyExt extends ReqExt {
  }

}
