package net.media.openrtb24.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeData extends AbstractExtensible<NativeData.NativeDataReqExt> {

  @NotNull
  private Integer type;

  private Integer len;

  private Ext ext;

  public NativeData() {
    setReqExt(new NativeDataReqExt());
  }

  public static class NativeDataReqExt extends ReqExt {
  }
}
